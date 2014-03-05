package artillery;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Animation {

	private int speed;
	private int frames;
	private int index = 0;
	private int count = 0;
	private ArrayList<Image> sprites;

	public Animation(int speed, ArrayList<Image> sprites) {
		this.speed = speed;
		this.sprites = sprites;
		this.frames = this.sprites.size();
	}
	
	//determine how long to stay on current image
	public void runAnimation() {
		count++;
		if (count > speed) {
			count = 0;
			nextFrame();
		}
	}
	
	//get next image
	public void nextFrame() {
		if (index >= (frames-1)) {
			index = 0;
		} else {
			index++;
		}

	}

	//draw current image 
	public void drawAnimation(Graphics g, int x, int y, int offset) {
		g.drawImage(this.sprites.get(index), x - offset, y, null);
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public int getFrames()
	{
		return this.frames;
	}
	
	public int getIndex()
	{
		return this.index;
	}

	public int getCount() {
		return count;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}