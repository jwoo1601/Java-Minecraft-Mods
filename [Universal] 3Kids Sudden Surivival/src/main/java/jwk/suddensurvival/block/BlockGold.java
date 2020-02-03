package jwk.suddensurvival.block;

import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockGold extends Block {

	public static final String NAME = "gold";
	public static final String TEXTURE_NAME = ModInfo.ID + ":gold";
	
	private MapColor mapColor;
	
	public BlockGold() {
		super(Material.iron);
		
		this.setHardness(3.0F)
		    .setResistance(10.0F)
		    .setStepSound(soundTypeMetal)
		    .setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setCreativeTab(Tab3Kids.INSTANCE);
		
		this.mapColor = MapColor.goldColor;
	}
	
	@Override
	public MapColor getMapColor(int par) {
		return mapColor;
	}
	
}
