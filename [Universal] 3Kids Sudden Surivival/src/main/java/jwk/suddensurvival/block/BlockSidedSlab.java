package jwk.suddensurvival.block;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.block.SlabBuilder.BlockSlabImpl;
import jwk.suddensurvival.block.SlabBuilder.SlabWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockSidedSlab extends BlockSlabImpl {

	protected String topTextureName = "MISSING_ICON_BLOCK";
	protected String bottomTextureName = "MISSING_ICON_BLOCK";
	protected String sideTextureName = "MISSING_ICON_BLOCK";
	
	@SideOnly(Side.CLIENT)
	protected IIcon topIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon bottomIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;
	
	public BlockSidedSlab(Material material) {
		super(material);
	}
	
	public BlockSidedSlab(Material material, boolean isDoubleSlab) {
		super(material, isDoubleSlab);
	}
	
	@Override
	@Deprecated
    public Block setBlockTextureName(String textureName) { return this; }
	
	public Block setTopTextureName(String name) { this.topTextureName = name; return this; }
	
	public Block setBottomTextureName(String name) { this.bottomTextureName = name; return this; }
	
	public Block setSideTextureName(String name) { this.sideTextureName = name; return this; }
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
		
		if (side == 0)
			return bottomIcon;
		else if (side == 1)
			return topIcon;
		else
			return sideIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        topIcon = register.registerIcon(topTextureName);
        bottomIcon = register.registerIcon(bottomTextureName);
        sideIcon = register.registerIcon(sideTextureName);
    }
    
    
    public static class SidedSlabWrapper {

    	private BlockSidedSlab singleSlab;
		private BlockSidedSlab doubleSlab;
		
		public SidedSlabWrapper(@Nonnull BlockSidedSlab singleB, @Nonnull BlockSidedSlab doubleB) {
			singleSlab = checkNotNull(singleB);
			doubleSlab = checkNotNull(doubleB);
		}
		
		public BlockSlabImpl getSingleSlab() { return singleSlab; }
		
		public BlockSlabImpl getDoubleSlab() { return doubleSlab; }
		
		public SidedSlabWrapper registerBlocks() {			
			GameRegistry.registerBlock(singleSlab, singleSlab.getUnformattedName());
			GameRegistry.registerBlock(doubleSlab, "double_" + singleSlab.getUnformattedName());
			
			return this;
		}
		
		public SidedSlabWrapper registerBlocks(@Nonnull String name) {
			checkNotNull(name);
			
			GameRegistry.registerBlock(singleSlab, name);
			GameRegistry.registerBlock(doubleSlab, "double_" + name);
			
			return this;
		}
		
		public SidedSlabWrapper setName(@Nonnull String name) {
			checkNotNull(name);
			
			singleSlab.setBlockName(name);
			doubleSlab.setBlockName(name);
			
			return this;
		}
		
		public SidedSlabWrapper setTopTextureName(@Nonnull String name) {
			checkNotNull(name);
			
			singleSlab.setTopTextureName(name);
			doubleSlab.setTopTextureName(name);
			
			return this;
		}
		
		public SidedSlabWrapper setBottomTextureName(@Nonnull String name) {
			checkNotNull(name);
			
			singleSlab.setBottomTextureName(name);
			doubleSlab.setBottomTextureName(name);
			
			return this;
		}
		
		public SidedSlabWrapper setSideTextureName(@Nonnull String name) {
			checkNotNull(name);
			
			singleSlab.setSideTextureName(name);
			doubleSlab.setSideTextureName(name);
			
			return this;
		}
		
		public SidedSlabWrapper setCreativeTab(@Nonnull CreativeTabs tab) {
			checkNotNull(tab);
			
			singleSlab.setCreativeTab(tab);
			
			return this;
		}
		
		public SidedSlabWrapper setHardness(float val) {
			singleSlab.setHardness(val);
			doubleSlab.setHardness(val);
			
			return this;
		}
		
		public SidedSlabWrapper setResistance(float val) {
			singleSlab.setResistance(val);
			doubleSlab.setResistance(val);
			
			return this;			
		}
		
		public SidedSlabWrapper setStepSound(@Nonnull SoundType type) {
			singleSlab.setStepSound(type);
			doubleSlab.setStepSound(type);
			
			return this;
		}
		
		public SidedSlabWrapper setUseNeighborBrightness() {
			singleSlab.setUseNeighborBrightness();
			doubleSlab.setUseNeighborBrightness();
			
			return this;
		}
		
		public SidedSlabWrapper setUnuseNeighborBrightness() {
			singleSlab.setUnuseNeighborBrightness();
			doubleSlab.setUnuseNeighborBrightness();
			
			return this;
		}
		
		public SidedSlabWrapper setLightLevel(float val) {
			singleSlab.setLightLevel(val);
			doubleSlab.setLightLevel(val);
			
			return this;
		}
		
		public SidedSlabWrapper setLightOpacity(int val) {
			singleSlab.setLightLevel(val);
			doubleSlab.setLightLevel(val);
			
			return this;
		}
		
    }

}
