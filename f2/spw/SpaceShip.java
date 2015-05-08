package f2.spw;

import java.awt.Graphics2D;

public class SpaceShip extends Sprite {
	int step = 8;
	LifePoint lifePoint;
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setImage("f2/spw/pics/spaceShip.png");
		lifePoint = new LifePoint(300);
	}
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.image, x, y, width, height, null);
	}
	public void hit(int damage){
		lifePoint.damage(damage);
	}
	public int getLife(){
		return lifePoint.getLifePoint();
	}
	public int getHeart(){
		return lifePoint.getHeart();
	}
	public void addLife(){
		lifePoint.addLife(100);
	}
	public void removeShield(){
		lifePoint.isChange(true);
	}
	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
}
