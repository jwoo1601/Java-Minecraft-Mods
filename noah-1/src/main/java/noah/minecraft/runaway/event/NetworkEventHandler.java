package noah.minecraft.runaway.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import noah.minecraft.runaway.Noah;

public class NetworkEventHandler {

	@SubscribeEvent
	public void onDisconnected(ClientDisconnectionFromServerEvent e) {
		Noah.Proxy.CisGameStarted = false;
		Noah.Proxy.CCriminal = null;
	}
}
