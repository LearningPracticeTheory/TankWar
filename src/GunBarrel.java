import java.awt.Color;
import java.awt.Graphics;
/**
 * 炮筒
 * 使用直线模拟
 * @author PianoLion
 *
 */
public class GunBarrel {
	/**
	 * 炮筒的中心以及炮口位置
	 */
	private int x0, y0, x1, y1;
	/**
	 * 所属 Tank
	 */
	Tank t = null;
	/**
	 * 炮筒方向
	 */
	Tank.Direction dir = null;
	/**
	 * 初始化炮筒的位置、方向以及所属 Tank
	 * @param x 炮筒的横坐标
	 * @param y	炮筒的纵坐标
	 * @param t 所属 Tank
	 */
	public GunBarrel(int x, int y, Tank t) {
		x0 = x;
		y0 = y;
		this.t = t;
		dir = Tank.Direction.D;
	}
	
	/**
	 * 画炮筒，由 Tank 方向决定
	 * @param g TankClient 传递的画笔
	 * @see java.awt.Graphics
	 */
	public void draw(Graphics g) {
		if(t.dir != Tank.Direction.STOP) {
			dir = t.dir;
		}
		
		direction();
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawLine(x0, y0, x1, y1);
		g.setColor(c);
	}
	/**
	 * Overload draw(), 设置炮筒横纵坐标并画炮筒
	 * @see GunBarrel#draw(Graphics g)
	 * @param x 炮筒的中心横坐标
	 * @param y 炮筒的中心纵坐标
	 * @param g 画笔
	 */
	public void draw(int x, int y, Graphics g) {
		x0 = x;
		y0 = y;
		this.draw(g);
	}
	/**
	 * 根据 dir 确定炮筒的方向并重置横纵坐标值
	 */
	private void direction() {
		switch(dir) {
		case U :
			x1 = x0;
			y1 = y0 - Tank.getHeight() / 2;
			break;
		case UL :
			x1 = x0 - Tank.getWidth() / 2;
			y1 = y0 - Tank.getHeight() / 2;
			break;
		case UR :
			x1 = x0 + Tank.getWidth() / 2;
			y1 = y0 - Tank.getHeight() / 2;
			break;
		case D :
			x1 = x0;
			y1 = y0 + Tank.getHeight() / 2;
			break;
		case DL :
			x1 = x0 - Tank.getWidth() / 2;
			y1 = y0 + Tank.getHeight() / 2;
			break;
		case DR :
			x1 = x0 + Tank.getWidth() / 2;
			y1 = y0 + Tank.getHeight() / 2;
			break;
		case L :
			x1 = x0 - Tank.getWidth() / 2;
			y1 = y0;
			break;
		case R :
			x1 = x0 + Tank.getWidth() / 2;
			y1 = y0;
			break;
		case STOP ://Tank stop, gun barrel default Down
			dir = Tank.Direction.D;
			break;
		}
	}
	
}