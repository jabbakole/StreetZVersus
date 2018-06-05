
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player extends Thing {
	private int xSize;
	private int ySize;
	private int id; // p1 or p2
	private ImageIcon charImage;
	private PlayerKeys pk;
	private int xVel;
	private int yVel;

	public Player(int id, File imgFile, PlayerKeys pk) {
		this.id = id;
		this.pk = pk;
		charImage = new ImageIcon("./src/Player1Char.png");
		xSize = charImage.getImage().getWidth(null);
		ySize = charImage.getImage().getHeight(null);

		if (id == 1) {
			xPos = 100;
			yPos = 400;
		} else {
			xPos = 800;
			yPos = 400;
		}

		box();
	}

	public void box() {
		boxes = new ArrayList<>();
		boxes.add(new Rectangle(xPos + 20, yPos + 20, 70, 70));
		boxes.add(new Rectangle(xPos + 20, yPos + 90, 70, 130));
	}

	public Image getImage() {

		return charImage.getImage();
	}

	@Override
	public void update() {
		yPos += yVel;
		xPos += xVel;

		box();
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(getImage(), xPos, yPos, null);
		for (Rectangle r : boxes) {
			g2d.draw(r);
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == pk.getUp()) {
			yVel = -2;
		} else if (key == pk.getRight()) {
			xVel = 2;
		} else if (key == pk.getLeft()) {
			xVel = -2;
		} else if (key == pk.getDown()) {
			yVel = 2;
		}
	}

	// probably unused for now
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == pk.getUp()) {
			yVel = 0;
		} else if (key == pk.getRight()) {
			xVel = 0;
		} else if (key == pk.getLeft()) {
			xVel = 0;
		} else if (key == pk.getDown()) {
			yVel = 0;
		}
	}

	public void collideCheck(ArrayList<Thing> ents) {
		boolean oof = false;
		for (Rectangle r : boxes) {
			for (Thing th : ents) {
				for(Rectangle rr : th.getBoxes()) {
					oof = r.intersects(rr);
					System.out.println(oof);
				}
			}
		}
		
	}
}