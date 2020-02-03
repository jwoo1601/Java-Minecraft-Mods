package jw.minecraft.utility.blockguard.event;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jw.minecraft.utility.Common;
import jw.minecraft.utility.LogHelper;
import jw.minecraft.utility.blockguard.BlockGuard;
import jw.minecraft.utility.blockguard.repository.BlockGuardData;
import jw.minecraft.utility.event.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHandler extends EventHandler {
	
	@SubscribeEvent
	public void onWorldLoaded(WorldEvent.Load e) {
		int wid = e.world.provider.dimensionId;
		
		switch (wid) {
		case Common.OVERWORLD_ID:
			BlockGuard.DATA[0] = BlockGuardData.get((byte)wid, e.world);
			break;
		case Common.NETHER_ID:
			BlockGuard.DATA[1] = BlockGuardData.get((byte)wid, e.world);
			break;
		case Common.THEEND_ID:
			BlockGuard.DATA[2] = BlockGuardData.get((byte)wid, e.world);
			break;
		}
	}
	
	@SubscribeEvent
	public void onWorldSaved(WorldEvent.Save e) {
		for (BlockGuardData data : BlockGuard.DATA)
			data.markDirty();
	}

	@Override
	public EventBus getCurrentBus() {
		return MinecraftForge.EVENT_BUS;
	}
}
