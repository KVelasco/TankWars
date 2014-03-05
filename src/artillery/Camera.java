package artillery;

public class Camera {

	private float x;
	private float y;
	
	private int width;
	private int height;

	public Camera(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = width;
	}

	public void bound(int offset, int player) {
		if (player == 1) {
			if (x > 0)
				x = 0;
			if (y > 0)
				y = 0;
			if (x < -(width - ((Game.WIDTH * Game.SCALE))
					/ offset))
				x = -(width - ((Game.WIDTH * Game.SCALE) / offset));
			if (y <   -(height - Game.HEIGHT * Game.SCALE)+10)
				y =   -(height - Game.HEIGHT * Game.SCALE)+10;
		} 
		
		else if (player == 2) {
			if (x > 400)
				x = 400;
			if (y > 0)
				y = 0;
			if (x < -(width - ((Game.WIDTH * Game.SCALE))
					/ offset)+10)
				x = -(width - ((Game.WIDTH * Game.SCALE) / offset))+10;
			if (y < -(width - Game.HEIGHT * Game.SCALE)+10)
				y = -(width - Game.HEIGHT * Game.SCALE)+10;
		}
	}

	public void update(Tank tank, float offset, int offset2, int player) {
		this.move(tank, offset, player);
		this.bound(offset2, player);

	}

	public void move(Tank tank, float offset, int player) {
		if(player == 1){
			x = -tank.getXOffset() + (Game.WIDTH * Game.SCALE) / offset;
			y = -tank.getYOffset() + (Game.HEIGHT * Game.SCALE) / offset;
		}else if (player == 2){
			x = -tank.getXOffset() + (Game.WIDTH * Game.SCALE) * offset;
			y = -tank.getYOffset() + (Game.HEIGHT * Game.SCALE) * offset;
		}
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
