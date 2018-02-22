import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
/**
 * 炮弹
 * 用圆代替
 * @author PianoLion
 *
 */
public class Missile {
	/**
	 * 炮弹的宽度
	 */
	private static final int WIDTH = 10;
	/**
	 * 炮弹的高度
	 */
	private static final int HEIGHT = 10;
	/**
	 * 炮弹的横纵坐标
	 */
	private int x, y;
	/**
	 * 炮弹的速度，根据 Tank 的 2 倍速度确立
	 */
	private static final int SPEED = Tank.getSpeed() * 2;
	/**
	 * 炮弹的生死
	 */
	private boolean live = true;
	/**
	 * 炮弹的好坏
	 */
	private boolean good;
	/**
	 * 炮弹的方向
	 */
	Tank.Direction dir = null;
	TankClient tc = null;
	/**
	 * 初始化炮弹位置以及方向
	 * @param x 炮弹的横坐标
	 * @param y	炮弹的纵坐标
	 * @param dir 炮弹的方向
	 */
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	/**
	 * 重载，添加好坏的变量 
	 * @param x 炮弹的横坐标
	 * @param y 炮弹的纵坐标
	 * @param good 炮弹的好坏
	 * @param dir 炮弹的方向
	 * @param tc TankClient 的引用
	 * @see #Missile(int x, int y, Tank.Direction dir)
	 */
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	/**
	 * 画炮弹并控制炮弹的移动轨迹
	 * @param g TankClient 传递的画笔
	 * @see java.awt.Graphics
	 */
	public void draw(Graphics g) {
		
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		if(good) {
			g.setColor(new Color(255, 200, 255));//Pink
		} else {
			g.setColor(Color.GREEN);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	/**
	 * 炮弹移动的轨迹，根据 dir 判断移动的方向并判断是否出界
	 */
	private void move() {
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
		
		if(x <= 0 || y <= 0 || x >= tc.GAME_WIDTH || y >= tc.GAME_HEIGHT) {
			this.setLive(false);
		}

	}
	
	/**
	 * 获取当前位置的矩形方块--炮弹
	 * @return Rectangle 的对象
	 * @see java.awt.Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	/**
	 * 一颗 Missile 打击一辆 Tank
	 * @param t 所要打击的 Tank
	 * @return boolean 是否 hit
	 * @see Tank
	 */
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive() && t.isGood() != this.isGood()) {
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			if(t.isGood()) {
				t.setLife(t.getLife() - 20);
				if(t.getLife() <= 0) {
					t.setLive(false);
				}
			} else {
				t.setLive(false);
			}
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 一颗 Missile 打击一个连的  Tanks 
	 * @param tanks 一个连的 Tanks
	 * @return boolean 是否 hit 一个连的某辆 Tank
	 */
	public boolean hitTanks(List<Tank> tanks) {
		for(int i = 0; i < tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 打到墙上
	 * @param w 所要击打的墙
	 * @return boolean 是否 hit
	 * @see Wall
	 */
	public boolean hitWall(Wall w) {
		if(this.getRect().intersects(w.getRect())) {
			this.setLive(false);
			return true;
		}
		return false;
	}
	
	/**
	 * 获取炮弹的宽度
	 * @return WIDTH 炮弹的宽度
	 */
	public static int getWidth() {
		return WIDTH;
	}

	/**
	 * 获取炮弹的高度
	 * @return HEIGHT 炮弹的高度
	 */
	public static int getHeight() {
		return HEIGHT;
	}

	/**
	 * 查看炮弹生死
	 * @return live 炮弹生死
	 */
	public boolean isLive() {
		return live;
	}
	
	/**
	 * 设置炮弹的生死
	 * @param live true 为或者，否则为 false
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	
	/**
	 * 查看炮弹的好坏 
	 * @return boolean 炮弹的好坏
	 */
	public boolean isGood() {
		return good;
	}
	
}