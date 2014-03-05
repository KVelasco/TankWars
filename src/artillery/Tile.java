package artillery;

import java.awt.Graphics;

public class Tile extends GameObject{
	
	private boolean isBreakable;

	public Tile(String ref, int xOffset, int yOffset, boolean isBreakable) {
		super(ref, xOffset, yOffset);
		this.isBreakable = isBreakable;
	}

	public void draw(Graphics g) {
		g.drawImage(this.sprite.getImage(), this.getXOffset(), this.getYOffset(), null);
	}

	
	public boolean remove() {
		return isBreakable;
	}

	
	public void update() {	
	}


	public void move() {
	}

}
