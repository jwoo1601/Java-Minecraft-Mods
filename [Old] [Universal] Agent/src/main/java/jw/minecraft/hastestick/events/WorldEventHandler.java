package jw.minecraft.hastestick.events;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jw.minecraft.hastestick.HasteStickMod;
import jw.minecraft.hastestick.repository.AdminData;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHandler {

	public WorldEventHandler(EventBus eventBus) {
		eventBus.register(this);
	}
	
	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load e) {
		if (e.world.provider.dimensionId == 0) {
			HasteStickMod.globalAdminData = AdminData.get(e.world);
			FMLLog.info("[HasteStickMod] AdminData is loaded");
		}
	}
	
	@SubscribeEvent
	public void onWorldSaved(WorldEvent.Save e) {
		if (e.world.provider.dimensionId == 0) {
			HasteStickMod.globalAdminData.markDirty();
			FMLLog.info("[HasteStickMod] AdminData is saved");
		}
	}
}
