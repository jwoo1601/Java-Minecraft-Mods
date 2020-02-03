package noah.minecraft.runaway.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noah.minecraft.runaway.event.NetworkEventHandler;
import noah.minecraft.runaway.event.RenderHUDEventHandler;

public class Client extends Proxy {

	public static RenderHUDEventHandler Handler = new RenderHUDEventHandler();
	public static NetworkEventHandler Handler2 = new NetworkEventHandler();
	
	public void fmlLifeCycleEvent(FMLPreInitializationEvent e) {
		super.fmlLifeCycleEvent(e);
		
		MinecraftForge.EVENT_BUS.register(Handler);
		MinecraftForge.EVENT_BUS.register(Handler2);
	}
}
