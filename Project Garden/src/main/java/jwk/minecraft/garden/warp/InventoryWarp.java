package jwk.minecraft.garden.warp;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class InventoryWarp implements IInventory {
	
	private String name = EnumChatFormatting.BLACK + "텔레포트";
	
	public static final BlockPos POS_FLOWER_FACTORY = new BlockPos(-194, 4, -147);
	
	private WarpData[] array = new WarpData[9]; // 2 6
	
	public InventoryWarp(BlockPos posFlowerShop) {
		array[0] = WarpData.none();
		array[1] = WarpData.none();
		array[2] = new WarpData(EnumChatFormatting.GOLD + "공장" + EnumChatFormatting.WHITE + "으로", POS_FLOWER_FACTORY.clone(), new ItemStack(ProjectGarden.proxy.ITEM_FLOWER_FACTORY)); // factory
		array[3] = WarpData.none();
		array[4] = WarpData.none();
		array[5] = WarpData.none();
		array[6] = new WarpData(EnumChatFormatting.GREEN + "가게" + EnumChatFormatting.WHITE + "로", posFlowerShop, new ItemStack(ProjectGarden.proxy.ITEM_FLOWER_SHOP)); // shop
		array[7] = WarpData.none();
		array[8] = WarpData.none();
	}
	
	public void setFlowerShopPosition(BlockPos pos) {
		array[6].setPosition(pos);
	}
	
	public WarpData getWarpData(int index) {
		
		if (index >= 0 && index < array.length)
			return array[index];
		
		return null;
	}
	
	public boolean hasTargetPosition(int index) {
		
		if (index >= 0 && index < array.length)
			return array[index].getPosition() != null;
		
		return false;
	}

	@Override
	public int getSizeInventory() { return array.length; }

	@Override
	public ItemStack getStackInSlot(int index) {
		
		if (index >= 0 && index < array.length)
			return array[index].getDisplayStack();
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return getStackInSlot(index);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return getStackInSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) { }

	@Override
	public String getInventoryName() { return name;	}

	@Override
	public boolean hasCustomInventoryName() { return name != null; }

	@Override
	public int getInventoryStackLimit() { return 1; }

	@Override
	public void markDirty() { }

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) { return true; }

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {	return false; }

}
