package mint.seobaragi.tasks;

import static library.Reference.HotZoneTrackerSet;

import mint.seobaragi.player.tracker.PlayerHotZoneTracker;
import library.ITaskable;

public class TaskHotZone implements ITaskable
{
	@Override
	public void uptate()
	{
		for(PlayerHotZoneTracker tracker : HotZoneTrackerSet)
			tracker.update();
	}
	
	@Override
	public long getDuration()
	{
		return 1;
	}
}
