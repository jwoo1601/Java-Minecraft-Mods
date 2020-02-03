package mint.seobaragi.tasks;

import static library.Message.CANCLEAR;
import static library.Message.GETDIRT;
import static library.Reference.JjimJilZonePlayerSet;
import static library.Reference.getPlayerByUUID;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.UUID;

import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import library.ITaskable;

public class TaskJimJilZone implements ITaskable
{
	
	@Override
	public void uptate()
	{
		for(UUID uid : JjimJilZonePlayerSet)
		{
			EntityPlayerMP player = getPlayerByUUID(uid);
			PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
			
			stat.setDirtLevel(stat.dirtLevel + 2);
			stat.setWaterLevel(stat.waterLevel - 30);
			player.addChatMessage(new ChatComponentText(GETDIRT));
		}
	}
	
	//틱단위 20틱 = 1초
	@Override
	public long getDuration()
	{
		return 3600;
	}
}
