package mint.seobaragi.tasks;

import static library.Reference.ColdZoneTrackerSet;

import mint.seobaragi.player.tracker.PlayerColdZoneTracker;
import library.ITaskable;

public class TaskColdZone implements ITaskable
{
	
	@Override
	public void uptate()
	{
		for(PlayerColdZoneTracker tracker : ColdZoneTrackerSet)
			tracker.update();
	}
	
	@Override
	public long getDuration()
	{
		return 1;
	}
}
