package noah.teleport;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import noah.teleport.proxy.CommonProxy;
import noah.teleport.util.Common;

@Mod(modid=Common.MOD_ID, name=Common.MOD_DEF_NAME, version=Common.MOD_VERSION, 
     acceptedMinecraftVersions="1.8.",
     acceptableRemoteVersions="1.8.",
     acceptableSaveVersions="1.8.")
public final class Main {
	
	@Instance(Common.MOD_ID)
	public static Main Instance;
	
	@SidedProxy(clientSide="noah.teleport.proxy.Client", 
	            serverSide="noah.teleport.proxy.Server")
    public static CommonProxy Proxy;
	
	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e) {
		Proxy.fmlLifeCycleEvent(e);
	}
	
	@EventHandler
	public void Initialize(FMLInitializationEvent e) {
		Proxy.fmlLifeCycleEvent(e);
	}
	
	@EventHandler
	public void ServerStarting(FMLServerStartingEvent e) {
		Proxy.fmlLifeCycleEvent(e);
	}
}
