import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Stage extends Thing
{
   String name;
   String desc;

   Image image;

   int floorLevel;

   Stage(String name, String desc, File imageFile)
   {
      try
      {
         this.image = ImageIO.read(imageFile);
      }
      catch (IOException e)
      {
         System.out.println("IOIOIO");
      }
      boxes = new ArrayList<>();
      boxes.add(new Rectangle(0, 600, 1280, 10));
   }

   @Override
   public void update()
   {
      // TODO Auto-generated method stub

   }

   @Override
   public void draw(Graphics2D g2d)
   {
      g2d.drawImage(image, 0, 0, null);
      // drawing model boxes temporarily
      for (Rectangle rect : boxes)
      {
         g2d.draw(rect);
      }
   }

}