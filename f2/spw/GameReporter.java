package f2.spw;

public interface GameReporter {
	public final static int SCORE_STAGE_CHANGE = 1000;
	long getScore();
	int getStage();
	int getTime();
	int getLifePoint();
	int getHeart();
	int getLifePointBoss();
	int getHeartBoss();
	boolean bossBorn();
}
