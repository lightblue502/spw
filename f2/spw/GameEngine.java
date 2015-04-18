package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	private final int SECOND = 1000; 
	private final double RANDOM_ITEM =  0.01;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	private SpaceShip v;
	public LifePoint lifePoint;
	private Timer timer;
	private int count = 0;
	private long score = 0;
	private double difficulty = 0.05;
	private int stage = 0;
	private int countTimer = 0;
	private boolean statusBulletUpgrade = false;
	public GameEngine(GamePanel gp, SpaceShip v,LifePoint lifePoint) {
		this.gp = gp;
		this.v = v;		
		this.lifePoint= lifePoint;
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				counter();
				itemProcess();
				bulletProcess();
				enemyProcess();
				process();
				if(checkScore(SCORE_STAGE_CHANGE))
					stageChanged();
			}
		});
		timer.setRepeats(true);
	}
							/// LifePoint ///
	public int getHeart(){
		return lifePoint.getHeart();
	}
	public int getLifePoint(){
		return lifePoint.getLifePoint();
	}
	public void checkLife(){
		if(lifePoint.getHeart() <= 0){
			gp.updateGameUI(this);
			die();
		}
	}
//	public void checkLifePoint(){
//		if(lifePoint.getLifePoint() <= 0 ){
//			
//		}
//	}
							/// TIME ///
	public Timer getTimer(){
		return timer;
	}
	public void start(){
		timer.start();
	}
	public void die(){
		timer.stop();
	}
	public int getTime() {
		return count;
	}
	private void counter(){
		countTimer += timer.getDelay();
		if(countTimer %  SECOND == 0){
			count++;
		}
	}
							/// SCORE ///
	public boolean checkScore(int score){
		if(this.score / score == stage){
			if(this.score % score == 0){
				return true;
			}
		}
		return false;
	}
	public long getScore(){
		return score;
	}
							///	STAGE ///
	public int getStage() {
		return stage;
	}
	
	private void stageChanged(){
		this.difficulty += 0.01;
		stage++;
	}
							///	Enemy ///
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void enemyProcess(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
				
			}
		}
	}
							///	BULLET ///
	public void setBulletUpgrade(boolean statusBulletUpgrade){
		this.statusBulletUpgrade  = statusBulletUpgrade;
	}
	public boolean isBulletUpgrade(){
		return statusBulletUpgrade;
	}
	private void generateBullet(){
		Bullet b = new Bullet(v.x + (v.width / 2) - 2, v.y);
		gp.sprites.add(b);
		bullets.add(b);
		if(isBulletUpgrade()){
			Bullet b1 = new Bullet(v.x , v.y);
			Bullet b2 = new Bullet(v.x + (v.width), v.y);
			gp.sprites.add(b1);
			bullets.add(b1);
			gp.sprites.add(b2);
			bullets.add(b2);
		}
	}
	private void bulletProcess(){
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
	}
								///	ITEM ///
	private void generateItem(){
		int random = (int)(Math.random()*3);
		if(random == 0){
			ItemHeart itemHeart = new ItemHeart((int)(Math.random()*390), 30, 25, 25,"heart");
			gp.sprites.add(itemHeart);
			items.add(itemHeart);
		}else if(random == 1){
			ItemShield itemShield = new ItemShield((int)(Math.random()*390), 30, 25, 25,"shield");
			gp.sprites.add(itemShield);
			items.add(itemShield);
		}
		else {
			ItemBullet itemBullet = new ItemBullet((int)(Math.random()*390), 30, 25, 25,"newBullet");
			gp.sprites.add(itemBullet);
			items.add(itemBullet);
		}
	}
	private void itemProcess(){
		if(Math.random() < RANDOM_ITEM){
			generateItem();
		}
		Iterator<Item> item_iter = items.iterator();
		while(item_iter.hasNext()){
			Item item = item_iter.next();
			item.proceed();
			
			if(!item.isAlive()){
				item_iter.remove();
				gp.sprites.remove(item);
				System.out.println(item.getName());
			}
		}
	}
	private Timer delayTimeItem = new Timer(1000*3, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setBulletUpgrade(false);
			delayTimeItem.stop();
		}
	});
	public void startDelayTimeItem(){
		delayTimeItem.start();
	}
					/// process intersects object ///
	private void process(){
		Ellipse2D.Double vr =  v.getEllipse();
		Ellipse2D.Double eEllip;
		Ellipse2D.Double itemEllip;
		for(Item item : items){
			itemEllip = item.getEllipse();
			if(itemEllip.intersects(vr.x, vr.y, vr.width, vr.height)){
//				upgrade(item);
				item.getItem(this);
				item.disappear();
				return;
			}
		}
		for(Enemy e : enemies){
			eEllip = e.getEllipse();
			for(Bullet b :bullets){
				Ellipse2D.Double bEllip = b.getEllipse();
				if(eEllip.intersects(bEllip.x, bEllip.y, bEllip.width, bEllip.height)){
					b.disappear();
					e.disappear();
					return;
				}
			}
			if(eEllip.intersects(vr.x+20, vr.y+20, vr.width /2, vr.height /2)){
				lifePoint.decreaseLifePoint();
				e.disappear();
				checkLife();
				return;
			}
		}
		gp.updateGameUI(this);
	}
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_SPACE:
			generateBullet();
			break;
		case KeyEvent.VK_F1:
			difficulty = 0;
			break;
		case KeyEvent.VK_D:
			difficulty += 0.05;
			break;
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}

}
