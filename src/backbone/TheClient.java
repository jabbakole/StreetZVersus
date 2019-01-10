package backbone;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import things.Player;
import things.Stage;

public class TheClient extends JFrame
{
   TheClient()
   {
      Dimension dm = new Dimension(1280, 720);

      PlayerKeys pk1 = new PlayerKeys(1);
      PlayerKeys pk2 = new PlayerKeys(2);

      File imageFile = new File("./src/spookyland.png");

      // p1 files
      BufferedImage charImgFile = null;
      try
      {
         charImgFile = ImageIO.read(new File("./src/Char1.png"));
      }
      catch (IOException e1)
      {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }

      // p2 files
      BufferedImage charImgFile2 = null;
      try
      {
         charImgFile2 = ImageIO.read(new File("./src/Char1.png"));
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      Player p1 = new Player(1, charImgFile, pk1, 10, 500);
      Player p2 = new Player(2, charImgFile2, pk2, 10, 500);

      Stage stage = new Stage("SpookyLand", "Wow spooky", imageFile, p1, p2);

      Versus versus = new Versus(p1, p2, stage);
      stage.addVS(versus);

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
