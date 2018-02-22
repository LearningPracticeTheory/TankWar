import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;
/**
 * 坦克
 * 使用圆进行模拟
 * @author Tank
 *
 */
public class Tank {
	/**
	 * 坦克的宽度
	 */
	private static final int WIDTH = 30;
	/**
	 * 坦克的宽度
	 */
	private static final int HEIGHT = 30;
	/**
	 * 坦克的速度
	 */
	private static final int SPEED = parseInt("tankSpeed");
	/**
	 * 敌人坦克的运动级别
	 */
	private final int AI_MOVE_LEVEL = parseInt("aiMoveLevel");//1~9
	/**
	 * 敌人坦克的火力级别
	 */
	private final int AI_FIRE_LEVEL = parseInt("aiFireLevel");
	/**
	 * 重生坦克的数量
	 */
	private final int TANKS_NUM = parseInt("tanksRebornNum");
	/**
	 * 主战坦克的生命值
	 */
	private final int MY_TANK_LIFE = parseInt("myTankLife");
	private int life = MY_TANK_LIFE;
	private int x, y;
	private int oldX, oldY;

	private boolean live = true;
	private boolean good; 
	boolean bU = false, bD = false, bL = false, bR = false;

	TankClient tc = null;
	GunBarrel gb = null;
	private BloodBar bb = new BloodBar();
	
	enum Direction {U, UL, UR, D, DL, DR, L, R, STOP};
	Direction dir = Direction.STOP;
	
	private static Random r = new Random();
	private static Direction dirs[] = Direction.values();
	
	/**
	 * 初始化坦克
	 * @param x 坦克的横坐标
	 * @param y 坦克的纵坐标
	 * @param good 坦克的好坏
	 * @param tc TankClient 的引用
	 */
	public Tank(int x, int y, boolean good, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.good = good;
		gb = new GunBarrel(x + WIDTH / 2, y + HEIGHT / 2, this);
		oldX = x;
		oldY = y;
	}

	/**
	 * 根据好坏画不同的坦克
	 * @param g 画笔
	 * @see java.awt.Graphics
	 */
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
		
		if(gb != null) {
			gb.draw(x + WIDTH / 2, y + HEIGHT / 2, g);
		}
		
		AIDirection();
		AIFire();
		move();
	}

	/**
	 * 敌人坦克自动开火
	 */
	public void AIFire() {
		if(!good && r.nextInt(10) < AI_FIRE_LEVEL) {
			tc.missiles.add(fire());
		}
	}
	
	/**
	 * 开火并 new 炮弹
	 * @return Missile 炮弹
	 * @see Missile
	 */
	public Missile fire() {
		int x = this.x + WIDTH / 2 - Missile.getWidth() / 2;
		int y = this.y + HEIGHT / 2 - Missile.getHeight() / 2;
		if(good) {
			return new Missile(x, y, true, gb.dir, tc);
		}
		return new Missile(x, y, false, gb.dir, tc);
	}
	
	/**
	 * A 型炮弹
	 */
	public void AFire() {
		int x = this.x + WIDTH / 2 - Missile.getWidth() / 2;
		int y = this.y + HEIGHT / 2 - Missile.getHeight() / 2;
		for(int i = 0; i < dirs.length - 1; i++) {
			tc.missiles.add(new Missile(x, y, true, dirs[i], tc));
		}
	}
	
	/**
	 * 敌人坦克移动方向
	 */
	public void AIDirection() {
		if(!good && r.nextInt(10) < AI_MOVE_LEVEL) {
			dir = dirs[r.nextInt(dirs.length)];
		}
	}
	
	/**
	 * 根据按键的不同确定方向
	 */
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
	
	/**
	 * 控制坦克移动以及出界处理
	 */
	public void move() {
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
	
	/**
	 * 敌人坦克重生
	 */
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
	
	/**
	 * 检测坦克撞一系列的墙
	 * @param walls 一系列的墙
	 * @return boolean 是否撞上
	 */
	public boolean intersectInWalls(java.util.List<Wall> walls) {
		for(int i = 0; i < walls.size(); i++) {
			if(this.getRect().intersects(walls.get(i).getRect())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 坦克之间的碰撞检测
	 * @param tanks 一个连的坦克
	 * @return boolean 是否相撞
	 */
	public boolean intersectInTanks(java.util.List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if(this != t && this.getRect().intersects(t.getRect())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 记录坦克上一步的位置
	 */
	public void backStep() {
		x = oldX;
		y = oldY;
	}
	
	/**
	 * 撞一堵墙
	 * @param w 墙壁
	 * @return boolean 是否撞上
	 * @see Wall
	 */
	public boolean collideWithWall(Wall w) {
		if(this.getRect().intersects(w.getRect())) {
			backStep();
			return true;
		}
		return false;
	}
	
	/**
	 * 与一个连的坦克进行碰撞检测
	 * @param tanks 一个连的坦克
	 * @return boolean 是否与某辆坦克相撞
	 */
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

	/**
	 * 处理按键消息
	 * @param e 键盘事件
	 * @see java.awt.event.KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
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
	
	/**
	 * 处理抬键消息
	 * @param e 键盘事件
	 * @see java.awt.event.KeyEvent
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL :
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

	/**
	 * 与血块进行碰撞检测
	 * @param b 血块
	 * @return boolean 是否碰撞
	 * @see Blood
	 */
	public boolean eatBlood(Blood b) {
		if(this.isGood() && b.isLive() && this.getRect().intersects(b.getRect())) {
			b.setLive(false);
			this.life = MY_TANK_LIFE;
			return true;
		}
		return false;
	}
	
	/**
	 * 主战坦克血条
	 * @author PianoLion
	 * @see java.awt.Graphics
	 */
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
	
	/**
	 * 获取当前位置的矩形方块--坦克
	 * @return Rectangle 的对象
	 * @see java.awt.Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 读取并转换配置文件的内容
	 * @param key 配置文件对应的 Key 值
	 * @return key 对应的 Value
	 * @See PropertiesManage
	 */
	private static int parseInt(String key) {
		return Integer.parseInt(PropertiesManage.getProperty(key));
	}
	
	/**
	 * 获取坦克的速度
	 * @return SPEED 坦克的速度
	 */
	public static int getSpeed() {
		return SPEED;
	}

	/**
	 * 获取坦克的宽度
	 * @return WIDTH 坦克的宽度
	 */
	public static int getWidth() {
		return WIDTH;
	}

	/**
	 * 获取坦克的高度
	 * @return HEIGHT 坦克的高度
	 */
	public static int getHeight() {
		return HEIGHT;
	}

	/**
	 * 判断坦克生死
	 * @return boolean 生为 true，否则为 false
	 */
	public boolean isLive() {
		return live;
	}

	/**
	 * 设置坦克的生死
	 * @param live 生为 true，否则为 false
	 */
	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 判断坦克的好坏
	 * @return true 为好，否则为坏
	 */
	public boolean isGood() {
		return good;
	}

	/**
	 * 获取主战坦克的生命值
	 * @return life 生命值
	 */
	public int getLife() {
		return life;
	}

	/**
	 * 设置主战坦克的生命值
	 * @param life 生命值
	 */
	public void setLife(int life) {
		this.life = life;
	}

}