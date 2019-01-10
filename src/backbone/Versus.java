package backbone;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import things.Player;
import things.Stage;
import things.Thing;

public class Versus extends JPanel implements ActionListener
{
   Stage  stage;
   Player player1;
   Player player2;
   Timer  timer;

   ArrayList<Thing> entities;

   Versus(Player p1, Player p2, Stage stage)
   {// fix stageName
      setFocusable(true);
      entities = new ArrayList<>();
      addKeyListener(new VersusKeyAdapter(p1, p2));
      this.stage = stage;
      player1 = p1;
      player2 = p2;
      addEntity(stage);
      addEntity(p1);
      addEntity(p2);
      timer = new Timer(17, this);
      timer.start();

   }

   public void paint(Graphics g)
   {
      super.paint(g);
      Graphics2D g2d = (Graphics2D) g;
      stage.draw(g2d);
   }

   public void addEntity(Thing e)
   {
      entities.add(e);
   }

   public void removeEntity(Thing e)
   {
      entities.remove(e);
   }

   @Override
   public void actionPerformed(ActionEvent arg0)
   {
      tick();
      repaint();
   }

   public void tick()
   {
      stage.update();
      player1.update(entities);
      player2.update(entities);
   }

}
