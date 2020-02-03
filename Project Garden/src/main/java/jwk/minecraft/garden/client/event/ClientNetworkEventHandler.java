package jwk.minecraft.garden.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;

@SideOnly(Side.CLIENT)
public class ClientNetworkEventHandler {

	@SubscribeEvent
	public void onDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
		System.out.println("on Disconnected");
		ProjectGarden.proxy.unloadClientResources();
	}
	
}
