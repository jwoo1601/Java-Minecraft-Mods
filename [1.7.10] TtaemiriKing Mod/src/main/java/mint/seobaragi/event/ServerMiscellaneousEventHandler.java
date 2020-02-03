package mint.seobaragi.event;

import static library.Reference.COLD_ZONE;
import static library.Reference.ColdZonePlayerSet;
import static library.Reference.HOT_ZONE;
import static library.Reference.HotZonePlayerSet;
import static library.Reference.JJIMJIL_ZONE;
import static library.Reference.JjimJilZonePlayerSet;
import static library.Reference.TimerColdZone;
import static library.Reference.TimerHotZone;
import static library.Reference.TimerJimJilZone;
import static library.Reference.TimerWaterLevel;
//import static library.Reference.WaterLevelTargetCache;
//import static library.Reference.WaterLevelTargetMap;
import static library.Reference.WaterLevelTrackerSet;
import static library.Reference.currentRegionMap;
import static library.Reference.getCurrentRegion;
import static library.Reference.getTrackerFromPlayer;

import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import library.Reference;
import mint.seobaragi.player.tracker.PlayerWaterLevelTracker;
import mint.seobaragi.region.CuboidRegion;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class ServerMiscellaneousEventHandler
{
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			TimerHotZone.update(1);
			TimerColdZone.update(1);
			TimerJimJilZone.update(1);
			TimerWaterLevel.update(1);
		}
	}
	
	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event)
	{
		UUID uid = event.player.getUniqueID();
		CuboidRegion currentRegion = getCurrentRegion(event.player);
		
		currentRegionMap.put(uid, currentRegion);
		
//		int value = 1200;
//		if (WaterLevelTargetCache.containsKey(uid))
//			value = WaterLevelTargetCache.remove(uid);
//			
//		WaterLevelTargetMap.put(uid, value);
		
		
		WaterLevelTrackerSet.add(new PlayerWaterLevelTracker(event.player));
		
		if(currentRegion == HOT_ZONE)
			HotZonePlayerSet.add(uid);
		
		else if(currentRegion == COLD_ZONE)
			ColdZonePlayerSet.add(uid);
		
		else if(currentRegion == JJIMJIL_ZONE)
			JjimJilZonePlayerSet.add(uid);
		
	}
	
	@SubscribeEvent
	public void onLogOut(PlayerLoggedOutEvent event)
	{
		UUID uid = event.player.getUniqueID();
		CuboidRegion currentRegion = currentRegionMap.remove(uid);
		
//		int value = WaterLevelTargetMap.remove(uid);
//		WaterLevelTargetCache.put(uid, value);
		
		WaterLevelTrackerSet.remove(getTrackerFromPlayer(event.player));
		
		if (currentRegion == HOT_ZONE)
			Reference.HotZonePlayerSet.remove(uid);
		
		else if (currentRegion == COLD_ZONE)
			Reference.ColdZonePlayerSet.remove(uid);
		
		else if (currentRegion == JJIMJIL_ZONE)
			Reference.JjimJilZonePlayerSet.remove(uid);
	}
}
