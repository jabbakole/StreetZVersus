import java.awt.Dimension;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TheClient extends JFrame
{
   TheClient()
   {
      Dimension dm = new Dimension(1280, 720);

      PlayerKeys pk = new PlayerKeys(1);
      

      File imageFile = new File("./src/spookyland_icon.png");
      File charImgFile = new File("./src/Player1Char.png");

      Stage stage = new Stage("SpookyLand", "Wow spooky", imageFile);

      Player p1 = new Player(1, charImgFile, pk);
      
      Versus versus = new Versus(p1, stage);

      add(versus);

      setSize(dm);
      setPreferredSize(dm);
      setMaximumSize(dm);
      setMinimumSize(dm);
      setResizable(false);
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
   }

   public static void main(String[] args)
   {
      new TheClient();
   }
}