package jwk.minecraft.fogmanager.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.fogmanager.animation.FogAnimationHelper;
import jwk.minecraft.fogmanager.config.Configuration;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;

@SideOnly(Side.CLIENT)
public class TickEventHandler {

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent e) {
		FogAnimationHelper helper = Configuration.getInstance().AnimationHelper;
		
		if (helper.getState()) {
			helper.update(1);
			
			if (helper.getCurrentAnimation() != null) {
				FogConfig fog = Configuration.getInstance().fog();
				
				float result = fog.getStartDepth() + helper.getCurrentPosition();
				fog.setStartDepth(result >= 0.0f ? result : 0.0f);
			}
		}
	}
	
}
