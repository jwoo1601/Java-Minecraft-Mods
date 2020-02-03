package jwk.suddensurvival.block;

import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

public class BlockPollutedSand extends BlockFalling {
	
	public static final String NAME = "pollutedSand";
	public static final String TEXTURE_NAME = ModInfo.ID + ":polluted_sand";
	
	public BlockPollutedSand() {
		super(Material.sand);
		
		this.setHardness(0.5F)
		    .setStepSound(soundTypeSand)
		    .setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}

	@Override
    public int damageDropped(int damage) {
        return damage;
    }
	
}
