package artillery;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject  
{
	protected int health;
	
	protected int xOffset;
	
	protected int yOffset;
	
	protected int speed;
	
	protected Sprite sprite; 
	
	public Rectangle me = new Rectangle(); 
	
	public Rectangle him = new Rectangle(); 
	
	public GameObject(String ref, int xOffset, int yOffset)
	{
		this.sprite = ImageLoader.get().getSprtie(ref);
		
		
		this.xOffset = xOffset;
		
		this.yOffset = yOffset;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public int getXOffset()
	{
		return xOffset;
	}
	
	public void setXOffset(int xOffset)
	{
		this.xOffset = xOffset;
	}
	
	public int getYOffset()
	{
		return yOffset;
	}
	
	public void setYOffset(int yOffset)
	{
		this.yOffset = yOffset;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	//game object collides with another game object return true, else false 
	public boolean collision(GameObject other)
	{
		me.setBounds(xOffset, yOffset, sprite.getWidth(), sprite.getHeight());
		him.setBounds(other.xOffset, other.yOffset, other.sprite.getWidth(), other.sprite.getHeight());
		return me.intersects(him); 
	}
	
	public abstract void draw(Graphics g);
	
	public abstract boolean remove(); 
	
	public abstract void update();
	
	public abstract void move(); 
	

}

