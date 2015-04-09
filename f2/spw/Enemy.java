package f2.spw;

import java.awt.Graphics2D;


public class Enemy extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	public Enemy(int x, int y) {
		super(x, y, 40, 40);
		this.setImage("f2/spw/pics/enemy.png");
	}

	@Override
	public void draw(Graphics2D g) {
		
//		if(y < Y_TO_FADE){
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
//		}
//		else{
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
//					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
//		}
		g.drawImage(image, x, y, width, height, null);
		
	}

	public void proceed(){
		y += step;
		if(y > Y_TO_DIE){
			alive = false;
		}
	}
	public void disappear(){
		this.alive = false;
	}
	public boolean isAlive(){
		return alive;
	}
}