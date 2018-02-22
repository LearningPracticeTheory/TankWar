import java.awt.*;

class Blood {
	
	private static final int WIDTH = 15;
	private static final int HEIGHT = 15;
	
	int x, y;	
	int step = 0;
	int pos[][] = {{300, 300}, {350, 300}, {350, 350}, {300, 350}};
	
	TankClient tc;
	
	private boolean live = true;
	
	public Blood(TankClient tc) {
		this.tc = tc;
	}
	
	private void position() {
		if(step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
	}

	public void draw(Graphics g) {
		if(!live) {
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		position();
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		step ++;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	
	
}