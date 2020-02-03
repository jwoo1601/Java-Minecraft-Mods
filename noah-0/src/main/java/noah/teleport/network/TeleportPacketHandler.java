package noah.teleport.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import noah.teleport.util.Common;

public class TeleportPacketHandler {
	
	public static final SimpleNetworkWrapper INSTANCE;
	
	public static int UID = 0;
	
	static {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Common.MOD_ID);
		INSTANCE.registerMessage(PlaceDataChangedPacket.CPDCPacketHandler.class, PlaceDataChangedPacket.class, UID++, Side.CLIENT);
		INSTANCE.registerMessage(PlaceDataChangedPacket.SPDCPacketHandler.class, PlaceDataChangedPacket.class, UID++, Side.SERVER);
	}
}
