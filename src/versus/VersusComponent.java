package versus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class VersusComponent extends JComponent
{
   VersusMode versus;
   
   public VersusComponent()
   {
      versus = new VersusMode();
   }
   
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      ArrayList<Rectangle> x = versus.getp1().getHurtbox();
      g2.setColor(Color.BLACK);
      for (Rectangle z : x)
      {
         g2.draw(z);
      }
      
      x = versus.getp2().getHurtbox();
      g2.setColor(Color.PINK);
      for (Rectangle z : x)
      {
         g2.draw(z);
      }
   }
}
