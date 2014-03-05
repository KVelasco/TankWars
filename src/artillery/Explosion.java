

package artillery;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.Collections;

public class Explosion extends GameObject {

	private ArrayList<Image> explosion = new ArrayList<Image>();
	private Image small[] = new Image[6];
	private Image large[] = new Image[7];
	private Animation animation;

	Explosion(String ref, int xOffset, int yOffset, int type) {
		super(ref, xOffset, yOffset);
		if (type == 1) {
			for (int i = 0; i < small.length; i++) {
				ImageFilter extractFilter = new CropImageFilter(i * 32, 0, 32,
						32);

				ImageProducer producer = new FilteredImageSource(this.sprite
						.getImage().getSource(), extractFilter);

				small[i] = Toolkit.getDefaultToolkit().createImage(producer);
			}
			Collections.addAll(explosion, small);
		} else if (type == 2) {
			for (int i = 0; i < large.length; i++) {
				ImageFilter extractFilter = new CropImageFilter(i * 64, 0, 64,
						64);

				ImageProducer producer = new FilteredImageSource(this.sprite
						.getImage().getSource(), extractFilter);

				large[i] = Toolkit.getDefaultToolkit().createImage(producer);
			}
			Collections.addAll(explosion, large);
		}

		animation = new Animation(7, explosion);
	}

	// if last frame remove explosion
	public boolean remove() {
		if ((explosion.size() - 1) == animation.getIndex()) {
			return true;
		}
		return false;
	}

	// draw
	public void draw(Graphics g) {
		animation.drawAnimation(g, this.xOffset, this.yOffset, 0);
	}

	// update
	public void update() {
		animation.runAnimation();
	}

	public void move() {
	}
}
