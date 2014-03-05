package artillery;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;

public class Bullet extends Projectile {
	
	public Image Images[] = new Image[60];
	private int index;
	
	
	Bullet(String ref, int xOffset, int yOffset, int speed, int damage, int index) {
		super(ref, xOffset, yOffset);
		this.setSpeed(speed);
		this.damage = damage;
		this.index = index;
		
		for (int i = 0; i < Images.length; i++) {
			ImageFilter extractFilter = new CropImageFilter(i * 24, 0, 24, 24);

			ImageProducer producer = new FilteredImageSource(this.sprite
					.getImage().getSource(), extractFilter);

			Images[i] = Toolkit.getDefaultToolkit().createImage(producer);
		}
		this.sprite.setWidth(24);
	}

	//draw
	public void draw(Graphics g) {
		g.drawImage(this.Images[index], this.getXOffset(), this.getYOffset(), null);
	}
	
	//update
	public void update() {
		this.move();
		this.remove();
	}

	//move depending on the direction 
	public void move() {
		
		this.setYOffset(this.getYOffset()
				+ (int) (this.getSpeed() * Math.sin(-Math
						.toRadians(index * 6))));
		this.setXOffset(this.getXOffset()
				+ (int) (this.getSpeed() * Math.cos(-Math
						.toRadians(index * 6))));
	}

	//remove bullet if out of range
	public boolean remove() {
		if (getYOffset() < -32) {
			return true;
		}

		if (getXOffset() < -32) {
			return true;
		}
		if (getXOffset() >= 1024 + 32) {
			return true;
		}
		if (getYOffset() >= 1024 + 32) {
			return true;
		}

		return false;
	}

}
