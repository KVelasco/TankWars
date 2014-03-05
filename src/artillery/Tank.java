package artillery;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.Toolkit;
import java.util.ArrayList;

public class Tank extends Vehicle {

	public Image Images[] = new Image[60];
	protected InputHandler input;
	public int index = 0;
	private int player;
	private int score = 0;
	private int lives = 4;

	private long firingTimer;
	private long firingDelay;

	private ArrayList<Bullet> bullets;

	private int xStart;
	private int yStart;
	public int ySpeed;

	private boolean recovering = false;
	private long recoverTimer;
	private long recoverDelay;
	private long recoverTotal = 0;

	private int powerUp = 0;
	

	Tank(String ref, int xOffset, int yOffset, InputHandler input, int player,
			ArrayList<Bullet> bullets) {
		super(ref, xOffset, yOffset);

		this.player = player;
		this.input = input;
		this.setSpeed(4);
		this.setySpeed(4);
		this.setHealth(50);
		this.bullets = bullets;

		firingTimer = System.nanoTime();
		firingDelay = 200;

		xStart = xOffset;
		yStart = yOffset;

		recoverTimer = System.nanoTime();
		recoverDelay = 1;

		for (int i = 0; i < Images.length; i++) {
			ImageFilter extractFilter = new CropImageFilter(i * 64, 0, 64, 64);

			ImageProducer producer = new FilteredImageSource(this.sprite
					.getImage().getSource(), extractFilter);

			Images[i] = Toolkit.getDefaultToolkit().createImage(producer);
		}

		this.sprite.setWidth(64);
	}

	@Override
	public void shoot() {

		if (player == 1) {
			if (input.fire.isPressed()) {
				long elapsed = (System.nanoTime() - firingTimer) / 1000000;

				if (elapsed > firingDelay) {
					if (this.powerUp == 1) {
						bullets.add(new Bullet("Rocket_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								10, index));
					} else if (this.powerUp == 2) {
						bullets.add(new Bullet("Bouncing_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								5, index));
					} else {
						bullets.add(new Bullet("Shell_light_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								1, index));
					}
					firingTimer = System.nanoTime();
				}

			}
		}

		if (player == 2) {
			if (input.fire2.isPressed()) {
				long elapsed = (System.nanoTime() - firingTimer) / 1000000;

				if (elapsed > firingDelay) {

					if (this.powerUp == 1) {
						bullets.add(new Bullet("Rocket_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								10, index));
					} else if (this.powerUp == 2) {
						bullets.add(new Bullet("Bouncing_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								5, index));
					} else {
						bullets.add(new Bullet("Shell_light_strip60.png", this
								.getXOffset() + 13, this.getYOffset() + 20, 7,
								1, index));
					}
					firingTimer = System.nanoTime();
				}
			}
		}
	}

	public void bound() {
	
	}

	public void draw(Graphics g) {
		if (recovering) {
			long elapsed = (System.nanoTime() - recoverTimer) / 1000000;
			if (elapsed > recoverDelay) {
				g.drawImage(Images[index], this.getXOffset(),
						this.getYOffset(), null);
				recoverTimer = System.nanoTime();
				recoverTotal += recoverDelay;
				if (recoverTotal > 1500) {
					recoverTotal = 0;
					recovering = false;
				}
			}
		} else {
			g.drawImage(Images[index], this.getXOffset(), this.getYOffset(),
					null);

		}
		// g.drawImage(Images[index], this.getXOffset(), this.getYOffset(),
		// null);

	}

	public boolean remove() {
		
		if(lives <= 0 && health <= 0){
			if(player == 1){
				Game.state = Game.STATE.TWOPLAYER;
			}else if(player == 2){
				Game.state = Game.STATE.ONEPLAYER;
			}
		}
		if (this.getHealth() <= 0) {
			recovering = true;
			powerUp = 0;
			lives--;
			health = 50;
			xOffset = xStart;
			yOffset = yStart;
			index = 0;
		}
		
		return false;
	}

	public void update() {
		this.shoot();
		this.remove();
		this.move();
	}

	@Override
	public void move() {

		if (player == 1) {
			if (input.up.isPressed()) {
				this.setYOffset(this.getYOffset()
						+ (int) (this.getySpeed() * Math.sin(-Math
								.toRadians(index * 6))));
				this.setXOffset(this.getXOffset()
						+ (int) (this.getSpeed() * Math.cos(-Math
								.toRadians(index * 6))));
			}
			if (input.down.isPressed()) {
				this.setYOffset(this.getYOffset()
						- (int) (this.getySpeed() * Math.sin(-Math
								.toRadians(index * 6))));
				this.setXOffset(this.getXOffset()
						- (int) (this.getSpeed() * Math.cos(-Math
								.toRadians(index * 6))));
			}
			if (input.left.isPressed()) {
				if (index < 59) {
					index++;
				} else {
					index = 0;
				}
			}
			if (input.right.isPressed()) {
				if (index <= 0) {
					index = 59;
				} else {
					index--;
				}
			}

		}

		if (player == 2) {
			if (input.up2.isPressed()) {
				this.setYOffset(this.getYOffset()
						+ (int) (this.getySpeed() * Math.sin(-Math
								.toRadians(index * 6))));
				this.setXOffset(this.getXOffset()
						+ (int) (this.getSpeed() * Math.cos(-Math
								.toRadians(index * 6))));

			}
			if (input.down2.isPressed()) {
				this.setYOffset(this.getYOffset()
						- (int) (this.getySpeed() * Math.sin(-Math
								.toRadians(index * 6))));
				this.setXOffset(this.getXOffset()
						- (int) (this.getSpeed() * Math.cos(-Math
								.toRadians(index * 6))));

			}
			if (input.left2.isPressed()) {
				if (index < 59) {
					index++;
				} else {
					index = 0;
				}
			}
			if (input.right2.isPressed()) {
				if (index <= 0) {
					index = 59;
				} else {
					index--;
				}
			}
		}

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isRecovering() {
		return recovering;
	}

	public void setPowerUp(int powerUp) {
		this.powerUp = powerUp;
	}

	public int getPowerUp() {
		return this.powerUp;
	}
	
	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}


}
