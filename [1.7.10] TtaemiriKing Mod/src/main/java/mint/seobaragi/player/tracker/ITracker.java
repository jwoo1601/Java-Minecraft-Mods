package mint.seobaragi.player.tracker;

public interface ITracker{
	
	void startTracking();
	
	void update();
	
	void stopTracking();
	
	void reset();
	
	int getDuration();
	
}
