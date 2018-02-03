package versus;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Character
{
   private int         xPosition;
   private int         yPosition;
   private ArrayList<Rectangle> hurtbox;
   private File        hurtboxData;

   public Character(File dataFile)
   {
      hurtbox = new ArrayList<Rectangle>();
      this.hurtboxData = dataFile;
      setHurtboxes();
   }

   private void setHurtboxes()
   {
      try
      {
         Scanner reader = new Scanner(hurtboxData);
         while (reader.hasNextLine())
         {
            hurtbox.add(new Rectangle());
         }
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }

}
