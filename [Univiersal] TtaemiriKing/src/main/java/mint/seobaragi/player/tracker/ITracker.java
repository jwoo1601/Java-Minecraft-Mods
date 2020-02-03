package mint.seobaragi.player.tracker;

public interface ITracker<T extends ITracker>{
	
	T startTracking();
	
	T update();
	
	T stopTracking();
	
	T reset();
	
}
