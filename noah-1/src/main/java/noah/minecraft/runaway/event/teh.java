package noah.minecraft.runaway.event;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class teh {
	
	@SubscribeEvent
	public void onEvent(BlockEvent.BreakEvent e) {
		System.out.println("BlockEvent.BreakEvent");
	}
	
	@SubscribeEvent
	public void onEvent(PlayerEvent.HarvestCheck e) {
		System.out.println("PlayerEvent.HarvestCheck");
	}
	
	@SubscribeEvent
	public void onEvent(PlayerEvent.BreakSpeed e) {
		System.out.println("PlayerEvent.BreakSpeed");
	}
	
	@SubscribeEvent
	public void onEvent(BlockEvent.PlaceEvent e) {
		System.out.println("BlockEvent.PlaceEvent");
	}
	
	@SubscribeEvent
	public void onEvent(UseHoeEvent e) {
		System.out.println("UseHoeEvent e");
	}
	
	@SubscribeEvent
	public void onEvent(PlayerUseItemEvent.Start e) {
		System.out.println("PlayerUseItemEvent.Start");
	}
	
	@SubscribeEvent
	public void onEvent(InputEvent.MouseInputEvent e) {
		System.out.println("MouseInput");
	}
}
