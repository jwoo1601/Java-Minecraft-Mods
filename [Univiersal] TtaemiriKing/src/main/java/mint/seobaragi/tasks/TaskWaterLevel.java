package mint.seobaragi.tasks;

import static library.Message.CANCLEAR;
import static library.Reference.DEBUG;
import static library.Reference.WaterLevelTrackerSet;
import static library.Reference.getPlayerByUUID;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.Map.Entry;
import java.util.UUID;

import mint.seobaragi.player.tracker.PlayerWaterLevelTracker;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import library.ITaskable;

public class TaskWaterLevel implements ITaskable
{
	@Override
	public void uptate()
	{		
		if (DEBUG && !WaterLevelTrackerSet.isEmpty())
			System.out.println("the number of Tracker = " + WaterLevelTrackerSet.size());
		
		for (PlayerWaterLevelTracker tracker : WaterLevelTrackerSet)
			tracker.update();
	}

	@Override
	public long getDuration()
	{
		return 1;
	}
}
