package f2.spw;

public class LifePoint {
	private int lifePoint;
	public LifePoint(int lifePoint) {
		this.lifePoint = lifePoint;
	}
	public void decreaseLifePoint(){
		this.lifePoint -= 30;
	}
	public void increaseLifePoint(){
		this.lifePoint += 100;
	}
	public int getLifePoint(){
		return lifePoint; 
	}
	
}
