package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	private final int SECOND = 1000; 
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<BulletBoss> bulletsBoss = new ArrayList<BulletBoss>();
	private EnemyBoss eb;
	private SpaceShip v;
	private Shield shield;
	private Timer timer;
	private int count = 0;
	private long score = 0;
	private double difficulty = 0.05;
	private int stage = 1;
	private int countTimer = 0;
	private boolean statusBulletUpgrade = false;
	private boolean bossBorn = false;
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		gp.sprites.add(v);
		eb = new EnemyBoss(0, 30);
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				counter();
				itemProcess();
				bulletProcess();
				enemyProcess();
				enemyBossProcess();
				bulletBossProcess();
				process();
				if(checkScore(SCORE_STAGE_CHANGE)){
					stageChanged();
				}
			}
		});
		timer.setRepeats(true);
	}
							/// Shield ///
	public void generateShield(){
		System.out.println("genshield");
		shield = new Shield(v);
		gp.sprites.add(shield);
		shield.work(this);
	}

	public void removeShield(){
		System.out.println("remove Shield");
		gp.sprites.remove(shield);
		v.removeShield();
	}
							/// LifePoint ///
	public int getHeart(){
		return v.getHeart();
	}
	public int getLifePoint(){
		return v.getLife();
	}
	public void spaceShipAddLife(){
		v.addLife();
	}
	public int getLifePointBoss() {
		return eb.getLife();
	}
	public int getHeartBoss() {
		return eb.getHeart();
	}

	public void checkLife(){
		if(v.getHeart() <= 0){
			gp.updateGameUI(this);
			die();
		}
	}
	public void checkLifeBoss(){
		if(eb.getHeart() <= 0){
			System.out.println(" boss disappear");
			eb.disappear();
			BOSSdie();
		}
	}
							/// TIME ///
	public Timer getTimer(){
		return timer;
	}
	public void start(){
		timer.start();
	}
	public void die(){
		new GameEnd("GAME OVER", score);
		timer.stop();
	}
	public void BOSSdie(){
		new GameEnd("END GAME", score);
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
		if(this.score == 0){
			return false; 
		}else if(this.score / score == stage){
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
	public boolean bossBorn(){
		return bossBorn;
	}
	public int getStage() {
		return stage;
	}
	private void stageChanged(){
		this.difficulty += 0.05;
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
	}						/// EnermyBoss ///
	public void checkBoss(){
		if(stage > 5){
			stage = 5;
			bossBorn = true;
		}
	}
	private void enemyBossProcess(){
		checkBoss();
		if(bossBorn){
			gp.sprites.add(eb);
			eb.proceed();
			eb.enermyShoot(this);
			if(!eb.isAlive()){
				bossBorn = false;
				eb.reset();
				score += 1000;
			}
		}else {
			gp.sprites.remove(eb);
		}
		
	}
							///	BULLET ///
	public void setBulletUpgrade(boolean statusBulletUpgrade){
		this.statusBulletUpgrade  = statusBulletUpgrade;
	}
	public boolean isBulletUpgrade(){
		return statusBulletUpgrade;
	}
	public void generateEnermyBullet(int x , int y){
		BulletBoss bb = new BulletBoss(x , y);
		gp.sprites.add(bb);
		bulletsBoss.add(bb);
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
	private void bulletBossProcess(){
		Iterator<BulletBoss> bb_iter = bulletsBoss.iterator();
		while(bb_iter.hasNext()){
			BulletBoss bb = bb_iter.next();
			bb.proceed();
			
			if(!bb.isAlive()){
				bb_iter.remove();
				gp.sprites.remove(bb);
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
	private final double RANDOM_ITEM = 0.01;
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
	private Timer delayItem;
	public void setItemDelay(int time){
		delayItem = new Timer(1000*time, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setBulletUpgrade(false);
				removeShield();
				delayItem.stop();
			}
		});
		
	}
	public void startDelayItem(int time){
		setItemDelay(time);
		delayItem.start();
	}
					/// process intersects object ///
	private void process(){
		Ellipse2D.Double vr =  v.getEllipse();
		Ellipse2D.Double bossR =  eb.getEllipse();
		Ellipse2D.Double eEllip;
		Ellipse2D.Double itemEllip;
		for(Item item : items){
			itemEllip = item.getEllipse();
			if(itemEllip.intersects(vr.x, vr.y, vr.width, vr.height)){
				// itemUpgrade
				item.getItem(this);
				item.disappear();
				return;
			}
		}
		for(BulletBoss bb :bulletsBoss){
			Ellipse2D.Double bbEllip = bb.getEllipse();
			// bulletBoss hit spaceShip 
			if(bbEllip.intersects(vr.x+20, vr.y+20, vr.width /2, vr.height /2)){
				v.hit(10);
				checkLife();
			}
		}
		for(Bullet b :bullets){
			Ellipse2D.Double bEllip = b.getEllipse();
			// bullet hit boss 
			if(bEllip.intersects(bossR.x, bossR.y, bossR.width, bossR.height)){
				eb.hit(10);
				b.disappear();
				checkLifeBoss();
			}
		}
		for(Enemy e : enemies){
			eEllip = e.getEllipse();
			for(Bullet b :bullets){
				Ellipse2D.Double bEllip = b.getEllipse();
				// bullet hit enermy
				if(eEllip.intersects(bEllip.x, bEllip.y, bEllip.width, bEllip.height)){
						b.disappear();	
						e.disappear();
					}
			}
			//enermy hit spaceShip
			if(eEllip.intersects(vr.x+20, vr.y+20, vr.width /2, vr.height /2)){
				v.hit(30);
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
		case KeyEvent.VK_P:
			timer.stop();
			break;
		case KeyEvent.VK_O:
			timer.start();
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
