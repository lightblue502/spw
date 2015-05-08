package f2.spw;

public class LifePoint {
	private int lifePoint;
	private int initLifePoint;
	private boolean isChange = true;
	private int heart;
	public LifePoint(int lifePoint) {
		this.lifePoint = lifePoint;
		initLifePoint = lifePoint;
	}
	public void damage(int num){
		if(isChange)
			this.lifePoint -= num;
	}
	public void addLife(int num){
		if(isChange)
			this.lifePoint += num;
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
		this.heart = (int) Math.ceil(heart); //ª—¥‡»…¢÷Èπ
		return this.heart;
	}
	public void decreaseHeart(){
		this.heart--;
	}
	public void isChange(boolean status){
		this.isChange = status;
	}
	public boolean getIsChage(){
		return isChange;
	}
	
	
}
