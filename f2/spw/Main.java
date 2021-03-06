package f2.spw;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Main{
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		SpaceShip v = new SpaceShip(160, 530, 70, 70);
//		LifePoint lifePoint = new LifePoint(300);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);
//		GameEngine engine = new GameEngine(gp, v , lifePoint);

		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		

		
		engine.start();
	}
}
