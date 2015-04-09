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
	private Timer timer;
	private int count = 0;
	private long score = 0;
	private double difficulty = 0.05;

	private int stage = 0;
	private int countTimer = 0;

	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				counter();
				itemProcess();
				bulletProcess();
				enemyProcess();
				process();
				if(checkScore(SCORE_STAGE_CHANGE)){
					stageChanged();
				}
			}
		});
		timer.setRepeats(true);
		
		
	}
	private void counter(){
		countTimer += timer.getDelay();
		if(countTimer %  SECOND == 0){
			count++;
		}
	}
	public long getScore(){
		return score;
	}
	public int getStage() {
		return stage;
	}
	public Timer getTimer(){
		return timer;
	}
	public void start(){
		timer.start();
	}
	public void die(){
		timer.stop();
	}
	private void stageChanged(){
		this.difficulty += 0.01;
		stage++;
	}
	public boolean checkScore(int score){
		if(this.score / score == stage){
			if(this.score % score == 0){
				System.out.println("test");
				return true;
			}
		}
		return false;
	}
	@Override
	public int getTime() {
		return count;
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateBullet(){
		Bullet b = new Bullet(v.x + (v.width / 2) - 2, v.y);
		gp.sprites.add(b);
		bullets.add(b);
	}
	private void generateItem(){
		int random = (int)(Math.random()*3);
		System.out.println(random);
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
	private void process(){
//		Rectangle2D.Double vr = v.getRectangle();
//		Rectangle2D.Double er;
		Ellipse2D.Double vr =  v.getEllipse();
		Ellipse2D.Double eEllip;
		Ellipse2D.Double itemEllip;
		for(Item item : items){
			itemEllip = item.getEllipse();
			if(itemEllip.intersects(vr.x, vr.y, vr.width, vr.height)){
				item.disappear();
				return;
			}
		}
		for(Enemy e : enemies){
//			er = e.getRectangle();
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
				die();
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
