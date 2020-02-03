package jwk.minecraft.fogmanager;

import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import jwk.minecraft.fogmanager.animation.AnimatedFog;
import jwk.minecraft.fogmanager.config.Configuration;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;
import jwk.minecraft.fogmanager.enumerations.EnumFogQuality;
import jwk.minecraft.fogmanager.enumerations.EnumFogRenderOption;
import jwk.minecraft.fogmanager.network.PacketFog;
import jwk.minecraft.fogmanager.network.PacketFogAnimation;
import jwk.minecraft.fogmanager.server.commands.CommandFog;
import jwk.minecraft.fogmanager.utils.Color4f;
import cpw.mods.fml.common.Mod;

@Mod(modid = "fogmanager", name = "Minecraft Fog Manager", version = "1.7.10-0.2.0.0", useMetadata = true)
public class FogManager {
	
	@Instance("fogmanager")
	public static FogManager instance;
	
	@SidedProxy(clientSide = "jwk.minecraft.fogmanager.Client", serverSide = "jwk.minecraft.fogmanager.Server")
	public static IProxy proxy;
	
	public static final SimpleNetworkWrapper NET_HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("fogmanager");
	
	static {
		NET_HANDLER.registerMessage(PacketFog.ClientHandler.class, PacketFog.class, 0, Side.CLIENT);
		NET_HANDLER.registerMessage(PacketFogAnimation.ClientHandler.class, PacketFogAnimation.class, 1, Side.CLIENT);
	}
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e) {
		proxy.registerRenderEventHandlers();
		proxy.registerTickEventHandlers();
	}
	
	@EventHandler
	public void serverInitialize(FMLServerStartingEvent e) {
		e.registerServerCommand(new CommandFog());
	}
	
}
