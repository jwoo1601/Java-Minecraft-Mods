package mint.seobaragi.tasks;

import static library.Message.CANCLEAR;
import static library.Reference.HotZonePlayerSet;
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
//		for(Entry<UUID, Integer> entry : WaterLevelTargetMap.entrySet())
//		{
//			UUID uid = entry.getKey();
//			int value = entry.getValue();
//			EntityPlayerMP player = getPlayerByUUID(uid);
//		
//			if(value == 0)
//			{
//
//				PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
//				
//				stat.setWaterLevel(stat.waterLevel - 1);
//				WaterLevelTargetMap.put(uid, 1200);
//			}
//			
//			else{
//				player.addChatMessage(new ChatComponentText("Elapsed Tick: " + value));
//				WaterLevelTargetMap.put(uid, --value);
//			}
//		}
		
		for (PlayerWaterLevelTracker tracker : WaterLevelTrackerSet)
			tracker.update();
	}

	@Override
	public long getDuration()
	{
		return PlayerWaterLevelTracker.DURATION;
	}
}
