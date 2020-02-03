package jwk.minecraft.garden.shop;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.List;

import com.google.common.collect.Lists;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.item.category.ItemCategory;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.util.SoundUtil;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class ShopEventListener {

	public static void onSlotClicked(ShopManager manager, int index, EntityPlayerMP player) {
		ISlot slot = manager.getSlot(index);
		
		if (slot == null) {
			System.err.println("Invalid Slot Index: " + index  + " uuid: " + player.getUniqueID());
			return;
		}
		
		ITeam team = TeamRegistry.getTeamOf(player);
		
		if (team == null) {
			handleError(player, "소속 " + AQUA + "팀" + WHITE + "이 없어 " + GOLD + "구매" + WHITE + "가 불가능 합니다");
			return;
		}
		
		long price = slot.getPrice();
		long balance = CurrencyYD.INSTANCE.getManager().get(team);
		
		switch (manager.getShopType()) {
		
		case PURCHASE:
			
			if (price > balance) {
				handleError(player, "통장 " + GOLD + "잔고" + WHITE + "가" + RED + " 부족" + WHITE + "해 풀품을 " + GOLD + "구매"  + WHITE + "하실 수 없습니다");
				return;
			}
			
			if (!player.inventory.addItemStackToInventory(slot.getCategory().getItems()[0].copy())) {
				handleError(player, "인벤토리에 " + RED + "빈 공간 " + WHITE + "이 없어 " + GOLD + "구매" + WHITE + "하실 수 없습니다");
				return;
			}
			
			CurrencyYD.INSTANCE.getManager().decrease(team, price);			
			handleSuccess(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 을(를) " + GOLD + "구매" + WHITE + "했습니다 " + RED + "-" + price + GOLD + "Y");
			
			break;
			
		case SALE:
			ItemStack[] stacks = getAllStacksInCategory(player, slot.getCategory());
			
			if (stacks.length == 0) {
				handleError(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 이(가) 없어 " + GOLD + "구매" + WHITE + " 하실 수 없습니다");
				return;
			}
			
			int size = addAllStackSize(stacks);
			int amount = slot.getAmount();
			
			if (size < amount) {
				handleError(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 이(가) " + RED + (amount - size) + WHITE + " 개 " + RED + "부족" + WHITE + "합니다");
				return;
			}
			
			decreaseInOrder(player, stacks, amount);
			CurrencyYD.INSTANCE.getManager().increase(team, price);
			handleSuccess(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 을(를) " + GOLD + "판매" + WHITE + "했습니다 " + GOLD + "+" + price + "Y");
			
			break;
			
		}
	}
	
	public static void onSlotRightClicked(ShopManager manager, int index, EntityPlayerMP player) {
		ISlot slot = manager.getSlot(index);
		
		if (slot == null) {
			System.err.println("Invalid Slot Index: " + index  + " uuid: " + player.getUniqueID());
			return;
		}
		
		ITeam team = TeamRegistry.getTeamOf(player);
		
		if (team == null) {
			handleError(player, "소속 " + AQUA + "팀" + WHITE + "이 없어 " + GOLD + "구매" + WHITE + "가 불가능 합니다");
			return;
		}
		
		final int amount = 10;
		long price = slot.getPrice() * amount;
		long balance = CurrencyYD.INSTANCE.getManager().get(team);
		
		switch (manager.getShopType()) {
		
		case PURCHASE:
			
			if (price > balance) {
				handleError(player, "통장 " + GOLD + "잔고" + WHITE + "가" + RED + " 부족" + WHITE + "해 풀품을 " + GOLD + "구매"  + WHITE + "하실 수 없습니다");
				return;
			}
			
			ItemStack stack = slot.getCategory().getItems()[0].copy();
			int maxSize = stack.getMaxStackSize();
			int count = 0;
			
			while (count < amount) {
				
				if (maxSize >= amount) {
					stack.stackSize = amount;
					
					if (!player.inventory.addItemStackToInventory(stack)) {
						handleError(player, "인벤토리에 " + RED + "빈 공간 " + WHITE + "이 없어 " + GOLD + "구매" + WHITE + "하실 수 없습니다");
						return;
					}
					
					count += amount;
				}
				
				else {
					ItemStack stack_c = stack.copy();
					
					int i = maxSize; for (;;) {
						
						if (count + i > amount)
							--i;
						else
							break;
					}
					
					stack_c.stackSize = i;
					
					if (!player.inventory.addItemStackToInventory(stack_c)) {
						
						if (count == 0) {
							handleError(player, "인벤토리에 " + RED + "빈 공간 " + WHITE + "이 없어 " + GOLD + "구매" + WHITE + "하실 수 없습니다");
							return;
						}
						
						else
							break;
					}
					
					else
						count += i;
				}
			}
			
			long result = slot.getPrice() * count;
			
			CurrencyYD.INSTANCE.getManager().decrease(team, result);			
			handleSuccess(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 을(를) " + count + "개 " + GOLD + "구매" + WHITE + "했습니다 " + RED + "-" + result + GOLD + "Y");
			
			break;
			
		case SALE:
			ItemStack[] stacks = getAllStacksInCategory(player, slot.getCategory());
			
			if (stacks.length == 0) {
				handleError(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 이(가) 없어 " + GOLD + "구매" + WHITE + " 하실 수 없습니다");
				return;
			}
			
			int size = addAllStackSize(stacks);
			
			if (size < amount) {
				
				int availableCount = size / slot.getAmount();
				if (availableCount == 0) {					
					handleError(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 이(가) " + RED + (slot.getAmount() - size) + WHITE + " 개 " + RED + "부족" + WHITE + "합니다");
					return;
				}
				
			    int totalCount = slot.getAmount() * availableCount;
				long totalPrice = totalCount * slot.getPrice();
				
				decreaseInOrder(player, stacks, totalCount);
				CurrencyYD.INSTANCE.getManager().increase(team, totalPrice);
				handleSuccess(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 을(를) " + totalCount + "개 " + GOLD + "판매" + WHITE + "했습니다 " + GOLD + "+" + totalPrice + "Y");
			}
			
			else {
				decreaseInOrder(player, stacks, amount);
				CurrencyYD.INSTANCE.getManager().increase(team, price);
				handleSuccess(player, AQUA + slot.getCategory().getDisplayName() + WHITE + " 을(를) " + amount + "개 " + GOLD + "판매" + WHITE + "했습니다 " + GOLD + "+" + price + "Y");
			}
			
			break;
			
		}
	}
	
	private static ItemStack[] getAllStacksInCategory(EntityPlayerMP player, ItemCategory category) {
		ItemStack[] inventory = player.inventory.mainInventory;
		ItemStack[] stacks = new ItemStack[inventory.length];
		
		for (int i=0; i < inventory.length; i++) {
			
			if (inventory[i] != null && category.isItemInCategory(inventory[i]))
				stacks[i] = inventory[i];
			else
				stacks[i] = null;
		}
		
		return stacks;
	}
	
	private static int addAllStackSize(ItemStack[] stacks) {
		int sum = 0;
		
		for (ItemStack stack : stacks) {
			
			if (stack != null && stack.stackSize != 0)
				sum += stack.stackSize;
		}
		
		return sum;
	}
	
	private static void decreaseInOrder(EntityPlayerMP player, ItemStack[] stacks, int totalAmount) {
		
		for (int i=0; i < stacks.length; i++) {
			
			if (totalAmount == 0)
				return;
			
			if (stacks[i] == null)
				continue;
			
			int size = stacks[i].stackSize;
			
			if (size <= totalAmount) {
				player.inventory.decrStackSize(i, size);
				totalAmount -= size;
			}
			
			else {
				player.inventory.decrStackSize(i, totalAmount);
				return;
			}
		}
	}
	
	private static void handleError(EntityPlayerMP player, String message) {
		player.addChatComponentMessage(ProjectGarden.toFormatted(message));
		SoundUtil.playSoundTo(player, SoundType.SHOP_SLOT_FAIL);
	}
	
	private static void handleSuccess(EntityPlayerMP player, String message) {
		player.addChatComponentMessage(ProjectGarden.toFormatted(message));
		SoundUtil.playSoundTo(player, SoundType.SHOP_SLOT_SUCCESS);
	}
	
}
