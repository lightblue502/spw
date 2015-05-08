package f2.spw;

import java.awt.Graphics2D;
public class EnemyBoss extends Sprite{
	public static final int Y_TO_FADE = 400;
	public static final int Y_TO_DIE = 600;
	private boolean statusBullet = true;		
	private int step = 8;
	private boolean alive = true;
	LifePoint lifePoint;
	public EnemyBoss(int x, int y) {
		super(x, y, 100, 100);
		this.setImage("f2/spw/pics/boss.png");
		lifePoint = new LifePoint(300);
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
		
	}
	int numsBullet = 0;
	int tempTime = 0;
	private void aiBullet(int time){
//		System.out.println(numsBullet);
		if(numsBullet > Math.random()*10){
			numsBullet = 0;
			tempTime = time;
			tempTime += Math.random()*1.5;
			statusBullet = false;
		}
		if(time > tempTime)
			statusBullet = true;
	}
	public void enermyShoot(GameEngine g){
		aiBullet(g.getTime());
		if(statusBullet){
			numsBullet++;
			g.generateEnermyBullet(x + width / 2, y + height / 2 );
		}
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
	boolean toggleStep = true;
	private void aiStep(){
		if(x > Y_TO_FADE - 50 )
			toggleStep = !toggleStep;
		else if( x < 0)
			toggleStep = !toggleStep;
	}
	public void proceed(){
		aiStep();
		System.out.println(x);
		if(toggleStep)
			x += step;
		else
			x -= step;
	}
	public void disappear(){
		this.alive = false;
		this.statusBullet = false;
	}
	public void reset(){
		this.alive = true;
		this.statusBullet = true;
	}
	public boolean isAlive(){
		return alive;
	}
}
