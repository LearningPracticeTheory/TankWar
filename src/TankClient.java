import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class TankClient extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public final int GAME_WIDTH = 800;
	public final int GAME_HEIGHT = 600;
	
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		setVisible(true);
		
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
		super.paint(g);

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
		
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("Missiles count:" + missiles.size(), 10, 50);
		g.drawString("Tanks count:" + tanks.size(), 10, 70);
		g.drawString("myTank's life:" + myTank.getLife(), 10, 90);
		g.setColor(c);

	}

	private class PaintThread implements Runnable {
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
