import java.awt.*;

public class Missile {
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 10;
	
	int x, y;
	private static final int SPEED = Tank.getSpeed() * 2;

	Tank.Direction dir = null; 
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GREEN);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}


}
