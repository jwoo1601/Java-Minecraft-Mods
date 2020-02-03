package jwk.minecraft.garden.warp;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWarp extends Container {

	private InventoryWarp inventoryWarp;
	
	public ContainerWarp(@Nonnull InventoryWarp invWarp) {
		inventoryWarp = checkNotNull(invWarp);
		
		final int XOffset = 18, YOffset = 18;
		final int startX = 8, startY = 88; // 18
		
		for (int x=0; x < 9; ++x)
			addSlotToContainer(new Slot(invWarp, x, startX + XOffset * x, startY));
	}
	
	@Override
    public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer player) {
    	
		if (slotId >= 0 && slotId < inventoryWarp.getSizeInventory()) {
			
			if (clickedButton == 0) {
    		
				if (inventoryWarp.hasTargetPosition(slotId)) {
					BlockPos pos = inventoryWarp.getWarpData(slotId).getPosition();
    			
					player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
				}
			}
    	
			return this.getSlot(slotId).getStack();
		}
		
		return null;
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) {
		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventoryWarp.isUseableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		inventoryWarp.closeInventory();
	}
	
	@Override
    public void putStackInSlot(int index, ItemStack stack) { }

	@Override
    @SideOnly(Side.CLIENT)
    public void putStacksInSlots(ItemStack[] stack) { }
    
	@Override
    public boolean canDragIntoSlot(Slot slot) { return false; }
    
	@Override
    public boolean func_94530_a(ItemStack stack, Slot slot) { return false; }
	
	public InventoryWarp getInventoryWarp() { return inventoryWarp; }

}
