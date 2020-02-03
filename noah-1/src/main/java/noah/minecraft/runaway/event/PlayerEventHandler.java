package noah.minecraft.runaway.event;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import noah.minecraft.runaway.Noah;
import noah.minecraft.runaway.command.SpongeCommand;
import noah.minecraft.runaway.packet.SpongePacket;

public class PlayerEventHandler {
	
	@SubscribeEvent
	public void onPlayerDead(LivingDeathEvent e) {
		/*System.out.println("1=" + e.entityLiving.getClass().toString());
		System.out.println("2=" + e.source.getSourceOfDamage().getClass().toString());
		System.out.println("3=" + e.entity.getClass().toString());
		System.out.println("Side=" + Thread.currentThread().getName()); */
		if (e.entityLiving instanceof EntityPlayerMP && e.source.getSourceOfDamage() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) e.entityLiving;
			EntityPlayer crim = (EntityPlayer) e.source.getSourceOfDamage();
			
			if (player.getUniqueID().equals(Noah.Proxy.Criminal.getUniqueID())) {		
				ItemStack[] objstack = player.inventory.mainInventory;
				ItemStack[] sbjstack = crim.inventory.mainInventory;
				
				for (int i=0; i < objstack.length; i++) {
					ItemStack s = objstack[i];
					
					if (s != null && s.hasDisplayName() && s.getDisplayName().equals(EnumChatFormatting.GOLD + "진짜 스펀지")) {
						player.inventory.removeStackFromSlot(i);
						player.inventoryContainer.putStackInSlot(44, new ItemStack(Blocks.sponge).setStackDisplayName("가짜 스펀지"));
						
						for (int j=0; j < sbjstack.length; j++) {
							ItemStack k = sbjstack[j];
							
							if (k != null && k.hasDisplayName() && k.getDisplayName().equals("가짜 스펀지")) {
								crim.inventory.removeStackFromSlot(j);
								crim.inventoryContainer.putStackInSlot(44, new ItemStack(Blocks.sponge).setStackDisplayName(EnumChatFormatting.GOLD + "진짜 스펀지"));
								
								Noah.Proxy.Criminal = crim;
								MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.RED + "범인" + EnumChatFormatting.WHITE + "이 살해당했습니다!"));
								Noah.Proxy.HINSTANCE.sendToAll(new SpongePacket(SpongePacket.Type.CHANGED, crim.getUniqueID()));
								return;
							}
						}
						
					}
					}
				
				crim.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "죽인 사람은 범인이 아닙니다!"));
				}
			}
		} 
	}
