import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Character // extends JPanel
{
   private int       xSize;
   private int       ySize;
   private int       xPos;
   private int       yPos;
   private int       id;       // p1 or p2
   private BufferedImage charImage;
   
   public int getXPos()
   {
      return xPos;
   }
   
   public int getYPos()
   {
      return yPos;
   }

   public Character(int id, File imgFile)
   {
      this.id = id;
      try
      {
         charImage = ImageIO.read(imgFile);
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      xSize = charImage.getWidth();
      ySize = charImage.getHeight();
      if (id == 1)
      {
         xPos = 100;
         yPos = 400;
      }
      else
      {
         xPos = 800;
         yPos = 400;
      }
//      this.setVisible(true);
//      Dimension dm = new Dimension(1280, 720);
//      setPreferredSize(dm);
   }
   
   public BufferedImage getImage()
   {
      return charImage;
   }
}
