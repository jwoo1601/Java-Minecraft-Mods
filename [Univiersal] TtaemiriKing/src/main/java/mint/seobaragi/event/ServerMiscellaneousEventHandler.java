package mint.seobaragi.event;

import static library.Reference.COLD_ZONE;
import static library.Reference.ColdZoneTrackerSet;
import static library.Reference.DEBUG;
import static library.Reference.HOT_ZONE;
import static library.Reference.HotZoneTrackerSet;
import static library.Reference.JJIMJIL_ZONE;
import static library.Reference.JjimJilZoneTrackerSet;
import static library.Reference.TimerColdZone;
import static library.Reference.TimerHotZone;
import static library.Reference.TimerJimJilZone;
import static library.Reference.TimerWaterLevel;
import static library.Reference.WaterLevelTrackerSet;
import static library.Reference.currentRegionMap;
import static library.Reference.getColdZoneTracker;
import static library.Reference.getCurrentRegion;
import static library.Reference.getHotZoneTracker;
import static library.Reference.getJjimjilZoneTracker;
import static library.Reference.getTrackerFromPlayer;
import static library.Reference.safeRemoveColdZoneTracker;
import static library.Reference.safeRemoveHotZoneTracker;
import static library.Reference.safeRemoveJjimjilZoneTracker;

import java.util.UUID;

import net.minecraft.server.MinecraftServer;
import library.Reference;
import mint.seobaragi.player.tracker.PlayerColdZoneTracker;
import mint.seobaragi.player.tracker.PlayerHotZoneTracker;
import mint.seobaragi.player.tracker.PlayerJjimjilZoneTracker;
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
		
		if(DEBUG)
			System.out.println("name = " + event.player.getDisplayName() + " uuid = " + uid);
		
		if(currentRegion == HOT_ZONE)
			HotZoneTrackerSet.add(new PlayerHotZoneTracker(event.player).startTracking());
		
		else if(currentRegion == COLD_ZONE)
			ColdZoneTrackerSet.add(new PlayerColdZoneTracker(event.player).startTracking());
		
		else if(currentRegion == JJIMJIL_ZONE)
			JjimJilZoneTrackerSet.add(new PlayerJjimjilZoneTracker(event.player).startTracking());
		
	}
	
	@SubscribeEvent
	public void onLogOut(PlayerLoggedOutEvent event)
	{
		UUID uid = event.player.getUniqueID();
		CuboidRegion currentRegion = currentRegionMap.remove(uid);
		
		if (DEBUG)
			System.out.println("Player Logged out <uuid=" + uid + " name=" + event.player.getDisplayName() + ">");
		
		getTrackerFromPlayer(event.player).stopTracking();
		
		if(currentRegion == HOT_ZONE)
			safeRemoveHotZoneTracker(event.player);
		
		else if(currentRegion == COLD_ZONE)
			safeRemoveColdZoneTracker(event.player);
		
		else if(currentRegion == JJIMJIL_ZONE)
			safeRemoveJjimjilZoneTracker(event.player);
	}
}
