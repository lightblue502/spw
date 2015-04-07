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
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();	
	private SpaceShip v;	
	private Timer timer;
	private int count = 0;
	private long score = 0;
	private double difficulty = 0.05;
	private int stage = 0;

	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				process();
			}
		});
		timer.setRepeats(true);
		
		
	}
	private int countTimer = 0;
	private void counter(){
		countTimer += timer.getDelay();
		if(countTimer %  SECOND == 0){
			count++;
		}
			
	}
	public Timer getTimer(){
		return timer;
	}
	public void start(){
		timer.start();
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
	
	private void process(){
		counter();
		if(Math.random() < difficulty){
			generateEnemy();
		}
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
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
		if(checkScore(SCORE_STAGE_CHANGE)){
			stageChanged();
		}
		gp.updateGameUI(this);
		
//		Rectangle2D.Double vr = v.getRectangle();
//		Rectangle2D.Double er;
		Ellipse2D.Double vr =  v.getEllipse();
		Ellipse2D.Double er;
		for(Enemy e : enemies){
		
//			er = e.getRectangle();
			er = e.getEllipse();
			for(Bullet b :bullets){
				Ellipse2D.Double br = b.getEllipse();
				if(er.intersects(br.x, br.y, br.width, br.height)){
					b.die();
					e.die();
					return;
				}
			}
			if(er.intersects(vr.x+20, vr.y+20, vr.width /2, vr.height /2)){
				die();
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
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

	public long getScore(){
		return score;
	}
	public int getStage() {
		return stage;
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
