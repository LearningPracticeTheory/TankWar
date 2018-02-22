import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {
	
	private static final long serialVersionUID = 1L;
	public final int GAME_WIDTH = 800;
	public final int GAME_HEIGHT = 600;
	
	Image img = null;
	
	Blood b = new Blood(this);
	Tank myTank = new Tank(50, 50, true, this);

	Wall w1 = new Wall(300, 100, 300, 30, this);
	Wall w2 = new Wall(200, 250, 30, 200, this);

	List<Wall> walls = new ArrayList<Wall>();
	List<Tank> tanks = new ArrayList<Tank>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	
	public static void main(String args[]) {
		new TankClient();
	}
	
	TankClient() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setTitle("TankWar");
		setResizable(false);
		setLocationRelativeTo(null);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		getContentPane().setBackground(Color.BLACK);
		setBackground(Color.BLACK);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		int initTankNum = Integer.parseInt(PropertiesManage.getProperty("initTankNum"));
		for(int i = 0; i < initTankNum; i++) {
			tanks.add(new Tank(50 + (i + 1) * 40, 50, false, this));
		}

		walls.add(w1);
		walls.add(w2);
		
		new Thread(new PaintThread()).start();
		addKeyListener(new KeyMonitor());
	}

	public void paint(Graphics g) {
//		super.paint(g);

		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("Missiles count:" + missiles.size(), 10, 50);
		g.drawString("Tanks count:" + tanks.size(), 10, 70);
		g.drawString("myTank's life:" + myTank.getLife(), 10, 90);
		g.setColor(c);

		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
		b.draw(g);

		myTank.collideWithTanks(tanks);
		myTank.eatBlood(b);

		for(int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.collideWithWall(w1);
			t.collideWithWall(w2);
			t.collideWithTanks(tanks);
		}
		
		for(int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
		}
		
		for(int i = 0; i < explodes.size(); i++) {
			explodes.get(i).draw(g);
		}
		
		
	}

	@Override
	public void update(Graphics g) {
		if(img == null) {
//			img = this.createImage(WIDTH, HEIGHT);
			img = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics offScreenGraphics = img.getGraphics();
		Color c = offScreenGraphics.getColor();
		offScreenGraphics.setColor(Color.BLACK);
//		offScreenGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		offScreenGraphics.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		offScreenGraphics.setColor(c);
		paint(offScreenGraphics);
		g.drawImage(img, 0, 0, null);
	}

	private class PaintThread implements Runnable {
		private boolean flag = true;
		public void run() {
			while(flag) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
					flag = false;
				}
			}
		}
	}

//key pressed && released on Tank will make more stable 
	private class KeyMonitor extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		
	}
	
}
