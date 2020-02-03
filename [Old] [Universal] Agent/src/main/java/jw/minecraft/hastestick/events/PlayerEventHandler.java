package jw.minecraft.hastestick.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jw.minecraft.hastestick.HasteStickMod;
import jw.minecraft.hastestick.NominationType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

public class PlayerEventHandler {
	
	public PlayerEventHandler(EventBus eventBus) {
		eventBus.register(this);
	}
	
	public static Map<String, NominationType> nominatedPlayerMap = new ConcurrentHashMap<String, NominationType>();

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onEntityInteract(EntityInteractEvent e) {
		
		if (e.target instanceof EntityPlayerMP) {
			
			ItemStack held = e.entityPlayer.getHeldItem();
			if (held != null && held.getItem() == Items.stick) {
				EntityPlayerMP target = (EntityPlayerMP) e.target;
				String name = target.getDisplayName();
			
				if (nominatedPlayerMap.containsKey(name)) {
					NominationType type = nominatedPlayerMap.get(name);
					
					switch (type) {
					case STICK:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.GOLD + " 막대"+ EnumChatFormatting.RESET + "로 지목되어 있습니다!"));
						break;
						
					case FEATHER:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.GOLD + " 깃털"+ EnumChatFormatting.RESET + "로 지목되어 있습니다!"));
						break;
						
					default:
						throw new IllegalArgumentException("type is not invalid value!");
					}
				}
				
				else {
					nominatedPlayerMap.put(name, NominationType.STICK);
					target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
					e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.GOLD + " 막대" + EnumChatFormatting.RESET + "로 지목했습니다."));
				}
			}
			
			else if (held != null && held.getItem() == Items.feather) {
				EntityPlayerMP target = (EntityPlayerMP) e.target;
				String name = target.getDisplayName();
			
				if (nominatedPlayerMap.containsKey(name)) {
					NominationType type = nominatedPlayerMap.get(name);
					
					switch (type) {
					case STICK:
						target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
						nominatedPlayerMap.put(name, NominationType.FEATHER);
						held.stackSize--;
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.GOLD + " 막대" + EnumChatFormatting.RESET + "로 다시 지목했습니다."));
						break;
						
					case FEATHER:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.GOLD + " 깃털"+ EnumChatFormatting.RESET + "로 지목되어 있습니다!"));
						break;
						
					default:
						throw new IllegalArgumentException("type is not invalid value!");
					}
				}
				
				else {
					target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
					nominatedPlayerMap.put(name, NominationType.FEATHER);
					held.stackSize--;
					e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.GOLD + " 깃털" + EnumChatFormatting.RESET + "로 지목했습니다."));
				}
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUseFinish(PlayerUseItemEvent.Finish e) {
		String name = e.entityPlayer.getDisplayName();
		
		if (nominatedPlayerMap.containsKey(name) && e.item != null && e.item.getItem() == Items.milk_bucket) {
			NominationType type = nominatedPlayerMap.get(name);
			
			switch (type) {
			case STICK:
				nominatedPlayerMap.remove(e.entityPlayer.getDisplayName());
				sendToAllAdmins(new ChatComponentText(EnumChatFormatting.AQUA + e.entityPlayer.getDisplayName() + "님의 효과가 " + EnumChatFormatting.GOLD + "제거" + EnumChatFormatting.RESET + "되었습니다."));
				break;
				
			default:
				throw new IllegalArgumentException("type is not invalid value!");
			}	
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUseStart(PlayerUseItemEvent.Start e) {
		String name = e.entityPlayer.getDisplayName();
		
		if (nominatedPlayerMap.containsKey(name) && e.item != null && e.item.getItem() == Items.milk_bucket && nominatedPlayerMap.get(name) == NominationType.FEATHER) {
			e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.AQUA + "당신" + EnumChatFormatting.RESET + "은" + EnumChatFormatting.RED + " 스파이" + EnumChatFormatting.RESET + "에게 "+ EnumChatFormatting.GOLD + "지목" + EnumChatFormatting.RESET + "되어 효과를 제거하실 수 없습니다."));
			e.setCanceled(true);
		}
	}
	
	public static void sendToAllAdmins(IChatComponent text) {
		for (String name : HasteStickMod.globalAdminData.adminSet) {
			EntityPlayer admin = getPlayerByName(name);
			
			if (admin != null)
				admin.addChatComponentMessage(text);
		}
	}
	
	public static EntityPlayer getPlayerByName(String name) {
		List<EntityPlayer> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		EntityPlayer target = null;
		for (EntityPlayer player : players) {
			
			if (player.getDisplayName().equals(name)) {
				target = player;
				break;
			}
		}
		
		return target;			
	}
}
