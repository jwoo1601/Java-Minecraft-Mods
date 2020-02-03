package mint.seobaragi.tasks;

import static library.Reference.JjimJilZoneTrackerSet;

import mint.seobaragi.player.tracker.PlayerJjimjilZoneTracker;
import library.ITaskable;

public class TaskJjimJilZone implements ITaskable
{
	
	@Override
	public void uptate()
	{
		for(PlayerJjimjilZoneTracker tracker : JjimJilZoneTrackerSet)
			tracker.update();
	}
	
	//틱단위 20틱 = 1초
	@Override
	public long getDuration()
	{
		return 1;
	}
}
