package caramel.forge;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CaramelInfo.ID, name = CaramelInfo.NAME, version = CaramelInfo.VERSION,
     acceptedMinecraftVersions = CaramelInfo.MC_VERSION, acceptableRemoteVersions = CaramelInfo.MC_VERSION)
public class CaramelForge {
	
	@Instance
	public static CaramelForge instance;
	
	public static Logger logger;
	
	@EventHandler
	public void initialize(FMLPreInitializationEvent e) {
		CaramelInfo.setupMetadata(e.getModMetadata());
		
		logger = e.getModLog();
	}
	
}
