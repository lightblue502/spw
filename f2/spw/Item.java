package f2.spw;

import java.awt.Graphics2D;

public class Item extends Sprite{
	private int step = 12;
	private boolean alive = true;
	private String name;
	public Item(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	public Item(int x, int y, int width, int height,String name){
		super(x, y, width, height);
		this.name = name;
	}
	public String getName(){
		return name;
	}
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(this.image, x, y, width, height, null);
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
