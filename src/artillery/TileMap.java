package artillery;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileMap {

	private int[][] map;
	private int mapHeight;
	private int mapWidth;
	private ArrayList<Tile> tiles;
	private ArrayList<PowerUp> powerUps;
	private PowerUp temp;
	private Sprite background;

	public TileMap(String ref, String ref2, ArrayList<Tile> tiles,
			ArrayList<PowerUp> powerUps) {
		this.tiles = tiles;
		this.powerUps = powerUps;
		background = ImageLoader.get().getSprtie(ref2);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					Game.class.getClassLoader().getResourceAsStream(ref)));

			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];
			String delimiters = " ";
			for (int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for (int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		} catch (Exception e) {
		}
	}

	public void loadTiles(int width, int height) {
		for (int row = 0; row < mapHeight; row++) {
			for (int col = 0; col < mapWidth; col++) {
				int rc = map[row][col];
				if (rc == 1) {
					tiles.add(new Tile("Blue_wall1.png", col * width, row
							* height, false));
				}
				if (rc == 2) {
					tiles.add(new Tile("Blue_wall2.png", col * width, row
							* height, true));
				}
			}
		}
	}

	public void loadPowerUps(int max) {

		for (int i = 0; i < tiles.size(); i++) {
			int rand = PowerUp.randomInt(1, 2);
			if (rand == 1) {
				temp = new PowerUp("powerup1.png", PowerUp.randomInt(0, 1024),
						PowerUp.randomInt(0, 1024), 0, 1);
				if (!temp.collision(tiles.get(i)) && powerUps.size() != max) {
					powerUps.add(temp);
				}

			} else if (rand == 2) {
				temp = new PowerUp("powerup2.png", PowerUp.randomInt(0, 1024),
						PowerUp.randomInt(0, 1024), 0, 2);
				if (!temp.collision(tiles.get(i)) && powerUps.size() != max) {
					powerUps.add(temp);

				}

			}
		}

	}

	public void drawBackground(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, background.getWidth(),
				background.getHeight(), null);
		this.drawTiles(g);

	}

	public void drawTiles(Graphics g) {
		for (int i = 0; i < tiles.size(); i++) {
			tiles.get(i).draw(g);
		}
	}
}
