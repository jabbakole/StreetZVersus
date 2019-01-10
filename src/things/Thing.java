package things;

import java.awt.Graphics2D;
import java.util.ArrayList;

import things.rectangletype.Quad;

public abstract class Thing
{
   protected int             xPos;
   protected int             yPos;
   protected ArrayList<Quad> quads;

   public Thing()
   {

   }

   public Thing(int x, int y)
   {
      this.xPos = x;
      this.yPos = y;
   }

   public int getXPos()
   {
      return xPos;
   }

   public int getYPos()
   {
      return yPos;
   }

   public abstract void update(ArrayList<Thing> entities);

   public abstract void draw(Graphics2D g2d);

   public ArrayList<Quad> getQuads()
   {
      return quads;
   }
}
