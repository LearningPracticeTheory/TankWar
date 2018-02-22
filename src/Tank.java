import java.awt.*;
import java.awt.event.*;

public class Tank {
	
	private int x, y;
	TankClient tc = null;
	
	public Tank(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP :
			y -= 8;
			break;
		case KeyEvent.VK_DOWN :
			y += 8;
			break;
		case KeyEvent.VK_LEFT :
			x -= 8;
			break;
		case KeyEvent.VK_RIGHT :
			x += 8;
			break;
		}
	}
	
}
