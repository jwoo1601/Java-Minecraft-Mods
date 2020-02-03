package jwk.minecraft.garden.warp;

import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class WarpData {
	
	public static final WarpData none() {
		return new WarpData("", null, new ItemStack(Blocks.stained_glass_pane, 1, 0));
	}

	private String displayName;
	private BlockPos position;
	private ItemStack displayStack;
	
	public WarpData(String displayName, BlockPos position, ItemStack stack) {
		this.displayName = displayName;
		this.position = position;
		this.displayStack = stack.copy().setStackDisplayName(displayName);
	}
	
	public String getDisplayName() { return displayName; }
	
	public void setDisplayName(String name) {
		displayName = name;
		displayStack.setStackDisplayName(name);
	}
	
	public BlockPos getPosition() { return position; }
	
	public void setPosition(BlockPos pos) {
		this.position = pos;
	}	
	
	public ItemStack getDisplayStack() { return displayStack; }
	
	public void setDisplayStack(ItemStack stack) {
		displayStack = stack.copy().setStackDisplayName(displayName);
	}
	
}
