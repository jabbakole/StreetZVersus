package things;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import backbone.Versus;
import things.rectangletype.Quad;
import things.rectangletype.RectType;

public class Stage extends Thing
{
   String name;
   String desc;
   Player p1;
   Player p2;
   int    xPos;

   Image image;
   Image punchedImage;

   JPanel vs;

   // int floorLevel;

   public Stage(String name, String desc, File imageFile, Player p1, Player p2)
   {
      xPos = 0;
      try
      {
         this.image = ImageIO.read(imageFile);
         this.punchedImage = ImageIO.read(imageFile);
      }
      catch (IOException e)
      {
         System.out.println("IOIOIO");
      }
      quads = new ArrayList<>();
      quads.add(new Quad(0, 600, 1280, 100, RectType.FLOOR));
      quads.add(new Quad(0, 100, 1280, 10, RectType.PLATFORM));
      // quads.add(new Quad(1230, 0, 50, 720, RectType.WALL));
      // quads.add(new Quad(0, 0, 50, 720, RectType.WALL));

      this.p1 = p1;
      this.p2 = p2;
   }

   @Override
   public void draw(Graphics2D g2d)
   {
      punchedImage.getGraphics().drawImage(image, 0, 0, null);
      punchedImage.getGraphics().drawImage(p1.getImage(), p1.getXPos(), p1.getYPos(), null);
      punchedImage.getGraphics().drawImage(p2.getImage(), p2.getXPos(), p2.getYPos(), null);
      g2d.drawImage(punchedImage, xPos, 0, 760*2, 144*2, null);
      // drawing model boxes temporarily
      for (Rectangle rect : quads)
      {
         g2d.draw(rect);
      }
      
      p1.draw(g2d);
      p2.draw(g2d);
   }

   public void update()
   {
      // xPos = -((p1.getXPos()+p2.getXPos())/2)-1000;
      // xPos++;
   }

   @Override
   public void update(ArrayList<Thing> entities)
   {
      // TODO Auto-generated method stub

   }

   public void addVS(Versus versus)
   {
      this.vs = versus;

   }

}
