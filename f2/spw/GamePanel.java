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
	public void drawHeart(int num,int initX, int initY){
		int positionHeart = initX;
		for(int i = 0 ;i < num ;i++){
			big.drawImage(imageHeart, 10+positionHeart, initY, 20, 20, null);
			positionHeart+=22;
		}
	}
	public void drawHp(int initX, int initY,int lifePoint,Color bgColor,Color textColor){

		big.setColor(bgColor);
		if(lifePoint <=  0)
			big.fillRect(initX, initY, 0, 20);
		else if(lifePoint % 100 == 0){
			big.fillRect(initX, initY, 100, 20);
		}else big.fillRect(initX, initY, lifePoint % 100, 20);
		big.setColor(textColor);
		big.drawString(String.format("%d", lifePoint), initX, initY + 13);
	}
	public void drawUILifeBoss(GameReporter reporter){
		drawHp(10, 27,reporter.getLifePointBoss(),Color.GRAY, Color.BLACK);
		drawHeart(reporter.getHeartBoss(), 0 , 7);
	}
	public void drawUILifeSpace(GameReporter reporter){
		drawHp(10,bi.getHeight()-30,reporter.getLifePoint(),Color.RED, Color.BLACK);
		drawHeart(reporter.getHeart(), 0 , bi.getHeight() - 50);
	}
	public void updateGameUI(GameReporter reporter) {
		big.clearRect(0, 0, 400, 600);
		big.drawImage(imageBg, 0, 0, null);
		big.setColor(Color.LIGHT_GRAY);
		if(checkStage(reporter, 3)){
			big.drawString(String.format("Stage %d", reporter.getStage()), 400 / 2 - 25, 600 / 2);
		}
		big.fillRect(295, 5, 65, 20);
		
		drawUILifeSpace(reporter);
		if(reporter.bossBorn()){
			drawUILifeBoss(reporter);
		}
		big.setColor(Color.BLACK);
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
