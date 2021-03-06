package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Sprite{
	int step = 20;
	private boolean alive = true;
	public Bullet(int x, int y) {
		super(x, y, 5, 10);
	}
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
	}
	public void proceed(){
		y -= step;
	}
	public void disappear(){
		this.alive = false;
	}
	public boolean isAlive(){
		return alive;
	}
}
