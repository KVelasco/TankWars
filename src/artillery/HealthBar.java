package artillery;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {
	
	private Tank tank;
	private int startHealth;
	private Sprite life = ImageLoader.get().getSprtie("life.png");
	public HealthBar(Tank tank){
		this.tank = tank; 
		this.startHealth = this.tank.getHealth()*4;
	}
	
	public void draw(Graphics g, int x, int y, int height){
		g.setColor(Color.gray);
		g.fillRect(x, y, startHealth, height);
		
		g.setColor(Color.green);
		g.fillRect(x, y, tank.getHealth()*4, height);
		
		g.setColor(Color.gray);
		g.drawRect(x, y, startHealth, height);
	}
	
	public void drawLives(Graphics g, int x, int y){
		for(int i = 0; i < tank.getLives(); i++){
			g.drawImage(life.getImage(), x+(i*35), y, null);
		}
	}
	
	

}
