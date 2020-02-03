package jwk.minecraft.garden.team.inventory;

import static com.google.common.base.Preconditions.checkNotNull;
import static net.minecraft.util.EnumChatFormatting.*;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import jwk.minecraft.garden.command.CommandInterface;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.item.category.Categories;
import jwk.minecraft.garden.item.category.ItemCategory;
import jwk.minecraft.garden.shop.ImmutablePriceRegistry;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamChest;
import jwk.minecraft.garden.team.TeamEventListener;
import jwk.minecraft.garden.team.TeamEventListener.ErrorType;
import jwk.minecraft.garden.util.INBTSerializable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.common.util.Constants.NBT;

public class InventoryTeamChest implements IInventory, INBTSerializable {
	
	public static final String SUFFIX = WHITE + " 배달창";
	
	private ItemStack[] stacks = new ItemStack[54];
	private ITeam teamObj;
	
	public InventoryTeamChest(@Nonnull ITeam team) {
		this.teamObj = checkNotNull(team);
	}
	
	public ITeam getTeam() { return teamObj; }

	@Override
	public int getSizeInventory() {
		return stacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return stacks[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack target = getStackInSlot(index);
		
		if (target == null)
			return null;
		
		ItemStack removed;
		if (target.stackSize <= count) {
			removed = target;
			setInventorySlotContents(index, null);
		}
		
		else {
			removed = target.splitStack(count);
			
			if (target.stackSize == 0)
				setInventorySlotContents(index, null);
		}
		
		markDirty();
		
		return removed;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack target = stacks[index];
		
        if (target == null)
        	return null;
        
        stacks[index] = null;        
        return target;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		stacks[index] = stack; 
		
		int limitSize = getInventoryStackLimit();
		if (stack != null && stack.stackSize > limitSize) 
 			stack.stackSize = limitSize;

		markDirty(); 
	}
	
	public void clearInventory() {
		
		for (int i=0; i < stacks.length; i++) {
			
			if (stacks[i] != null)
				stacks[i] = null;
		}
	}
	
	public List<ItemStack> clearAndGetAllContents() {
		List<ItemStack> list = getAllContents();
		clearInventory();
		
		return list;
	}
	
	public List<ItemStack> getAllContents() {
		List<ItemStack> list = Lists.newArrayList();
		
		for (int i=0; i < stacks.length; i++) {
			
			if (stacks[i] != null)
				list.add(stacks[i]);
		}
		
		return list;
	}

	@Override
	public String getInventoryName() {
		return teamObj.getTeamName() + SUFFIX;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() { }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;//CommandInterface.isOp(player.getDisplayName());
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		ItemCategory category = Categories.fromItemStack(stack);
		
		if (category != null && ImmutablePriceRegistry.hasImmutablePrice(category))
			return true;
		
		return false;
	}
	
	private long getSumOf(List<ItemStack> contents) {
		long sum = 0L;
		
		for (ItemStack stack : contents) {
			ItemCategory category = Categories.fromItemStack(stack);
			
			if (category != null && ImmutablePriceRegistry.hasImmutablePrice(category))
				sum += (ImmutablePriceRegistry.getPriceOf(category) * stack.stackSize);
		}
		
		return sum;
	}
	
	public void deliverContents(EntityPlayer player) {
		TeamChest chest = teamObj.getManager().getTeamChest();
		TeamEventListener listener = teamObj.getManager().getEventListener();
		
		if (chest == null) {
			listener.onFailedToDeliver(player, teamObj, ErrorType.NO_CURRENT_DEST);
			return;
		}
		
		List<ItemStack> contents = getAllContents();
		
		List<Integer> availableIndices = Lists.newArrayList();
		for (int i=0; i < chest.getSizeInventory(); i++) {
			
			if (chest.getStackInSlot(i) == null)
				availableIndices.add(i);
		}
		
		if (availableIndices.size() < contents.size()) {
			listener.onFailedToDeliver(player, teamObj, ErrorType.NOT_ENOUGH_SPACE);
			return;
		}
		
		long sum = getSumOf(contents);
		
		if (sum == 0L) {
			listener.onFailedToDeliver(player, teamObj, ErrorType.NO_IMMUTABLE_PRICE);
			return;
		}
		
		long current = CurrencyYD.INSTANCE.getManager().get(teamObj);
		
		if (current < sum) {
			listener.onFailedToDeliver(player, teamObj, ErrorType.NOT_ENOUGH_MONEY);
			return;
		}
		
		long result = CurrencyYD.INSTANCE.getManager().decrease(teamObj, sum);
		
		for (int j=0; j < contents.size(); j++)
			chest.setInventorySlotContents(availableIndices.get(j), contents.get(j));
		
		listener.onDeliverContents(player, teamObj, sum, result, contents);
		clearInventory();
	}

	
	private static final String KEY_INV_TEAM = "Inventory TeamChest";
	private static final String KEY_STACK_BASE = "Stack -";
	private static final String VALUE_EMPTY = "Empty";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagInv = new NBTTagCompound();
		
		for (int i=0; i < stacks.length; i++) {
			
			if (stacks[i] == null) {
				tagInv.setString(KEY_STACK_BASE + i, VALUE_EMPTY);
				continue;
			}
			
			NBTTagCompound tagStack = new NBTTagCompound();
			stacks[i].writeToNBT(tagStack);
			tagInv.setTag(KEY_STACK_BASE + i, tagStack);
		}
		
		tagCompound.setTag(KEY_INV_TEAM, tagInv);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagInv = null;
		
		if (tagCompound.hasKey(KEY_INV_TEAM, NBT.TAG_COMPOUND))
			tagInv = tagCompound.getCompoundTag(KEY_INV_TEAM);
		else
			throw new IllegalArgumentException();
		
		for (int i=0; i < stacks.length; i++) {
			String key = KEY_STACK_BASE + i;
			
			if (tagInv.hasKey(key, NBT.TAG_STRING)) {
				stacks[i] = null;
				continue;
			}
			
			else if (tagInv.hasKey(key, NBT.TAG_COMPOUND)) {
				NBTTagCompound tagStack = tagInv.getCompoundTag(key);
				
				if (stacks[i] == null)
					stacks[i] = new ItemStack(Items.apple);
				
				stacks[i].readFromNBT(tagStack);
			}
			
			else
				throw new IllegalArgumentException();
		}
	}

}
