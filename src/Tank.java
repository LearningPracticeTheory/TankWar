import java.awt.*;
import java.awt.event.*;

class Tank {
	
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final int SPEED = 10;
	
	private int x, y;
	TankClient tc = null;
	GunBarrel gb = null;
	
	boolean bU = false, bD = false, bL = false, bR = false;
	
	enum Direction {U, UL, UR, D, DL, DR, L, R, STOP};
	Direction dir = Direction.STOP;
	
	public Tank(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		gb = new GunBarrel(x + WIDTH / 2, y + HEIGHT / 2, this);
//System.out.println("gun barrel" + tc.gb);
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		if(gb != null) gb.draw(x + WIDTH / 2, y + HEIGHT / 2, g);
		move();
	}

	public Missile fire() {
		int x = this.x + WIDTH / 2 - Missile.getWidth() / 2;
		int y = this.y + HEIGHT / 2 - Missile.getHeight() / 2;
		return new Missile(x, y, gb.dir);
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL :
//			tc.m = fire();
			tc.missiles.add(fire());
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		}
		direction();
		
	}
	
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		}
		direction();

		
	}

	public void direction() {
		if(bU && !bD && !bL && !bR) dir = Direction.U;
		else if(bU && !bD && bL && !bR) dir = Direction.UL;
		else if(bU && !bD && !bL && bR) dir = Direction.UR;
		else if(!bU && bD && !bL && !bR) dir = Direction.D;
		else if(!bU && bD && bL && !bR) dir = Direction.DL;
		else if(!bU && bD && !bL && bR) dir = Direction.DR;
		else if(!bU && !bD && bL && !bR) dir = Direction.L;
		else if(!bU && !bD && !bL && bR) dir = Direction.R;
		else if(!bU && !bD && !bL && !bR) dir = Direction.STOP;
	}
	
	public void move() {
		switch(dir) {
		case U :
			y -= SPEED;
			break;
		case UL :
			x -= SPEED;
			y -= SPEED;
			break;
		case UR :
			x += SPEED;
			y -= SPEED;
			break;
		case D :
			y += SPEED;
			break;
		case DL :
			x -= SPEED;
			y += SPEED;
			break;
		case DR :
			x += SPEED;
			y += SPEED;
			break;
		case L :
			x -= SPEED;
			break;
		case R :
			x += SPEED;
			break;
		case STOP :
			break;
		}
	}

	public static int getSpeed() {
		return SPEED;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	
}
