package jwk.minecraft.garden.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.flower.FlowerInfoCache;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.flower.Flowers;
import jwk.minecraft.garden.repository.FlowerPropsRepository;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHandler {

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {		
		ProjectGarden.proxy.onSidedWorldLoad(e.world);
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save e) {
		ProjectGarden.proxy.onSidedWorldSave(e.world);
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload e) {
		ProjectGarden.proxy.onSidedWorldUnload(e.world);
	}
	
}
