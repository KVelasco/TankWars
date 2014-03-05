package artillery;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	private Game game;

	public MouseInput(Game game) {
		this.game = game;
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
	
		int mx = e.getX();
		int my = e.getY();

	
		if (Game.state == Game.STATE.MENU) {

			// play game
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.PLAY;
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}

		}

		//player one wins
		else if (Game.state == Game.STATE.ONEPLAYER) {

			// restart game
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.PLAY;
					game.start();
					
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}
		}

		//player two won
		else if (Game.state == Game.STATE.TWOPLAYER) {

			// back to main menu
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 250 && my <= 300) {
					Game.state = Game.STATE.PLAY;
					game.start();
				}
			}

			// quit game
			if (mx >= Game.WIDTH / 2 + 210 && mx <= Game.WIDTH/2 + 410) {
				if (my >= 350 && my <= 400) {
					System.exit(1);
				}
			}
		}

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
