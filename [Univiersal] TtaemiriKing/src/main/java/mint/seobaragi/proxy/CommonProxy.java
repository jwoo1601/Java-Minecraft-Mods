package mint.seobaragi.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import library.Reference;
import mint.seobaragi.event.ServerPlayerEventHandler;
import mint.seobaragi.event.ServerMiscellaneousEventHandler;
import mint.seobaragi.event.ServerWorldEventHandler;
import mint.seobaragi.packet.PacketPlayerStat;
import mint.seobaragi.packet.SeonengPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy
{
	public static final SimpleNetworkWrapper INSTANCE;
	
	//Packet
	static
	{
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
		INSTANCE.registerMessage(SeonengPacket.Handler.class, SeonengPacket.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(PacketPlayerStat.ClientHandler.class, PacketPlayerStat.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(PacketPlayerStat.ServerHandler.class, PacketPlayerStat.class, 2, Side.SERVER);
	}
	
	public void registerGuiEventHandler() {}
	public void registerRenderers() {}
	public void registerServerSideHandlers() {
		
	    	FMLCommonHandler.instance().bus().register(new ServerMiscellaneousEventHandler());
		   	MinecraftForge.EVENT_BUS.register(new ServerPlayerEventHandler());
		   	MinecraftForge.EVENT_BUS.register(new ServerWorldEventHandler());
	}

	public EntityPlayer getClientPlayer() { return null; }
	
}
