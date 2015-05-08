package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class Shield extends Sprite{
	SpaceShip v;
	public Shield(SpaceShip v) {
		super(v.x, v.y, 70, 70);
		this.v = v;
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.drawRoundRect(v.x, v.y, 70 , 70, 30, 30);
	}
	public void work(GameEngine g){
		v.lifePoint.isChange(false);
	}
}
