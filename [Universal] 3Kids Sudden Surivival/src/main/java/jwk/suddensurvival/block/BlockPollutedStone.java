package jwk.suddensurvival.block;

import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.BlockStone;

public class BlockPollutedStone extends BlockStone {
	
	public static final String NAME = "pollutedStone";
	public static final String TEXTURE_NAME = ModInfo.ID + ":polluted_stone";

	public BlockPollutedStone() {
		this.setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setHardness(1.5F)
		    .setResistance(10.0F)
		    .setStepSound(soundTypePiston)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
}
