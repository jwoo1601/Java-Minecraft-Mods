package jwk.minecraft.garden.team.container;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTeamChest extends Container {
	
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INV_ROW_COUNT = 3;
	private static final int PLAYER_INV_COLUMN_COUNT = 9;
	
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INV_ROW_COUNT * PLAYER_INV_COLUMN_COUNT;

	private static final int ROW_COUNT = 6;
	private static final int COLUMN_COUNT = 9;
	private static final int TOTAL_SLOT_COUNT = ROW_COUNT * COLUMN_COUNT;
	private static final int FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;	
	
	private InventoryTeamChest inventoryTeam;
	
	public ContainerTeamChest(@Nonnull InventoryPlayer invPlayer, @Nonnull InventoryTeamChest invTeam) {
		checkNotNull(invPlayer); checkNotNull(invTeam);
		
		inventoryTeam = invTeam;
		
		final int XOffset = 18, YOffset = 18;
		final int TeamInvX = 8, TeamInvY = 18; //20		
		for (int y=0; y < ROW_COUNT; ++y) {
			
			for (int x=0; x < COLUMN_COUNT; ++x) {
				int num = y * COLUMN_COUNT + x;
				int xpos = TeamInvX + x * XOffset;
				int ypos = TeamInvY + y * YOffset;
				
				addSlotToContainer(new Slot(invTeam, num, xpos, ypos));
			}
		}
		
		final int PlayerInvX = 8, PlayerInvY = 157; //51
		for (int y1=0; y1 < PLAYER_INV_ROW_COUNT; ++y1) {
			
			for (int x1=0; x1 < PLAYER_INV_COLUMN_COUNT; ++x1) {
				int num = HOTBAR_SLOT_COUNT + y1 * PLAYER_INV_COLUMN_COUNT + x1;
				int xpos = PlayerInvX + x1 * XOffset;
				int ypos = PlayerInvY + y1 * YOffset - 18;
				
				addSlotToContainer(new Slot(invPlayer, num, xpos, ypos));
			}
		}
		
		final int HotbarX = 8, HotbarY = 215; //108		
		for (int x2=0; x2 < HOTBAR_SLOT_COUNT; ++x2)
			addSlotToContainer(new Slot(invPlayer, x2, HotbarX + XOffset * x2, HotbarY - 18));
		

//		if (TOTAL_SLOT_COUNT != invTeam.getSizeInventory())
//			throw new IllegalArgumentException();
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventoryTeam.isUseableByPlayer(player);
	}

	@Override 
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex) { 
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex); 
		
		if (sourceSlot == null || !sourceSlot.getHasStack())
			return null;
		
		ItemStack sourceStack = sourceSlot.getStack(); 
		ItemStack copyOfSourceStack = sourceStack.copy(); 
	 
		// Check if the slot clicked is one of the vanilla container slots 
		if (sourceSlotIndex >= VANILLA_FIRST_SLOT_INDEX && sourceSlotIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			// This is a vanilla container slot so merge the stack into the tile inventory
			if (!mergeItemStack(sourceStack, FIRST_SLOT_INDEX, FIRST_SLOT_INDEX + TOTAL_SLOT_COUNT, false))
	 				return null; 
		}
		
		else if (sourceSlotIndex >= FIRST_SLOT_INDEX && sourceSlotIndex < FIRST_SLOT_INDEX + TOTAL_SLOT_COUNT) { 
	 			// This is a TE slot so merge the stack into the players inventory
			if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false))
				return null;
		}
		
		else { 
	 			System.err.print("Invalid slotIndex:" + sourceSlotIndex); 
	 			return null; 
	 	} 
	 
	 
		// If stack size == 0 (the entire stack was moved) set slot contents to null 
		if (sourceStack.stackSize == 0)
			sourceSlot.putStack(null); 
		
		else
			sourceSlot.onSlotChanged(); 
	 
	 
		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack; 
	} 
	 
	@Override
	public void onContainerClosed(EntityPlayer player) { 
		super.onContainerClosed(player);
		this.inventoryTeam.closeInventory();
	} 
	
	public InventoryTeamChest getInventoryTeam() { return inventoryTeam; }
	
}
