package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class BulletBoss extends Bullet{
	private boolean alive = true;
	public BulletBoss(int x, int y) {
		super(x, y);
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
	}
	public void proceed(){
		y += step;
	}
	public void disappear(){
		this.alive = false;
	}
	public boolean isAlive(){
		return alive;
	}
}
