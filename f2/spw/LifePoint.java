package f2.spw;

public class LifePoint {
	private int lifePoint = 300;
	private int initLifePoint;
	private boolean isChange = true;
	private int heart = 3;
	public LifePoint(int lifePoint) {
		this.lifePoint = lifePoint;
		initLifePoint = lifePoint;
	}
	public void decreaseLifePoint(){
		if(isChange)
			this.lifePoint -= 30;
	}
	public void increaseLifePoint(){
		if(isChange)
			this.lifePoint += 100;
	}
	public void setLifePoint(int lifePoint){
		this.lifePoint = lifePoint;
	}
	public int getLifePoint(){
		if(lifePoint <= 0)
			lifePoint = 0;
		else if(lifePoint >= initLifePoint)
			lifePoint = initLifePoint;
		return lifePoint; 
	}
	public int getHeart(){
		double heart = (double)lifePoint/100;
		this.heart = (int) Math.ceil(heart); //�Ѵ��ɢ��
		return this.heart;
	}
	public void decreaseHeart(){
		this.heart --;
	}
	public void isChange(boolean status){
		this.isChange = status;
	}
	public boolean getIsChage(){
		return isChange;
	}
	
	
}
