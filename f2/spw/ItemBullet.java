package f2.spw;

public class ItemBullet extends Item{
	public ItemBullet(int x, int y, int width, int height,String name){
		super(x, y, width, height,name);
		this.setImage("f2/spw/pics/bullet_item.png");
	}
	public void getItem(GameEngine g) {
		g.setBulletUpgrade(true);
		g.startDelayTimeItem();
	}
}
