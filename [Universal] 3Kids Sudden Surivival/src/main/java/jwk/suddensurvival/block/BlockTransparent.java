package jwk.suddensurvival.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTransparent extends Block {

	public static final String NAME = "transparent";
	public static final String TEXTURE_NAME = ModInfo.ID + ":transparent";
	
	public BlockTransparent() {
		super(Material.rock);
		
		this.setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setBlockUnbreakable()
		    .setResistance(6000000.0F)
		    .setStepSound(soundTypePiston)
		    .setCreativeTab(Tab3Kids.INSTANCE)
		    .setLightLevel(0.0F)
		    .setLightOpacity(-1);
		
		super.disableStats();
		super.useNeighborBrightness = true;
	}
	
	@Override
    public boolean isOpaqueCube() { return false; }
    
    @SideOnly(Side.CLIENT)
    @Override
    public float getAmbientOcclusionLightValue() {
        return 1.F;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side) { return false; }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return world.getBlock(x, y, z) == this;
    }

}
