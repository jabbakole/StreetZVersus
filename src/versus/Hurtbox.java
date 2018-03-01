package versus;

import java.awt.Rectangle;

public class Hurtbox extends Rectangle
{
   // position of the hurtbox relative to a point:
   // all hurtboxes need to be relative to a point which determines position
   // of the player/character
   private int relativeX;
   private int relativeY;

   public Hurtbox(int relX, int relY, int width, int height)
   {
      relativeX = relX;
      relativeY = relY;
      this.width = width;
      this.height = height;
   }

   public int getRelX()
   {
      return relativeX;
   }

   public int getRelY()
   {
      return relativeY;
   }

   // the x and y arguments are the point the hurtbox is relative to
   public void setPosition(int x, int y)
   {
      this.setLocation(x + relativeX, y + relativeY);
   }
}
