package things;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import backbone.PlayerKeys;
import things.rectangletype.Quad;
import things.rectangletype.RectType;

public class Player extends Thing implements Gravitable
{
   // for mechanics/model
   private int         topGap;                     // distance between yPos and topmost hurtbox
   private int         leftGap;                    // distance between xPos and leftmost hurtbox
   private int         xSize;                      // width of player model (hurtboxes)
   private int         ySize;                      // height of player model (hurtboxes)
   private int         speed;                      // how fast the character moves left/right
   private int         xVel;
   private int         yVel;
   private int         health;
   private boolean     onFloor             = false;
   private boolean     touchingRightStage  = false;
   private boolean     touchingLeftStage   = false;
   private boolean     touchingRightPlayer = false;
   private boolean     touchingLeftPlayer  = false;
   private boolean     holdDown            = false;
   private boolean     holdRight           = false;
   private boolean     holdLeft            = false;
   private boolean     holdJump            = false;
   private boolean     onLeft              = false;
   private boolean     onRight             = false;
   private PlayerState state;

   // miscellaneous
   private int        id; // p1 or p2
   private PlayerKeys pk;

   // temporary
   private BufferedImage charImage; // temporary sprite for player

   public Player(int id, BufferedImage imgFile, PlayerKeys pk, int speed, int health)
   {
      this.state = PlayerState.STANDING;
      this.id = id;
      this.pk = pk;
      this.charImage = imgFile;
      this.speed = speed;
      this.health = health;
      this.quads = new ArrayList<>();
      // if you switch the next two lines, program breaks for some reason. why?
      setPlayerPosition(id);
      initializeHurtboxes();
      boxCheck();
      setYVars();
      setXVars();
   }

   @Override
   public void update(ArrayList<Thing> entities)
   {
      // System.out.println("PLAYER " + id + " | x: " + xPos + " y: " + yPos + "
      // onFloor: " + onFloor
      // + " touchRightStage: " + touchingRightStage + " touchLeftStage: " +
      // touchingLeftStage + " touchLeftPlayer: "
      // + touchingLeftPlayer + "touchRightPlayer: " + touchingRightPlayer + " state:
      // " + state);
      initializeHurtboxes();
      boxCheck();

      if (state == PlayerState.ACTIVE || state == PlayerState.STARTUP || state == PlayerState.RECOVERY)
      {
         punch();
      }

      if (state == PlayerState.HITSTUN)
      {
         enterHitstun(hitstunFrames);
         System.out.println("yowch!" + hitstunFrames);
      }

      // System.out.println("start collision check");
      // collision check for loop
      for (Thing th : entities)
      {
         if (th.getClass() == Stage.class)
         {
            stageCollisionCheck(th.quads);
         }
         else if (th.getClass() == Player.class)
         {
            Player igneous = (Player) th;
            if (igneous.id != id)
            {
               playerCollisionCheck((Player) th);
               if (state == PlayerState.STANDING || state == PlayerState.CROUCHING)
               {
                  if (((Player) th).getXPos() < this.getXPos())
                     onLeft = false;
                  else
                     onLeft = true;
               }
            }
         }
      }
      if (holdRight)
         goRight();
      else if (holdLeft)
         goLeft();
      if (state == PlayerState.CROUCHING)
         xVel = 0;
      else if (holdJump)
         jump();
      if (state == PlayerState.ACTIVE || state == PlayerState.STARTUP || state == PlayerState.RECOVERY)
      {
         xVel = 0;
      }
      // System.out.println("then gravitate");
      gravitate();
      yPos += yVel;
      xPos += xVel;

   }

   @Override
   public void draw(Graphics2D g2d)
   {
//      Image image = getImage();
//      int width = image.getWidth(null);
//      int height = image.getHeight(null);
//      if (!onLeft)
//         g2d.drawImage(image, xPos + width, yPos, -width, height, null);
//      else
//         g2d.drawImage(image, xPos, yPos, width, height, null);
      for (Quad r : quads)
      {
         g2d.setColor(r.getColor());
         g2d.draw(r);
      }

      // draw health bars
      g2d.setColor(Color.GREEN);
      if (id == 1)
      {
         g2d.fillRect(70, 50, health, 50);
      }
      else
      {
         g2d.fillRect(710 + (500 - health), 50, health, 50);
      }
   }

   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();

      if (key == pk.getUp())
      {
         holdJump = true;
         jump();
      }
      else if (key == pk.getRight())
      {
         holdRight = true;
         goRight();
      }
      else if (key == pk.getLeft())
      {
         holdLeft = true;
         goLeft();
      }
      else if (key == pk.getDown())
      {
         holdDown = true;
         crouch();
      }
      else if (key == pk.getJabConfirm())
      {
         punch();
      }
   }

   public void keyReleased(KeyEvent e)
   {
      int key = e.getKeyCode();

      if (key == pk.getUp())
      {
         holdJump = false;
         // do nothing
      }
      else if (key == pk.getRight())
      {
         holdRight = false;
         if (state != PlayerState.CROUCHING)
            stand();
      }
      else if (key == pk.getLeft())
      {
         holdLeft = false;
         if (state != PlayerState.CROUCHING)
            stand();
      }
      else if (key == pk.getDown())
      {
         holdDown = false;
         if (state != PlayerState.WALKING)
            stand();
      }
   }

   private void stageCollisionCheck(ArrayList<Quad> stageObjs)
   {
      int nextGravitatedPosition = yPos + ySize + yVel;
      int nextLeftPosition = xPos + leftGap + xVel;
      int nextRightPosition = xPos + leftGap + xSize + xVel;
      for (Quad rect : stageObjs)
      {
         if (rect.getType() == RectType.FLOOR)
         {
            if (nextGravitatedPosition >= rect.getY())
            {
               // teleport player onto the floor
               yPos = (int) rect.getY() - ySize - topGap;
               onFloor = true;
               stand();

            } // else
              // state = PlayerState.AIRBORNE;
         }
         else if (rect.getType() == RectType.WALL)
         {
            if (nextLeftPosition <= rect.getMaxX() && nextLeftPosition >= rect.getMinX())
            {
               // teleport player next to left wall
               touchingLeftStage = true;
               xVel = 0;
               xPos = (int) rect.getMaxX() - leftGap;
            }
            else if (nextRightPosition >= rect.getMinX() && nextRightPosition <= rect.getMaxX())
            {
               // teleport player next to right wall
               touchingRightStage = true;
               xVel = 0;
               xPos = (int) rect.getMinX() - xSize - leftGap;
            }
         }
      }
   }

   private void playerCollisionCheck(Player otherPlayer)
   {
      ArrayList<Quad> playerObjs = otherPlayer.quads;
      for (Quad rect : playerObjs)
      {
         if (rect.getType() == RectType.HURTBOX)
         {
            for (Quad thisRect : this.quads)
            {
               if (thisRect.intersects(rect) && thisRect.getType() == RectType.HURTBOX)
               {
                  System.out.println(id + " scoot me away");
                  if (thisRect.getCenterX() >= rect.getCenterX())
                  {
                     this.xPos += speed;
                  }
                  else
                  {
                     this.xPos -= speed;
                  }
                  return;
               }
            }
         }
         else if (rect.getType() == RectType.HITBOX)
         {
            for (Quad thisRect : this.quads)
            {
               if (thisRect.getType() == RectType.HURTBOX && thisRect.intersects(rect))
               {
                  enterHitstun(15);
                  decreaseHealth(50);
                  System.out.println(id + " scoot me away");
                  if (thisRect.getCenterX() >= rect.getCenterX())
                  {
                     this.xPos += speed * 3;
                  }
                  else
                  {
                     this.xPos -= speed * 3;
                  }
               }
            }
         }
      }
   }

   private void stand()
   {
      if (onFloor)
      {
         if (!state.equals(PlayerState.ACTIVE) && !state.equals(PlayerState.RECOVERY)
               && !state.equals(PlayerState.STARTUP))
         {
            state = PlayerState.STANDING;
            xVel = 0;
         }
      }
   }

   private void jump()
   {
      if (state != PlayerState.CROUCHING && state != PlayerState.AIRBORNE)
      {
         state = PlayerState.AIRBORNE;
         onFloor = false;
         yVel = -45;
      }
   }

   private void goLeft()
   {
      if (state == PlayerState.STANDING || state == PlayerState.WALKING)
      {
         state = PlayerState.WALKING;
         touchingRightStage = false;
         if (!touchingLeftStage || !touchingLeftPlayer)
            xVel = -speed;
      }
   }

   private void goRight()
   {
      if (state == PlayerState.STANDING || state == PlayerState.WALKING)
      {
         state = PlayerState.WALKING;
         touchingLeftStage = false;
         if (!touchingRightStage || !touchingRightPlayer)
            xVel = speed;
      }
   }

   private void crouch()
   {
      if (state != PlayerState.AIRBORNE)
      {
         System.out.println("hey i crouch()");
         state = PlayerState.CROUCHING;
      }
   }

   private void decreaseHealth(int x)
   {
      health -= x;
   }

   private int frameCounter = 0;

   private void punch()
   {
      if (state == PlayerState.WALKING || state == PlayerState.STANDING || state == PlayerState.STARTUP
            || state == PlayerState.ACTIVE || state == PlayerState.RECOVERY)
      {
         if (frameCounter == 15)
         {
            state = PlayerState.STANDING;
            frameCounter = 0;
         }
         else if (frameCounter > 9)
         {
            state = PlayerState.RECOVERY;
            frameCounter++;
         }
         else if (frameCounter > 4)
         {
            state = PlayerState.ACTIVE;
            frameCounter++;
         }
         else if (frameCounter >= 0)
         {
            state = PlayerState.STARTUP;
            xVel = 0;
            frameCounter++;
         }
      }
   }

   private int hitstunFrames;

   private void enterHitstun(int hitstunFrames)
   {
      this.hitstunFrames = hitstunFrames;
      if (this.hitstunFrames == 0)
      {
         state = PlayerState.STANDING;
      }
      else
      {
         this.hitstunFrames--;
         state = PlayerState.HITSTUN;
      }
   }

   @Override
   public void gravitate()
   {
      if (!onFloor)
         yVel += 2;
      else
         yVel = 0;
   }

   private int spriteCounter;

   BufferedImage spriteSheet = charImage;

   private Image getNextSprite()
   {
      BufferedImage spriteSheet = charImage;
      int x = 180;
      int y = 300;
      switch (state)
      {
         case WALKING:
         {
            spriteCounter++;
            if (spriteCounter >= 4)
            {
               spriteCounter = 0;
            }

            return spriteSheet.getSubimage(spriteCounter * x, 0, x, y);
         }
         case STANDING:
         {
            spriteCounter++;
            if (spriteCounter >= 4)
            {
               spriteCounter = 0;
            }
            return spriteSheet.getSubimage(spriteCounter * x, 1200, x, y);
         }
         case CROUCHING:
         {
            return spriteSheet.getSubimage(x * 4, 0, x, y);
         }
         case AIRBORNE:
         {
            spriteCounter++;
            if (spriteCounter >= 6)
            {
               spriteCounter = 0;
            }
            if (spriteCounter < 3)
               return spriteSheet.getSubimage(spriteCounter * (x + 90), y, x + 90, y);
            else
               return spriteSheet.getSubimage((spriteCounter - 3) * (x + 90), y * 2, x + 90, y);
         }
         case STARTUP:
         {
            return spriteSheet.getSubimage(0, y * 3, x, y);
         }
         case ACTIVE:
         {
            return spriteSheet.getSubimage(x, y * 3, x, y);
         }
         case RECOVERY:
         {
            return spriteSheet.getSubimage(x * 2, y * 3, x, y);
         }
         case HITSTUN:
         {
            return spriteSheet.getSubimage(x * 3, y * 3, x, y);
         }
         default:
            return spriteSheet.getSubimage(spriteCounter * x, 0, x, y);
      }
   }

   public Image getImage()
   {
      Image image = getNextSprite();
      Image image2 = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
      int width = image.getWidth(null);
      int height = image.getHeight(null);
      if (!onLeft)
         image2.getGraphics().drawImage(image, width, 0, -width, height, null);
      else
         image2.getGraphics().drawImage(image, 0, 0, width, height, null);
      return image2;
   }

   private void setYVars()
   {
      // double-checked this method
      int higher = 10000;
      int lower = 0;
      for (Rectangle r : quads)
      {
         int y = (int) r.getY();
         int lowerY = (int) r.getY() + (int) r.getHeight();
         if (y <= higher)
            higher = y;
         if (lowerY >= lower)
            lower = lowerY;
      }
      ySize = lower - higher;
      topGap = higher - yPos;
   }

   private void setXVars()
   {
      // double checked this method.. don't think right gap is necessary so removed
      int righter = 0;
      int lefter = 100000;
      for (Rectangle r : quads)
      {
         int x = (int) r.getX();
         int righterX = (int) r.getX() + (int) r.getWidth();
         if (x <= lefter)
            lefter = x;
         if (righterX >= lefter)
            righter = righterX;
      }
      xSize = righter - lefter;
      System.out.println(xSize);
      leftGap = lefter - xPos;
   }

   private void setPlayerPosition(int id)
   {
      if (id == 1)
      {
         xPos = 300;
         yPos = 400;
      }
      else // is p2
      {
         xPos = 800;
         yPos = 400;
      }
   }

   private ArrayList<Quad> standBoxes;
   private ArrayList<Quad> crouchBoxes;
   private ArrayList<Quad> walkBoxes;
   private ArrayList<Quad> airBoxes;
   private ArrayList<Quad> punchStartUpBoxes;
   private ArrayList<Quad> punchActiveBoxes;
   private ArrayList<Quad> punchRecoveryBoxes;
   private ArrayList<Quad> hitstunBoxes;

   private void initializeHurtboxes()
   {
      int numb = 0;
      int numb1 = 0;
      if (!onLeft)
      {
         numb = -175;
         numb1 = 30;
      }

      standBoxes = new ArrayList<>();
      standBoxes.add(new Quad(xPos + 40 + numb1, yPos + 20, 70, 70, RectType.HURTBOX)); // O_O
      standBoxes.add(new Quad(xPos + 40 + numb1, yPos + 90, 70, 130, RectType.HURTBOX));
      standBoxes.add(new Quad(xPos + 40 + numb1, yPos + 220, 70, 70, RectType.HURTBOX));

      crouchBoxes = new ArrayList<>();
      crouchBoxes.add(new Quad(xPos + 20, yPos + 120, 200, 100, RectType.HURTBOX));

      walkBoxes = new ArrayList<>();
      walkBoxes.add(new Quad(xPos + 40, yPos + 20, 70, 70, RectType.HURTBOX));
      walkBoxes.add(new Quad(xPos + 40, yPos + 90, 70, 130, RectType.HURTBOX));
      walkBoxes.add(new Quad(xPos + 40, yPos + 220, 70, 70, RectType.HURTBOX));

      airBoxes = new ArrayList<>();
      airBoxes.add(new Quad(xPos + 40, yPos + 20, 70, 70, RectType.HURTBOX));

      punchStartUpBoxes = new ArrayList<>();
      punchStartUpBoxes.add(new Quad(xPos + 20 + numb1, yPos + 20, 70, 70, RectType.HURTBOX));
      punchStartUpBoxes.add(new Quad(xPos + 20 + numb1, yPos + 90, 70, 130, RectType.HURTBOX));
      punchStartUpBoxes.add(new Quad(xPos + 20 + numb1, yPos + 220, 70, 70, RectType.HURTBOX));

      punchActiveBoxes = new ArrayList<>();
      punchActiveBoxes.add(new Quad(xPos + 40 + numb1, yPos + 20, 70, 70, RectType.HURTBOX));
      punchActiveBoxes.add(new Quad(xPos + 40 + numb1, yPos + 90, 70, 130, RectType.HURTBOX));
      punchActiveBoxes.add(new Quad(xPos + 40 + numb1, yPos + 220, 70, 70, RectType.HURTBOX));
      punchActiveBoxes.add(new Quad(xPos + 70 + numb, yPos + 120, 200, 40, RectType.HITBOX));

      punchRecoveryBoxes = new ArrayList<>();
      punchRecoveryBoxes.add(new Quad(xPos + 20 + numb1, yPos + 20, 70, 70, RectType.HURTBOX));
      punchRecoveryBoxes.add(new Quad(xPos + 20 + numb1, yPos + 90, 70, 130, RectType.HURTBOX));
      punchRecoveryBoxes.add(new Quad(xPos + 20 + numb1, yPos + 220, 70, 80, RectType.HURTBOX));

      hitstunBoxes = new ArrayList<>();
      hitstunBoxes.add(new Quad(xPos + 40, yPos + 20, 70, 70, RectType.HURTBOX)); // O_O
      hitstunBoxes.add(new Quad(xPos + 40, yPos + 90, 70, 130, RectType.HURTBOX));
      hitstunBoxes.add(new Quad(xPos + 40, yPos + 220, 70, 70, RectType.HURTBOX));

   }

   private void boxCheck()
   {
      switch (state)
      {
         case STANDING:
         {
            // System.out.println(id + "hey i STANDING");
            quads = standBoxes;
            break;
         }
         case CROUCHING:
         {
            // System.out.println(id + "hey i CROUCHING");
            quads = crouchBoxes;
            break;
         }
         case WALKING:
         {
            // System.out.println(id + "hey i WALKING");
            quads = walkBoxes;
            break;
         }
         case AIRBORNE:
         {
            // System.out.println(id + "wow i fly");
            quads = airBoxes;
            break;
         }
         case STARTUP:
         {
            quads = punchStartUpBoxes;
            break;
         }
         case ACTIVE:
         {
            quads = punchActiveBoxes;
            break;
         }
         case RECOVERY:
         {
            quads = punchRecoveryBoxes;
            break;
         }
         case HITSTUN:
            quads = hitstunBoxes;
         break;
      }
   }
}
