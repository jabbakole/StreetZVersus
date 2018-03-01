package versus;

import java.io.File;

public class VersusMode
{
   private Character p1;
   private Character p2;
   private Stage     stage;

   public VersusMode()
   {
      initializeCharacters();
   }

   private void initializeCharacters()
   {
      p1 = new Character(new File("src/versus/characterdata/bobdata"), 120, 330);
      p2 = new Character(new File("src/versus/characterdata/ninjadata"), 870, 330);
   }

   private void tick()
   {
      
   }

   public Character getp1()
   {
      return p1;
   }

   public Character getp2()
   {
      return p2;
   }
}
