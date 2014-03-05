package artillery;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}

	public class Key {
		private boolean pressed = false;

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
		}
	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key fire = new Key();

	public Key up2 = new Key();
	public Key down2 = new Key();
	public Key left2 = new Key();
	public Key right2 = new Key();
	public Key fire2 = new Key();
	
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void toggleKey(int keyCode, boolean isPressed) {

		// Player One Input
		if (keyCode == KeyEvent.VK_W) {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S) {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A) {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D) {
			right.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_SPACE) {
			fire.toggle(isPressed);
		}
		

		// Player Two Input
		if (keyCode == KeyEvent.VK_UP) {
			up2.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			left2.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			down2.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			right2.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_ENTER) {
			fire2.toggle(isPressed);
		}
	}

}
