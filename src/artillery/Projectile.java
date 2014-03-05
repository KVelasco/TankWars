package artillery;

public abstract class Projectile extends GameObject
{
	protected int damage; 
	
	Projectile(String ref, int xOffset, int yOffset)
	{
		super(ref, xOffset, yOffset);
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
}
