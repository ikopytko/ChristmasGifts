package ua.minor.christmasgifts.managers;


public class GameManager {
	private static GameManager INSTANCE;
	
	private boolean allowNextShoot;
	private int snowballCount;
	private int score;
	private int score_prev;
	public boolean paused = true;
	
	private GameManager() {}
	
	public synchronized static GameManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	
	public synchronized void initialize() {
		allowNextShoot = true;
		snowballCount = 15;
		score_prev = score = 1;
	}
	
	public synchronized boolean canMakeShoot(){
		return allowNextShoot;
	}
	
	public synchronized void makeShoot(){
		allowNextShoot = false;
		snowballCount--;
	}
	
	public synchronized boolean isShooting() {
		return allowNextShoot;
	}
	
	public synchronized void shooted(boolean p){
		allowNextShoot = true;
		if (snowballCount == 0)
		{}
		
		if (p)
			shootedIncr();
		else
			resetPrev();
	}
	
	public synchronized void addSnowball(int count) {
		snowballCount += count;
	}
	
	public synchronized int getSnowball() {
		return snowballCount;
	}
	
	public synchronized void shootedIncr(){
		score_prev++;
		
		score += (int) (log(score_prev, 8)*log(score+score_prev, 3))+1;
	}
	
	static int log(int x, int base)
	{
	    return (int) (Math.log(x) / Math.log(base));
	}
	
	public synchronized void resetPrev(){
		score_prev = 0;
	}
	
	public synchronized void addScore(int n) {
		score += n;
	}
	
	public synchronized int getScore() {
		return score;
	}
}
