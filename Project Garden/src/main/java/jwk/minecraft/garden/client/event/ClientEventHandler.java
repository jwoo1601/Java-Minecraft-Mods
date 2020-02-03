package jwk.minecraft.garden.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.timer.SidedTickListenerList;

@SideOnly(Side.CLIENT)
public class ClientEventHandler {

	public static volatile long previousTicks = 0L;
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) {
		
		if (e.phase == TickEvent.Phase.START) {
			
			previousTicks = System.nanoTime();
			
			SidedTickListenerList list = ProjectGarden.proxy.getClientTickListenerList();
			
			if (list != null)
				list.updateListeners();
		}
	}
	
}
