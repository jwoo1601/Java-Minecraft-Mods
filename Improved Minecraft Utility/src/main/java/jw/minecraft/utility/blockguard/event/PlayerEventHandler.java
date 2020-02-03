package jw.minecraft.utility.blockguard.event;

import java.lang.ref.SoftReference;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jw.minecraft.utility.blockguard.BlockGuard;
import jw.minecraft.utility.blockguard.command.BlockGuardCommand;
import jw.minecraft.utility.blockguard.player.PlayerCache;
import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.event.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerEventHandler extends EventHandler {
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onEvent(PlayerInteractEvent e) {
		EntityPlayer player = e.entityPlayer;
		
		if (player.getHeldItem() != null && BlockGuard.toolEquals(player.getHeldItem())){
			
			switch (e.action) {
			case LEFT_CLICK_BLOCK:
				PlayerCache.getPlayerCache(player.getUniqueID()).setOrigin(Vec3i.createVector(e.x, e.y, e.z));
				e.entityPlayer.addChatComponentMessage(BlockGuard.tprintf("imu.bg.tool.leftclick.msg", e.x, e.y, e.z));
				break;
			case RIGHT_CLICK_BLOCK:
				PlayerCache.getPlayerCache(player.getUniqueID()).setEnd(Vec3i.createVector(e.x, e.y, e.z));
				e.entityPlayer.addChatComponentMessage(BlockGuard.tprintf("imu.bg.tool.rightclick.msg", e.x, e.y, e.z));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public EventBus getCurrentBus() {
		return MinecraftForge.EVENT_BUS;
	}
}
