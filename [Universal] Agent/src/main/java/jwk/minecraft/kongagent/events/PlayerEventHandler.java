package jwk.minecraft.kongagent.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import jwk.minecraft.kongagent.KongAgent;
import jwk.minecraft.kongagent.enumerations.NominationType;
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
		boolean decreaseStack = false;
		
		if (e.target instanceof EntityPlayerMP) {
			
			ItemStack held = e.entityPlayer.getHeldItem();
			if (held != null && held.getItem() == KongAgent.BLUE_WATER_GUN) {
				EntityPlayerMP target = (EntityPlayerMP) e.target;
				String name = target.getDisplayName();
			
				if (nominatedPlayerMap.containsKey(name)) {
					NominationType type = nominatedPlayerMap.get(name);
					
					switch (type) {
					case DEFAULT:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.BLUE + " 파란 물총"+ EnumChatFormatting.RESET + "으로 지목되어 있습니다."));
						break;
						
					case SPY:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.YELLOW + " 노란 물총"+ EnumChatFormatting.RESET + "으로 지목되어 있습니다."));
						break;
						
					default:
						throw new IllegalArgumentException("type is not invalid value!");
					}
				}
				
				else {
					nominatedPlayerMap.put(name, NominationType.DEFAULT);
					target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
					
					IChatComponent text = new ChatComponentText(EnumChatFormatting.GOLD + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.BLUE + " 파란 물총" + EnumChatFormatting.RESET + "으로 맞췄습니다!");
					sendToAllAdmins(text);
					
					if (!KongAgent.globalAdminData.adminSet.contains(e.entityPlayer.getDisplayName()))
						e.entityPlayer.addChatComponentMessage(text);
				}
			}
			
			else if (held != null && held.getItem() == KongAgent.YELLOW_WATER_GUN) {
				EntityPlayerMP target = (EntityPlayerMP) e.target;
				String name = target.getDisplayName();
			
				if (nominatedPlayerMap.containsKey(name)) {
					NominationType type = nominatedPlayerMap.get(name);
					
					switch (type) {
					case DEFAULT:
						target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
						nominatedPlayerMap.put(name, NominationType.SPY);
						
						decreaseStack = true;
						
						IChatComponent text = new ChatComponentText(EnumChatFormatting.GOLD + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.YELLOW + " 노란 물총" + EnumChatFormatting.RESET + "으로 다시 지목했습니다.");
						sendToAllAdmins(text);
						
						if (!KongAgent.globalAdminData.adminSet.contains(e.entityPlayer.getDisplayName()))
							e.entityPlayer.addChatComponentMessage(text);
						break;
						
					case SPY:
						e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "대상" + EnumChatFormatting.RESET + "이 이미" + EnumChatFormatting.YELLOW + " 노란 물총"+ EnumChatFormatting.RESET + "으로 지목되어 있습니다!"));
						break;
						
					default:
						throw new IllegalArgumentException("type is not invalid value!");
					}
				}
				
				else {
					target.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
					nominatedPlayerMap.put(name, NominationType.SPY);
					
					decreaseStack = true;
					
					IChatComponent text = new ChatComponentText(EnumChatFormatting.GOLD + name + EnumChatFormatting.RESET + " 님을" + EnumChatFormatting.YELLOW + " 노란 물총" + EnumChatFormatting.RESET + "으로 지목했습니다.");
					sendToAllAdmins(text);
					
					if (!KongAgent.globalAdminData.adminSet.contains(e.entityPlayer.getDisplayName()))
						e.entityPlayer.addChatComponentMessage(text);
				}
			}
		}
		
		if (decreaseStack)
			e.entityPlayer.inventory.decrStackSize(e.entityPlayer.inventory.currentItem, 1);
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUseFinish(PlayerUseItemEvent.Finish e) {
		if (!e.entityPlayer.worldObj.isRemote) {
			String name = e.entityPlayer.getDisplayName();
		
			if (nominatedPlayerMap.containsKey(name) && e.item != null && e.item.getItem() == Items.milk_bucket) {
				NominationType type = nominatedPlayerMap.get(name);
			
				switch (type) {
				
				case DEFAULT:
					
					nominatedPlayerMap.remove(e.entityPlayer.getDisplayName());
					
					e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.RESET + "이 " + EnumChatFormatting.RED + "제거" + EnumChatFormatting.RESET + "되었습니다"));
					sendToAllAdmins(new ChatComponentText(EnumChatFormatting.GOLD + e.entityPlayer.getDisplayName() + " 님의 " + EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.RESET + "이 " + EnumChatFormatting.RED + "제거" + EnumChatFormatting.RESET + "되었습니다."));
					break;
					
				case SPY:
					
					if (!e.entityPlayer.isPotionActive(Potion.digSpeed))
						e.entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), Integer.MAX_VALUE, 1));
					FMLLog.info("[KongAgent] Solved");
					
					break;
				
				default:
					throw new IllegalArgumentException("type is not invalid value!");
				}	
			}
		}
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onItemUseStart(PlayerUseItemEvent.Start e) {
		if (!e.entityPlayer.worldObj.isRemote) {
			String name = e.entityPlayer.getDisplayName();
		
			if (nominatedPlayerMap.containsKey(name) && e.item != null && e.item.getItem() == Items.milk_bucket && nominatedPlayerMap.get(name) == NominationType.SPY) {
				e.entityPlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "당신" + EnumChatFormatting.RESET + "은" + EnumChatFormatting.RED + " 스파이" + EnumChatFormatting.RESET + "에게 "+ EnumChatFormatting.GOLD + "지목" 
		                                                                 + EnumChatFormatting.RESET + "되어 " + EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.RESET + "을 " + EnumChatFormatting.RED + "제거" + EnumChatFormatting.RESET + "하실 수 없습니다."));
				e.setCanceled(true);
			}
		}
	}
	
	public static void sendToAllAdmins(IChatComponent text) {
		for (String name : KongAgent.globalAdminData.adminSet) {
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
