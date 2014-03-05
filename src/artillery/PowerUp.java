package artillery;

import java.awt.Graphics;
import java.util.Random;

public class PowerUp extends GameObject {

	private int type;
	private long Timer;
	private long Delay;

	public PowerUp(String ref, int xOffset, int yOffset, int speed, int type) {
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
		this.type = type;
		Timer = System.nanoTime();
		Delay = 10000;
	}

	public void draw(Graphics g) {
		g.drawImage(this.sprite.getImage(), this.getXOffset(),
				this.getYOffset(), null);
	}

	public boolean remove() {

		long elapsed = (System.nanoTime() - Timer) / 1000000;

		if (elapsed > Delay) {
			return true;
		}

		return false;

	}

	public void update() {
		//this.remove();
	}

	public void move() {
		this.setYOffset(this.getYOffset() + this.getSpeed());
	}

	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public int getType() {
		return type;
	}

}
