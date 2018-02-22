import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 墙壁
 * @author PianoLion
 *
 */
public class Wall {
	/**
	 * 墙的位置大小
	 */
	private int x, y, w, h;
	TankClient tc;

	/**
	 * 初始化墙的位置以及大小
	 * @param x 墙的横坐标
	 * @param y	墙的纵坐标
	 * @param w 墙的宽度
	 * @param h	墙的高度
	 * @param tc TankClient 的对象引用
	 */
	public Wall(int x, int y, int w, int h, TankClient tc) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	
	/**
	 * 画墙
	 * @param g TankClient 传递的画笔 
	 */
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	
	/**
	 * 获取当前位置的矩形方块--墙
	 * @return Rectangle 的对象
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
}