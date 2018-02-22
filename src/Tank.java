import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

class Tank {	 
	private static final int WIDTH = 30;
	private static final int HEIGHT = 30;
	private static final int SPEED = parseInt("tankSpeed");
	private final int AI_MOVE_LEVEL = parseInt("aiMoveLevel");//1~9
	private final int AI_FIRE_LEVEL = parseInt("aiFireLevel");
	private final int TANKS_NUM = parseInt("tanksRebornNum");
	private final int MY_TANK_LIFE = parseInt("myTankLife");
	
	private int life = MY_TANK_LIFE;
	
	private int x, y;
	private int oldX, oldY;
	TankClient tc = null;
	GunBarrel gb = null;
	
	private boolean live = true;
	private boolean good; 
	boolean bU = false, bD = false, bL = false, bR = false;
	
	enum Direction {U, UL, UR, D, DL, DR, L, R, STOP};
	Direction dir = Direction.STOP;
	
	private static Random r = new Random();
	private static Direction dirs[] = Direction.values();
	
	private BloodBar bb = new BloodBar();
	
	public Tank(int x, int y, boolean good, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.good = good;
		gb = new GunBarrel(x + WIDTH / 2, y + HEIGHT / 2, this);
		oldX = x;
		oldY = y;
//		tc.addKeyListener(new KeyMonitor());
//System.out.println("gun barrel" + tc.gb);
	}

	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) {
			g.setColor(Color.RED);
			bb.draw(g);
		} else {
			g.setColor(Color.BLUE);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		if(gb != null) gb.draw(x + WIDTH / 2, y + HEIGHT / 2, g);
		AIDirection();
		AIFire();
		move();
	}

	public void AIFire() {
		if(!good && r.nextInt(10) < AI_FIRE_LEVEL) {
			tc.missiles.add(fire());
		}
	}
	
	public Missile fire() {
		int x = this.x + WIDTH / 2 - Missile.getWidth() / 2;
		int y = this.y + HEIGHT / 2 - Missile.getHeight() / 2;
		if(good) {
			return new Missile(x, y, true, gb.dir, tc);
		}
		return new Missile(x, y, false, gb.dir, tc);
	}
	
	public void AFire() {
		int x = this.x + WIDTH / 2 - Missile.getWidth() / 2;
		int y = this.y + HEIGHT / 2 - Missile.getHeight() / 2;
		for(int i = 0; i < dirs.length - 1; i++) {
			tc.missiles.add(new Missile(x, y, true, dirs[i], tc));
		}
	}
	
	public void AIDirection() {
		if(!good && r.nextInt(10) < AI_MOVE_LEVEL) {
			dir = dirs[r.nextInt(dirs.length)];
		}
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
//System.out.println(dir);
		
		oldX = x;
		oldY = y;
		
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
		
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x > tc.GAME_WIDTH - WIDTH) x = tc.GAME_WIDTH - WIDTH;
		if(y > tc.GAME_HEIGHT - HEIGHT) y = tc.GAME_HEIGHT - HEIGHT;
		
	}
	
	public void tankReborn() {
		for(int i = 0; i < TANKS_NUM; i++) {
			int x = r.nextInt(tc.GAME_WIDTH - WIDTH);
			int y = r.nextInt(tc.GAME_HEIGHT - HEIGHT);
			Tank t = new Tank(x, y, false, tc);
			if(!t.intersectInWalls(tc.walls) && !t.intersectInTanks(tc.tanks)) {
				tc.tanks.add(t);
			} else {
				i--;
			}
		}
	}
	
	public boolean intersectInWalls(java.util.List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(this.getRect().intersects(walls.get(i).getRect())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean intersectInTanks(java.util.List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t && this.getRect().intersects(t.getRect())) {
				return true;
			}
		}
		return false;
	}
	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		switch(key) {
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
		case KeyEvent.VK_CONTROL :
//			tc.m = fire();
			if(live) {
				tc.missiles.add(fire());
			}
			break;
		case KeyEvent.VK_A :
			if(live) {
				AFire();
			}
			break;
		case KeyEvent.VK_F2 :
			if(tc.tanks.size() == 0) {
				tankReborn();
			}
			break;
		case KeyEvent.VK_F5 :
			if(tc.myTank.getLife() <= 0) {
				tc.myTank.setLive(true);
				tc.myTank.setLife(100);
			}
			break;
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

	public void backStep() {
		x = oldX;
		y = oldY;
	}
	
	public boolean collideWithWall(Wall w) {
		if(this.getRect().intersects(w.getRect())) {
//			dir = Direction.STOP;
			backStep();
			return true;
		}
		return false;
	}
	
	public boolean collideWithTanks(java.util.List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(t != this && t.isLive() && this.getRect().intersects(t.getRect())) {
				this.backStep();
				t.backStep();
				return true;
			}
		}
		return false;
	}
	
	public boolean eatBlood(Blood b) {
		if(this.isGood() && b.isLive() && this.getRect().intersects(b.getRect())) {
			b.setLive(false);
			this.life = MY_TANK_LIFE;
			return true;
		}
		return false;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y - 12, WIDTH, 10);
			int bloodWidth = WIDTH * life / MY_TANK_LIFE;
			g.fillRect(x, y - 12, bloodWidth, 10);
			g.setColor(c);
		}
	}
	
	private static int parseInt(String key) {
		return Integer.parseInt(PropertiesManage.getProperty(key));
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

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

}
