package jw.minecraft.utility.blockguard.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import jw.minecraft.utility.LogHelper;
import jw.minecraft.utility.blockguard.player.PlayerCache;
import jw.minecraft.utility.event.EventHandler;
import net.minecraft.entity.player.EntityPlayerMP;

public class NetworkEventHandler extends EventHandler {
	
	@SubscribeEvent
	public void onEvent(PlayerEvent.PlayerLoggedInEvent e) {
		//if (e.player instanceof EntityPlayerMP) {
			PlayerCache.put(e.player.getUniqueID(), null);
			LogHelper.info("Inserted PlayerCache <name=" + e.player.getDisplayName() + ", uuid=" + e.player.getUniqueID() + ">");
		//}
	}
	
	@SubscribeEvent
	public void onEvent(PlayerEvent.PlayerLoggedOutEvent e) {
		//if (e.player instanceof EntityPlayerMP) {
			PlayerCache.remove(e.player.getUniqueID());
			LogHelper.info("Removed PlayerCache <name=" + e.player.getDisplayName() + ", uuid=" + e.player.getUniqueID() + ">");
		//} 
	}
	
	@Override
	public EventBus getCurrentBus() {
		return FMLCommonHandler.instance().bus();
	}

}
