package artillery;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	public Rectangle play = new Rectangle(Game.WIDTH/2 + 210, 250, 200, 50);
	public Rectangle quit = new Rectangle(Game.WIDTH/2 + 210, 350, 200, 50);
	public Rectangle restart = new Rectangle(Game.WIDTH/2 + 210, 250, 200, 50);

	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		Font font1 = new Font("Impact", Font.BOLD, 50);
		g.setFont(font1);
		g.setColor(Color.WHITE);
		g.drawString("Tank Wars", Game.WIDTH/2 +210, 100);
		
		Font font2 = new Font("Impact", Font.BOLD, 30);
		g.setFont(font2);
		
		g.drawString("Play", play.x+75, play.y+35); 
		g.drawString("Quit", quit.x+75, quit.y+35); 
		
		g2d.draw(play);
		g2d.draw(quit);

		
	}
	
	
	public void drawPlayerOne(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Impact", Font.BOLD, 30);
		g.setColor(Color.GREEN);
		g.setFont(font);
		
		g.drawString("PLAYER ONE WINS", (Game.WIDTH*Game.SCALE)/2 - 125, 100); 
		g.drawString("Restart", restart.x+55, restart.y+35); 
		g.drawString("Quit", quit.x+75, quit.y+35); 
		
		g2d.draw(restart);
		g2d.draw(quit);
	}
	
	public void drawPlayerTwo(Graphics g){
		
		Graphics2D g2d = (Graphics2D) g;
		Font font = new Font("Impact", Font.BOLD, 30);
		g.setColor(Color.GREEN);
		g.setFont(font);
		
		g.drawString("PLAYER TWO WINS", (Game.WIDTH*Game.SCALE)/2 - 125, 100);
		g.drawString("Restart", restart.x+55, restart.y+35); 
		g.drawString("Quit", quit.x+75, quit.y+35); 
		
		g2d.draw(restart);
		g2d.draw(quit);
		
		
	}
}