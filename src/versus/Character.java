package versus;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Character
{
   // xPos and yPos are the points the hurtboxes are relative to
   private int         xPosition;
   private int         yPosition;
   private ArrayList<Rectangle> hurtbox;
   private File        hurtboxData;

   public Character(File dataFile, int x, int y)
   {
      hurtbox = new ArrayList<Rectangle>();
      this.hurtboxData = dataFile;
      xPosition = x;
      yPosition = y;
      setHurtboxes();
   }

   private void setHurtboxes()
   {
      try
      {
         Scanner read = new Scanner(hurtboxData);
         while (read.hasNextLine())
         {
            Hurtbox h = new Hurtbox(read.nextInt(), read.nextInt(), read.nextInt(), read.nextInt());
            h.setPosition(xPosition, yPosition);
            hurtbox.add(h);
         }
         read.close();
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   
   public ArrayList<Rectangle> getHurtbox()
   {
      return hurtbox;
   }
   

}
