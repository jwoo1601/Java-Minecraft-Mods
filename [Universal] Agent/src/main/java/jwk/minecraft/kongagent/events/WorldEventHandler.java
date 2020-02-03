package jwk.minecraft.kongagent.events;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jwk.minecraft.kongagent.KongAgent;
import jwk.minecraft.kongagent.repository.AdminData;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHandler {

	public WorldEventHandler(EventBus eventBus) {
		eventBus.register(this);
	}
	
	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load e) {
		if (!e.world.isRemote && e.world.provider.dimensionId == 0) {
			KongAgent.globalAdminData = AdminData.get(e.world);
			FMLLog.info("[KongAgent] AdminData is Loaded");
		}
	}
	
	@SubscribeEvent
	public void onWorldSaved(WorldEvent.Save e) {		
		if (!e.world.isRemote && e.world.provider.dimensionId == 0) {
			KongAgent.globalAdminData.markDirty();
			FMLLog.info("[KongAgent] AdminData is Saved");
		}
	}
	
}
