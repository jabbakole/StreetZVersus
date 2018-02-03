package versus;
import javax.swing.JFrame;

public class PlaceholderFrame extends JFrame
{
   
   public PlaceholderFrame()
   {
      setBounds(500, 200, 1280, 720);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setResizable(false);
      setVisible(true);
      setTitle("StreetZ");
      VersusMode game = new VersusMode();
      game.setSize(1280, 720);
      game.setOpaque(true);
      setContentPane(game);
   }
   
   public static void main(String[] args)
   {
      PlaceholderFrame frame = new PlaceholderFrame();
   }
}
