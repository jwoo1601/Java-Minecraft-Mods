package mint.seobaragi.event;

//import static library.Reference.WaterLevelTargetCache;
//import static library.Reference.WaterLevelTargetMap;
import static library.Reference.WaterLevelTrackerSet;
import static library.Reference.getTrackerFromPlayer;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.UUID;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mint.seobaragi.packet.PacketPlayerStat;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.packet.PacketPlayerStat.Type;
import mint.seobaragi.player.tracker.PlayerWaterLevelTracker;
import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEventHandler
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if(event.entity instanceof EntityPlayer)
		{			
			EntityPlayer player = (EntityPlayer) event.entity;
			player.registerExtendedProperties(ID, new PropertyPlayerStat());
		}
	}
	
	@SubscribeEvent
	public void onClone(PlayerEvent.Clone event)
	{
		PropertyPlayerStat target = (PropertyPlayerStat) event.original.getExtendedProperties(ID);
		
		((PropertyPlayerStat) event.entity.getExtendedProperties(ID)).set(target);		
	}
	
	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			UUID uid = player.getUniqueID();
			
			if (event.world.isRemote)
			{
				CommonProxy.INSTANCE.sendToServer(new PacketPlayerStat(Type.REQUEST, uid, null));
			}
			
			else {
				WaterLevelTrackerSet.add(new PlayerWaterLevelTracker(player));
				getTrackerFromPlayer(player).startTracking();
			}		
		}
	}
}
