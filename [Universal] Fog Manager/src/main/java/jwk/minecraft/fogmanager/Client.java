package jwk.minecraft.fogmanager;

import cpw.mods.fml.common.FMLCommonHandler;
import jwk.minecraft.fogmanager.events.RenderEventHandler;
import jwk.minecraft.fogmanager.events.TickEventHandler;
import net.minecraftforge.common.MinecraftForge;

public class Client implements IProxy {

	@Override
	public void registerRenderEventHandlers() {
		MinecraftForge.EVENT_BUS.register(new RenderEventHandler());
	}

	@Override
	public void registerTickEventHandlers() {
		FMLCommonHandler.instance().bus().register(new TickEventHandler());
	}

}
