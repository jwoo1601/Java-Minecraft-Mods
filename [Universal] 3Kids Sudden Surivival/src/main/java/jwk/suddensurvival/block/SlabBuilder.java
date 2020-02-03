package jwk.suddensurvival.block;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Random;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SlabBuilder {
	
	public static SlabWrapper newSlab(Material material) {
		return new SlabWrapper(new BlockSlabImpl(material), new BlockSlabImpl(material, true));
	}
	
	public static SlabWrapper newSlab(String name, Material material) {
		return new SlabWrapper(new BlockSlabImpl(material), new BlockSlabImpl(material, true)).setName(name);		
	}
	
	public static class SlabWrapper {
		
		private BlockSlabImpl singleSlab;
		private BlockSlabImpl doubleSlab;
		
		public SlabWrapper(@Nonnull BlockSlabImpl singleB, @Nonnull BlockSlabImpl doubleB) {
			singleSlab = checkNotNull(singleB);
			doubleSlab = checkNotNull(doubleB);
		}
		
		public BlockSlabImpl getSingleSlab() { return singleSlab; }
		
		public BlockSlabImpl getDoubleSlab() { return doubleSlab; }
		
		public SlabWrapper registerBlocks() {			
			GameRegistry.registerBlock(singleSlab, singleSlab.getUnformattedName());
			GameRegistry.registerBlock(doubleSlab, "double_" + singleSlab.getUnformattedName());
			
			return this;
		}
		
		public SlabWrapper registerBlocks(@Nonnull String name) {
			checkNotNull(name);
			
			GameRegistry.registerBlock(singleSlab, name);
			GameRegistry.registerBlock(doubleSlab, "double_" + name);
			
			return this;
		}
		
		public SlabWrapper setName(@Nonnull String name) {
			checkNotNull(name);
			
			singleSlab.setBlockName(name);
			doubleSlab.setBlockName(name);
			
			return this;
		}
		
		public SlabWrapper setTextureName(@Nonnull String textureName) {
			checkNotNull(textureName);
			
			singleSlab.setBlockTextureName(textureName);
			doubleSlab.setBlockTextureName(textureName);
			
			return this;
		}
		
		public SlabWrapper setCreativeTab(@Nonnull CreativeTabs tab) {
			checkNotNull(tab);
			
			singleSlab.setCreativeTab(tab);
			
			return this;
		}
		
		public SlabWrapper setHardness(float val) {
			singleSlab.setHardness(val);
			doubleSlab.setHardness(val);
			
			return this;
		}
		
		public SlabWrapper setResistance(float val) {
			singleSlab.setResistance(val);
			doubleSlab.setResistance(val);
			
			return this;			
		}
		
		public SlabWrapper setStepSound(@Nonnull SoundType type) {
			singleSlab.setStepSound(type);
			doubleSlab.setStepSound(type);
			
			return this;
		}
		
		public SlabWrapper setUseNeighborBrightness() {
			singleSlab.setUseNeighborBrightness();
			doubleSlab.setUseNeighborBrightness();
			
			return this;
		}
		
		public SlabWrapper setUnuseNeighborBrightness() {
			singleSlab.setUnuseNeighborBrightness();
			doubleSlab.setUnuseNeighborBrightness();
			
			return this;
		}
		
		public SlabWrapper setLightLevel(float val) {
			singleSlab.setLightLevel(val);
			doubleSlab.setLightLevel(val);
			
			return this;
		}
		
		public SlabWrapper setLightOpacity(int val) {
			singleSlab.setLightLevel(val);
			doubleSlab.setLightLevel(val);
			
			return this;
		}
		
	}

	public static class BlockSlabImpl extends BlockSlab {
		
		public BlockSlabImpl(Material material) {
			this(material, false);
		}
		
		public BlockSlabImpl(Material material, boolean isDoubleSlab) {
			super(isDoubleSlab, material);
		}
		
		public BlockSlabImpl setUseNeighborBrightness() { super.useNeighborBrightness = true; return this; }
		
		public BlockSlabImpl setUnuseNeighborBrightness() { super.useNeighborBrightness = false; return this; }
		
		public String getUnformattedName() { return this.getUnlocalizedName().replace("tile.", ""); }
		
		public boolean isDoubleSlab() { return super.field_150004_a; }

		@Override
		public String func_150002_b(int metadata) {
			return this.getUnlocalizedName();
		}

		@Override
	    protected ItemStack createStackedBlock(int metadata) {
	        return new ItemStack(GameRegistry.findItem(ModInfo.ID, this.getUnformattedName()), 2, metadata);
	    }
		
		@Override
	    public Item getItemDropped(int arg1, Random rand, int arg3) {
	        return GameRegistry.findItem(ModInfo.ID, this.getUnformattedName());
	    }
	    
	    @Override
	    @SideOnly(Side.CLIENT)
	    public Item getItem(World world, int x, int y, int z) {
	        return GameRegistry.findItem(ModInfo.ID, this.getUnformattedName());
	    }
	    
	    @Override
	    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {

	    	if (world.getBlock(x, y-1, z) == GameRegistry.findBlock(ModInfo.ID, this.getUnformattedName())) {
	    		world.setBlock(x, y-1, z, GameRegistry.findBlock(ModInfo.ID, "double_" + this.getUnformattedName()));
	    		world.setBlockToAir(x, y, z);
	    	}
	    	
	    	else if (world.getBlock(x, y+1, z) == GameRegistry.findBlock(ModInfo.ID, this.getUnformattedName())) {
	    		world.setBlock(x, y+1, z, GameRegistry.findBlock(ModInfo.ID, "double_" + this.getUnformattedName()));
	    		world.setBlockToAir(x, y, z);
	    	}
	    }
	    
	}
	
}
