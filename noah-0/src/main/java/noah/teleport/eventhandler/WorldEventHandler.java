package noah.teleport.eventhandler;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import noah.teleport.Main;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;

public class WorldEventHandler {
	
	public WorldEventHandler(EventBus bus) {
		bus.register(this);
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if (Common.isClientThread()) {
			if (Main.Proxy.FakePlaceReg == null)
				Main.Proxy.FakePlaceReg = new PlaceRegistry(Side.CLIENT);
		}
		else if (Common.isServerThread()) {
			if (Main.Proxy.PlaceReg == null) {
				int size = 3;
				Main.Proxy.PlaceReg = new PlaceRegistry[size];
				
				for (int i=0; i<size; i++)
					Main.Proxy.PlaceReg[i] = new PlaceRegistry(Side.SERVER);
			}
		}
	}
	
/*	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload e) {
		if (Common.isClientThread())
			Main.Proxy.FakePlaceReg.finalize();
		else if (Common.isServerThread()) {
			Main.Proxy.PlaceReg = null;
		}
	} */
}
