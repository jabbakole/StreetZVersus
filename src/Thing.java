import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Thing
{
   protected int xPos;
   protected int yPos;
   protected ArrayList<Rectangle> boxes;
   
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

   public abstract void update();
   
   public abstract void draw(Graphics2D g2d);
   
   public ArrayList<Rectangle> getBoxes() {
	   return boxes;
   }
}