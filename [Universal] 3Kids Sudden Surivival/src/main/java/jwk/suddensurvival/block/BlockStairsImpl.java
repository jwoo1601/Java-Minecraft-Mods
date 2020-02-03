package jwk.suddensurvival.block;

import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockStairsImpl extends BlockStairs {

	public BlockStairsImpl(String blockName, Block block, int iconIndex) {
		super(block, iconIndex);
		
		this.setBlockName(blockName)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
	public String getBlockName() { return this.getUnlocalizedName().replace("tile.", ""); }
	
}
