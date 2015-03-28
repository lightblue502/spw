package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;
	private Image image;
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
	
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		try {
			File sourceimage = new File("f2/spw/pics/bg.png");
			image = ImageIO.read(sourceimage);
		}catch (IOException e) {
        	e.printStackTrace();
        }
		big = (Graphics2D) bi.getGraphics();
//		big.drawImage(image, 0, 0, null);
//		big.setBackground(Color.BLUE);
	}

	public void updateGameUI(GameReporter reporter){
		
		big.clearRect(0, 0, 400, 600);
		big.drawImage(image, 0, 0, null);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);
		
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
		
	}

}
