package jwk.suddensurvival.block;

import static net.minecraftforge.common.util.ForgeDirection.UP;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockFenceImpl extends BlockFence {

	public BlockFenceImpl(String blockName, String textureName, Material material) {
		super(textureName, material);
		
		this.setBlockName(blockName);
	}
	
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
    	return true;
    }
    
    public String getBlockName() { return this.getUnlocalizedName().replace("tile.", ""); }
	
}
