import java.awt.Color;
import java.awt.Graphics;
/**
 * 爆炸
 * 使用不同大小的圆模拟
 * @author PianoLion
 *
 */
public class Explode {
	
	private int x, y;
	TankClient tc;
	/**
	 * 爆炸模拟圆的直径
	 */
	private int diameters[] = {4, 7, 12, 20, 30, 49, 40, 20, 10, 6};
	/**
	 * 记录爆炸的步伐
	 */
	private int step = 0;
	/**
	 * 初始化爆炸
	 * @param x 爆炸点的横坐标
	 * @param y	爆炸点的纵坐标
	 * @param tc TankClient 的对象引用
	 */
	public Explode(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	/**
	 * 画爆炸并控制爆炸圆大小
	 * @param g TankClient 传递的画笔
	 * @see java.awt.Graphics
	 */
	public void draw(Graphics g) {
		if(step == diameters.length) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		for(int i = 0; i < diameters.length; i++) {
			g.fillOval(x, y, diameters[step], diameters[step]);
		}
		step++;
		g.setColor(c);
	}
	
}