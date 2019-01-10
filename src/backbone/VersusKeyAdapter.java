package backbone;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import things.Player;

public class VersusKeyAdapter extends KeyAdapter
{

   Player p1;
   Player p2;

   public VersusKeyAdapter(Player p1, Player p2)
   {
      this.p1 = p1;
      this.p2 = p2;
   }

   public void keyPressed(KeyEvent e)
   {
      p1.keyPressed(e);
      p2.keyPressed(e);
   }

   public void keyReleased(KeyEvent e)
   {
      p1.keyReleased(e);
      p2.keyReleased(e);
   }

}
