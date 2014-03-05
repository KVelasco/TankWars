package artillery;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 5;
	public static final String NAME = "Tank Game";
	public static final Dimension DIMENSIONS = new Dimension(WIDTH * SCALE,
			HEIGHT * SCALE);
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);

	public boolean running = false;
	public int tickCount = 0;

	private ArrayList<Bullet> p1Bullets;
	private ArrayList<Bullet> p2Bullets;

	private ArrayList<Tile> tiles;
	private ArrayList<PowerUp> powerUps;
	private TileMap map;

	private Tank p1;
	private Tank p2;

	public InputHandler input;
	public InputHandler input2;

	private HealthBar p1Health;
	private HealthBar p2Health;

	private ArrayList<Explosion> explosions;

	private Sound soundExplosion;
	private Sound powerUpSound;
	private Sound soundExplosion2;
	private Sound music;

	public Camera cam1;
	public Camera cam2;

	public static enum STATE {
		MENU, ONEPLAYER, TWOPLAYER, GAMEOVER, GAMEWON, PLAY
	};

	public static STATE state;
	public static STATE temp;
	public JFrame frame;
	public Sprite title;
	public Menu menu;

	public Game(boolean isApplet) {
		if (!isApplet) {
			setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
			setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

			frame = new JFrame(NAME);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			frame.add(this, BorderLayout.CENTER);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setResizable(false);
		}
	}

	public void init() {
		input = new InputHandler(this);
		input2 = new InputHandler(this);
		this.addMouseListener(new MouseInput(this));

		tiles = new ArrayList<Tile>();
		powerUps = new ArrayList<PowerUp>();

		map = new TileMap("map.txt", "level.png", tiles, powerUps);
		map.loadTiles(32, 32);
		map.loadPowerUps(3);

		p1Bullets = new ArrayList<Bullet>();
		p2Bullets = new ArrayList<Bullet>();

		p1 = new Tank("Tank_blue_basic_strip60.png", 64, 64, input, 1,
				p1Bullets);
		p2 = new Tank("Tank_red_basic_strip60.png", 900, 900, input2, 2,
				p2Bullets);

		cam1 = new Camera(p1.getXOffset(), p1.getYOffset(), 1024, 1024);
		cam2 = new Camera(p2.getXOffset(), p2.getYOffset(), 1024, 1024);

		p1Health = new HealthBar(p1);
		p2Health = new HealthBar(p2);

		explosions = new ArrayList<Explosion>();

		soundExplosion = new Sound("snd_explosion1.wav");
		soundExplosion2 = new Sound("snd_explosion2.wav");
		powerUpSound = new Sound("life_pickup.wav");
		music = new Sound("music.wav");
		menu = new Menu();
		state = STATE.MENU;
		title = ImageLoader.get().getSprtie("Title1.png");

	}

	public synchronized void start() {
		running = true;
		new Thread(this).start();

	}

	public synchronized void stop() {
		running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000.0 / 60.0;

		// int ticks = 0;
		// int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0; // how many nano secs have gone by

		init();
		music.play(true);
		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				// ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				// frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				// System.out.println(ticks + " tikcs, " + frames + " frames");
				// frames = 0;
				// ticks = 0;
			}
		}
		music.stop();
	}

	public void tick() {
		tickCount++;

		if (Game.state == Game.STATE.PLAY) {
			p1.update();
			p2.update();

			cam1.update(p1, 4, 2, 1);
			cam2.update(p2, .75f, 1, 2);

			// player one bullet updates
			for (int i = 0; i < p1Bullets.size(); i++) {
				p1Bullets.get(i).update();
				if (p1Bullets.get(i).remove()) {
					p1Bullets.remove(i);
				}
			}

			// player two bullets update
			for (int i = 0; i < p2Bullets.size(); i++) {
				p2Bullets.get(i).update();
				if (p2Bullets.get(i).remove()) {
					p2Bullets.remove(i);
				}
			}

			// player one collision with blocks
			for (int i = 0; i < tiles.size(); i++) {
				for (int j = 0; j < p1Bullets.size(); j++) {
					if (p1Bullets.get(j).collision(tiles.get(i))
							&& tiles.get(i).remove()) {
						explosions.add(new Explosion(
								"Explosion_small_strip6.png", p1Bullets.get(j)
										.getXOffset(), p1Bullets.get(j)
										.getYOffset(), 1));
						soundExplosion.play(false);
						tiles.remove(i);
						p1Bullets.remove(j);
						break;
					} else if (p1Bullets.get(j).collision(tiles.get(i))) {
						explosions.add(new Explosion(
								"Explosion_small_strip6.png", p1Bullets.get(j)
										.getXOffset(), p1Bullets.get(j)
										.getYOffset(), 1));
						soundExplosion.play(false);
						p1Bullets.remove(j);
						break;
					}
				}
			}

			// player two collision with blocks
			for (int i = 0; i < tiles.size(); i++) {
				for (int j = 0; j < p2Bullets.size(); j++) {
					if (p2Bullets.get(j).collision(tiles.get(i))
							&& tiles.get(i).remove()) {
						explosions.add(new Explosion(
								"Explosion_small_strip6.png", p2Bullets.get(j)
										.getXOffset(), p2Bullets.get(j)
										.getYOffset(), 1));
						soundExplosion.play(false);
						tiles.remove(i);
						p2Bullets.remove(j);
						break;
					} else if (p2Bullets.get(j).collision(tiles.get(i))) {
						explosions.add(new Explosion(
								"Explosion_small_strip6.png", p2Bullets.get(j)
										.getXOffset(), p2Bullets.get(j)
										.getYOffset(), 1));
						soundExplosion.play(false);
						p2Bullets.remove(j);
						break;
					}
				}
			}

			// player one bullet collision with player two
			for (int i = 0; i < p1Bullets.size(); i++) {
				if (p2.collision(p1Bullets.get(i)) && !p2.isRecovering()) {
					p2.setHealth(p2.getHealth() - p1Bullets.get(i).getDamage());
					explosions.add(new Explosion("Explosion_small_strip6.png",
							p1Bullets.get(i).getXOffset(), p1Bullets.get(i)
									.getYOffset(), 1));
					soundExplosion.play(false);
					p1Bullets.remove(i);
					if (p2.getHealth() <= 0) {
						explosions.add(new Explosion(
								"Explosion_large_strip7.png", p2.getXOffset(),
								p2.getYOffset(), 2));
						soundExplosion2.play(false);
						p1.setScore(p1.getScore() + 1);
					}
					break;
				}
			}

			// player two bullet collision with player one
			for (int i = 0; i < p2Bullets.size(); i++) {
				if (p1.collision(p2Bullets.get(i)) && !p1.isRecovering()) {
					p1.setHealth(p1.getHealth() - p2Bullets.get(i).getDamage());
					explosions.add(new Explosion("Explosion_small_strip6.png",
							p2Bullets.get(i).getXOffset(), p2Bullets.get(i)
									.getYOffset(), 1));
					soundExplosion.play(false);
					p2Bullets.remove(i);
					if (p1.getHealth() <= 0) {
						p2.setScore(p2.getScore() + 1);
						explosions.add(new Explosion(
								"Explosion_large_strip7.png", p1.getXOffset(),
								p1.getYOffset(), 2));
						soundExplosion2.play(false);
					}
					break;
				}
			}

			// player one collision with power ups
			for (int i = 0; i < powerUps.size(); i++) {
				if (p1.collision(powerUps.get(i))) {
					powerUpSound.play(false);
					p1.setPowerUp(powerUps.get(i).getType());
					powerUps.remove(i);
					break;
				}
			}

			// player two collision with power ups
			for (int i = 0; i < powerUps.size(); i++) {
				if (p2.collision(powerUps.get(i))) {
					powerUpSound.play(false);
					p2.setPowerUp(powerUps.get(i).getType());
					powerUps.remove(i);
					break;
				}
			}

			// player one collision with blocks
			for (int i = 0; i < tiles.size(); i++) {
				if (p1.collision(tiles.get(i))) {
					p1.speed = -10;
					p1.ySpeed = -10;
					break;
				} else if ((p1.speed <= 0 && p1.ySpeed <= 0 && p1
						.collision(tiles.get(i)))
						|| (p1.speed <= 0 && p1.ySpeed <= 0 && !p1
								.collision(tiles.get(i)))) {
					p1.speed = 4;
					p1.ySpeed = 4;
					break;
				}
			}

			// player two collision with blocks
			for (int i = 0; i < tiles.size(); i++) {
				if (p2.collision(tiles.get(i))) {
					p2.speed = -10;
					p2.ySpeed = -10;
					break;
				} else if ((p2.speed <= 0 && p2.ySpeed <= 0 && p2
						.collision(tiles.get(i)))
						|| (p2.speed <= 0 && p2.ySpeed <= 0 && !p2
								.collision(tiles.get(i)))) {
					p2.speed = 4;
					p2.ySpeed = 4;
					break;
				}
			}

			// update explosions
			for (int i = 0; i < explosions.size(); i++) {
				explosions.get(i).update();
				if (explosions.get(i).remove()) {
					explosions.remove(i);
				}
			}

			// update powerups
			for (int i = 0; i < powerUps.size(); i++) {
				if (powerUps.get(i).remove()) {
					powerUps.remove(i);
				}
			}

			// reload powerups
			if (powerUps.isEmpty()) {
				map.loadPowerUps(3);
			}
		}

	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		Graphics2D g2d = (Graphics2D) g;

		if (Game.state == Game.STATE.MENU) {
			g.drawImage(title.getImage(), 5, 0, null);
			menu.draw(g);

		}

		else if (Game.state == Game.STATE.ONEPLAYER) {
			this.stop();
			map.drawBackground(g);
			menu.drawPlayerOne(g);

		}

		else if (Game.state == Game.STATE.TWOPLAYER) {
			this.stop();
			map.drawBackground(g);
			menu.drawPlayerTwo(g);
		}

		else if (Game.state == Game.STATE.PLAY) {
			// player one view window
			g2d.setClip(0, 0, (Game.WIDTH * Game.SCALE) / 2, Game.HEIGHT
					* Game.SCALE + 10);

			g2d.translate(cam1.getX(), cam1.getY());

			map.drawBackground(g);
			p1.draw(g);
			p2.draw(g);

			// draw player one bullets
			for (int i = 0; i < p1Bullets.size(); i++) {
				p1Bullets.get(i).draw(g);
			}

			// draw player two bullets
			for (int i = 0; i < p2Bullets.size(); i++) {
				p2Bullets.get(i).draw(g);
			}

			// draw explosion
			for (int i = 0; i < explosions.size(); i++) {
				explosions.get(i).draw(g);
			}

			// draw power ups
			for (int i = 0; i < powerUps.size(); i++) {
				powerUps.get(i).draw(g);
			}

			g2d.translate(-cam1.getX(), -cam1.getY());

			// player one health and lives
			p1Health.draw(g, 5, 5, 25);
			p1Health.drawLives(g, 5, 30);

			// player one score
			g.setFont(new Font("Century Gothic", Font.BOLD, 18));
			String s = "SCORE: " + p1.getScore();
			g.setColor(Color.black);
			g.drawString(s, 225, 25);

			// player two view window
			g2d.setClip((Game.WIDTH * Game.SCALE) / 2, 0,
					(Game.WIDTH * Game.SCALE) / 2 + 10, Game.HEIGHT
							* Game.SCALE + 10);
			g2d.translate(cam2.getX(), cam2.getY());

			map.drawBackground(g);
			p2.draw(g);
			p1.draw(g);

			// draw player one bullets
			for (int i = 0; i < p1Bullets.size(); i++) {
				p1Bullets.get(i).draw(g);
			}

			// draw player two bullets
			for (int i = 0; i < p2Bullets.size(); i++) {
				p2Bullets.get(i).draw(g);
			}

			// draw explosion
			for (int i = 0; i < explosions.size(); i++) {
				explosions.get(i).draw(g);
			}

			// draw power ups
			for (int i = 0; i < powerUps.size(); i++) {
				powerUps.get(i).draw(g);
			}

			g2d.translate(-cam2.getX(), -cam2.getY());

			// player two health
			p2Health.draw(g, (WIDTH * SCALE) / 2 + 5, 5, 25);
			p2Health.drawLives(g, (WIDTH * SCALE) / 2 + 5, 30);

			// player two score
			g.setFont(new Font("Century Gothic", Font.BOLD, 18));
			String s2 = "SCORE: " + p2.getScore();
			g.setColor(Color.black);
			g.drawString(s2, (WIDTH * SCALE) / 2 + 225, 25);

			// mini map
			g2d.setClip(0, 0, (Game.WIDTH * Game.SCALE) + 1000, Game.HEIGHT
					* Game.SCALE + 1000);

			AffineTransform tx = scale(.1, .1);
			g2d.setTransform(tx);
			g2d.translate(3550, 4910);

			map.drawBackground(g);
			p1.draw(g);
			p2.draw(g);

			// draw power ups
			for (int i = 0; i < powerUps.size(); i++) {
				powerUps.get(i).draw(g);
			}
			g2d.translate(-3550, -4910);

		}
		g.dispose();
		bs.show();

	}

	public void checkGameStatus() {

	}

	public static AffineTransform scale(double scalex, double scaley) {
		AffineTransform tx = new AffineTransform();
		tx.scale(scalex, scaley);
		return tx;
	}

	public static void main(String[] args) {
		new Game(false).start();
	}
}
