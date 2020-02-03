package jwk.suddensurvival.block;

import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMetal extends Block {

	public static final String NAME = "metal";
	public static final String TEXTURE_NAME = ModInfo.ID + ":metal";
	
	public BlockMetal() {
		super(Material.iron);
		
		this.setHardness(5.0F)
		    .setResistance(10.0F)
		    .setStepSound(soundTypeMetal)
		    .setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
}
