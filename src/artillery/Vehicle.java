package artillery;

public abstract class Vehicle extends GameObject
{
	protected int health;
	
	Vehicle(String ref, int xOffset, int yOffset)
	{
		super(ref, xOffset, yOffset);
	}
	
	public int getHealth()
	{
		return this.health;
	}
	
	public void setHealth(int health)
	{
		this.health = health;
	}
	
	public abstract void shoot(); 
}
