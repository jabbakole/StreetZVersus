import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.Timer;

public class Versus extends JPanel implements ActionListener {
	Stage stage;
	Player player1;
	Timer timer;

	ArrayList<Thing> entities;

	Versus(Player p1, Stage stage) {// fix stageName
		setFocusable(true);
		entities = new ArrayList<>();
		addKeyListener(new VersusKeyAdapter(p1));
		this.stage = stage;
		player1 = p1;
		
		addEntity(stage);
		
		timer = new Timer(17, this);
		timer.start();
	
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		stage.draw(g2d);
		player1.draw(g2d);

	}

	public void addEntity(Thing e) {
		entities.add(e);
	}

	public void removeEntity(Thing e) {
		entities.remove(e);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		player1.update();
		tick();
		repaint();
	}

	public void tick() {
		player1.collideCheck(entities);
	}

}