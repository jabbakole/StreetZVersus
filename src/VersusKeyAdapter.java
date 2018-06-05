

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VersusKeyAdapter extends KeyAdapter
{

   Player p1;
 //  private PlayerKeys p2;

   public VersusKeyAdapter(Player p1)
   {
      this.p1 = p1;
    //  this.p2 = p2;
   }
   
   public void keyPressed(KeyEvent e) {
	   p1.keyPressed(e);
   }

   public void keyReleased(KeyEvent e) {
	   p1.keyReleased(e);
   }


}
