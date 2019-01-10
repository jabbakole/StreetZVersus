package things.rectangletype;

import java.awt.Color;
import java.awt.Rectangle;

public class Quad extends Rectangle
{
   RectType type;
   Color color;
   
   public Quad(int xPos, int yPos, int xSize, int ySize, RectType type)
   {
      super(xPos, yPos, xSize, ySize);
      this.type = type;
      if (type.equals(RectType.HURTBOX))
      {
         color = Color.BLACK;
      }
      else if (type.equals(RectType.HITBOX))
      {
         color = Color.RED;
      }
   }
   
   public Color getColor()
   {
      return color;
   }

   public RectType getType()
   {
      return type;
   }
}
