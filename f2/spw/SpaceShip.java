package f2.spw;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SpaceShip extends Sprite {
	int step = 8;
	private final boolean STATUS_ITEM = false;
	private boolean statusBullet = STATUS_ITEM;
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.setImage("f2/spw/pics/spaceShip.png");
	}

	@Override
	public void draw(Graphics2D g) {
//		g.setColor(Color.GREEN);
		g.drawImage(this.image, x, y, width, height, null);
	}

	public void move(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 400 - width)
			x = 400 - width;
	}
	public void upgrade(Item item){
		if(item instanceof ItemBullet){
			setStatusBullet(true);
			startDelayTimeItem();
		}
		else if(item instanceof ItemShield)
			System.out.println("ItemShield");
		else if(item instanceof ItemHeart)
			System.out.println("itemHeart");
	}
	private Timer delayTimeItem = new Timer(1000*3, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setStatusBullet(false);
			delayTimeItem.stop();
		}
	});
	public void startDelayTimeItem(){
		delayTimeItem.start();
	}
	public void setStatusBullet(boolean statusBullet){
		this.statusBullet = statusBullet;
	}
	public boolean getStatusBullet(){
		return statusBullet;
	}
}
