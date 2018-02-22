import java.awt.*;
import javax.swing.JFrame;

public class TankClient extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public final int GAME_WIDTH = 800;
	public final int GAME_HEIGHT = 600;
	
	int x = 50, y = 50;
	
	Image img = null;
	
	public static void main(String args[]) {
		new TankClient();
	}
	
	TankClient() {
//		setSize(800, 600);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setTitle("TankWar");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.BLACK);
		setVisible(true);
		new Thread(new PaintThread()).start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		
		y += 8;
	}
	/*
	@Override
	public void update(Graphics g) {
		if(img == null) {
			img = this.createImage(WIDTH, HEIGHT);//JFrame
		}
		Graphics offScreenGraphics = img.getGraphics();
		Color c = offScreenGraphics.getColor();
		offScreenGraphics.setColor(Color.BLACK);
		offScreenGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		paint(offScreenGraphics);
		offScreenGraphics.setColor(c);
		g.drawImage(img, 0, 0, null);
	}
	*/
	private class PaintThread implements Runnable {

		@Override
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
