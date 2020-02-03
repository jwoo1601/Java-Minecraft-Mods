package mint.seobaragi.event;

import static library.Reference.TimerColdZone;
import static library.Reference.TimerHotZone;
import static library.Reference.TimerJimJilZone;
import static library.Reference.TimerWaterLevel;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ServerWorldEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		
		if (e.world.provider.dimensionId == 0) {
			TimerHotZone.start();
			TimerColdZone.start();
			TimerJimJilZone.start();
			TimerWaterLevel.start();
		}
	}
	
	@SubscribeEvent
	public void onWorldUnLoad(WorldEvent.Unload e) {
		
		if (e.world.provider.dimensionId == 0)
		{
			TimerHotZone.stop();
			TimerColdZone.stop();
			TimerJimJilZone.stop();
			TimerWaterLevel.stop();
		}
	}
}
