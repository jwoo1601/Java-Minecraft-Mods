package jwk.minecraft.garden.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.timer.SidedTickListenerList;

public class ServerEventHandler {

	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent e) {
		
		if (e.phase == TickEvent.Phase.START) {
			SidedTickListenerList list = ProjectGarden.proxy.getServerTickListenerList();
			
			if (list != null)
				list.updateListeners();
		}
	}
	
}
