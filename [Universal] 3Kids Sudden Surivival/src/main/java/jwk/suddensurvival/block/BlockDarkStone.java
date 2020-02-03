package jwk.suddensurvival.block;

import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.BlockStone;

public class BlockDarkStone extends BlockStone {

	public static final String NAME = "darkStone";
	public static final String TEXTURE_NAME = ModInfo.ID + ":dark_stone";

	public BlockDarkStone() {
		this.setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setHardness(1.5F)
		    .setResistance(10.0F)
		    .setStepSound(soundTypePiston)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
}
