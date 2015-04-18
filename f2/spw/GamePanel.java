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
	private Image imageBg;
	private Image imageHeart;
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private int curTime = -1;
	public GamePanel() {
	
		bi = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		try {
			File sourceImageBg = new File("f2/spw/pics/bg.png");
			imageBg = ImageIO.read(sourceImageBg);
			File sourceImageHeart = new File("f2/spw/pics/heart_item.png");
			imageHeart = ImageIO.read(sourceImageHeart);
		}catch (IOException e) {
        	e.printStackTrace();
        }
		big = (Graphics2D) bi.getGraphics();
//		big.drawImage(image, 0, 0, null);
//		big.setBackground(Color.BLUE);
	}
	
	public boolean checkStage(GameReporter reporter, int delayTime){
		if(reporter.getScore() % reporter.SCORE_STAGE_CHANGE == 0 ){
			if( curTime == -1 ){
				curTime = reporter.getTime();
				return false;
			}
		}
		else if(reporter.getTime() <= curTime + delayTime)
			return true;
		else curTime = -1;
		return false;
		
	}
	public void updateGameUI(GameReporter reporter) {
		big.clearRect(0, 0, 400, 600);
		big.drawImage(imageBg, 0, 0, null);
		big.setColor(Color.LIGHT_GRAY);
		if(checkStage(reporter, 3)){
			big.drawString(String.format("Stage %d", reporter.getStage()), 400 / 2 - 25, 600 / 2);
		}
		big.fillRect(295, 5, 65, 20);
		big.setColor(Color.RED);
		
		if(reporter.getLifePoint() <=  0)
			big.fillRect(10, 27, 0, 20);
		else if(reporter.getLifePoint() % 100 == 0){
			big.fillRect(10, 27, 100, 20);
		}else big.fillRect(10, 27, reporter.getLifePoint() % 100, 20);
		
		big.setColor(Color.BLACK);
		big.drawString(String.format("%d", reporter.getLifePoint()), 10, 40);
		int positionHeart = 0;
		for(int i = 0 ;i < reporter.getHeart() ;i++){
			big.drawImage(imageHeart, 10+positionHeart, 7, 20, 20, null);
			positionHeart+=22;
		}
		
		
		
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
