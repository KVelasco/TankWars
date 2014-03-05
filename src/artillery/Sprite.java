package artillery;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	private Image image;
	private int width;
	private int height; 

	public Sprite(Image image) {
		this.image = image;
		this.height = image.getHeight(null);
		this.width = image.getWidth(null);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public void setWidth(int width){
		this.width = width;
	}

	public void draw(Graphics g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

}
