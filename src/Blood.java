import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 血块
 * 使用方块进行模拟
 * @author PianoLion
 *
 */
public class Blood {
	
	/**
	 * 血块的宽度
	 */
	private static final int WIDTH = 15;
	/**
	 * 血块的高度
	 */
	private static final int HEIGHT = 15;
	/**
	 * 血块运动的横纵坐标
	 */
	private int x, y;
	/**
	 * 记录血块运动的步伐
	 */
	private int step = 0;
	/**
	 * 血块运动的轨迹，使用二维整型数组代表位置 pos(x, y);
	 */
	private int pos[][] = {{300, 300}, {350, 300}, {350, 350}, {300, 350}};
	
	TankClient tc;
	/**
	 * 控制血块的生死/是否存活
	 */
	private boolean live = true;
	/**
	 * 初始化血块
	 * @param tc TankClient 的对象引用
	 */
	public Blood(TankClient tc) {
		this.tc = tc;
	}
	/**
	 * 位置运动/变动
	 */
	private void position() {
		if(step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}
	/**
	 * 画血块并控制血块周期运动
	 * @param g TankClient 传递的画笔
	 * @see Graphics
	 */
	public void draw(Graphics g) {
		if(!live) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		position();
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		step++;
	}
	
	/**
	 * 获取当前位置的矩形方块--血块
	 * @return Rectangle 的对象
	 * @see java.awt.Rectangle
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	/**
	 * 判断血块是否存活
	 * @return boolean，存活 return true, 否则 return false
	 */
	public boolean isLive() {
		return live;
	}
	/**
	 * 设置血块的生死
	 * @param live boolean true 为活着，否则 false 
	 */
	public void setLive(boolean live) {
		this.live = live;
	}
	
}