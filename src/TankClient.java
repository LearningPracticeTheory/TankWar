import java.awt.*;
import javax.swing.JFrame;

public class TankClient extends JFrame {
	
	private static final long serialVersionUID = 1L;
	public final int GAME_WIDTH = 800;
	public final int GAME_HEIGHT = 600;
	
	public static void main(String args[]) {
		new TankClient();
	}
	
	TankClient() {
//		setSize(800, 600);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setTitle("TankWar");
		getContentPane().setBackground(Color.BLACK);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
}
