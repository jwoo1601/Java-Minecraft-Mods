package jwk.suddensurvival;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import jwk.suddensurvival.proxy.CommonProxy;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, acceptedMinecraftVersions = ModInfo.MC_VERSION, acceptableRemoteVersions = ModInfo.MC_VERSION)
public class SuddenSurvival {
	
	@Instance
	public static SuddenSurvival instance;
	
	@SidedProxy(clientSide = "jwk.suddensurvival.proxy.Client", serverSide = "jwk.suddensurvival.proxy.Server")
	public static CommonProxy proxy;
	
	public static Logger logger;
	
	@EventHandler
	public void initialize(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void load(FMLInitializationEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void postInitialize(FMLPostInitializationEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverInitialize(FMLServerAboutToStartEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverPostInitialize(FMLServerStartedEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void serverStop(FMLServerStoppingEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
	@EventHandler
	public void postStop(FMLServerStoppedEvent e) {
		proxy.onFMLLifeCycleEvent(e);
	}
	
}
