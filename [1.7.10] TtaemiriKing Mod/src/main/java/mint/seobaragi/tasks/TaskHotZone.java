package mint.seobaragi.tasks;

import static library.Message.CANCLEAR;
import static library.Reference.HotZonePlayerSet;
import static library.Reference.getPlayerByUUID;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.UUID;

import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import library.ITaskable;

public class TaskHotZone implements ITaskable
{
	@Override
	public void uptate()
	{
		for(UUID uid : HotZonePlayerSet)
		{
			EntityPlayerMP player = getPlayerByUUID(uid);
			PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
			
			stat.setSellPoint(1);
			player.addChatMessage(new ChatComponentText(CANCLEAR));
		}
	}
	
	@Override
	public long getDuration()
	{
		return 3600;
	}
}
