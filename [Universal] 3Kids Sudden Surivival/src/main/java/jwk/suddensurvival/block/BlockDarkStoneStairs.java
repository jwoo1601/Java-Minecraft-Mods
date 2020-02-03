package jwk.suddensurvival.block;

import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockDarkStoneStairs extends BlockStairs {
	
	private static Block modelBlock = Blocks.DARK_STONE;
	private static int index = 0;
	
	public static final String NAME = "stairsDarkStone";

	public  BlockDarkStoneStairs() {
		super(modelBlock, index);
		this.setBlockName(NAME)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
    
}
