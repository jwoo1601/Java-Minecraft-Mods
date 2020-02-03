package noah.teleport.eventhandler;

import net.minecraft.block.properties.PropertyHelper;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Test {

	TileEntity tie;
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent e) {
		if (e.action == Action.RIGHT_CLICK_BLOCK && !e.world.isRemote) {
		}
	}
	
//	@SubscribeEvent
//	public void onOpenContainer(TickEvent.ClientTickEvent e) {
//		if (e.phase == Phase.START && Minecraft.getMinecraft().currentScreen != null) {
//			System.out.println(Minecraft.getMinecraft().currentScreen.getClass().getName());
//	}
//	}
	
	@SubscribeEvent
	public void onServerChatHandled(ServerChatEvent e) {
		e.setComponent(new ChatComponentText(e.username + EnumChatFormatting.DARK_GREEN + " *" + EnumChatFormatting.GREEN + "*" + EnumChatFormatting.DARK_GREEN + "* " + EnumChatFormatting.RESET + e.message));
	}
}
