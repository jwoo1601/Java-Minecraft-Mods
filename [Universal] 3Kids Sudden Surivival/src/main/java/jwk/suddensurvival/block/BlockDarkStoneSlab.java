package jwk.suddensurvival.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class BlockDarkStoneSlab extends BlockSlab {
	
	public static final String NAME = "darkStoneSlab";
	public static final String DOUBLE_NAME = "double_darkStoneSlab";
	public static final String TEXTURE_NAME = ModInfo.ID + ":dark_stone";

	public BlockDarkStoneSlab(boolean isDoubleSlab) {
		super(isDoubleSlab, Material.rock);
		
		this.setBlockName(NAME)
		    .setBlockTextureName(TEXTURE_NAME)
		    .setHardness(2.0F)
		    .setResistance(10.0F)
		    .setStepSound(soundTypePiston);
		
		if (!isDoubleSlab)
		    setCreativeTab(Tab3Kids.INSTANCE);
		
		super.useNeighborBrightness = true;
	}

	@Override
	public String func_150002_b(int metadata) {
		return this.getUnlocalizedName();
	}

	@Override
    protected ItemStack createStackedBlock(int metadata) {
        return new ItemStack(GameRegistry.findItem(ModInfo.ID, NAME), 2, metadata);
    }
	
	@Override
    public Item getItemDropped(int arg1, Random rand, int arg3) {
        return GameRegistry.findItem(ModInfo.ID, NAME);
    }
    
    @SideOnly(Side.CLIENT)
    private static boolean func_150003_a(Block block) {
        return block == Blocks.DARK_STONE_SLAB;
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return func_150003_a(this) ? GameRegistry.findItem(ModInfo.ID, NAME) : (this == Blocks.DOUBLE_DARK_STONE_SLAB ? GameRegistry.findItem(ModInfo.ID, NAME) : GameRegistry.findItem(ModInfo.ID, NAME));
    }
    
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
    	
    	if (world.getBlock(x, y-1, z) == Blocks.DARK_STONE_SLAB) {
    		world.setBlock(x, y-1, z, Blocks.DOUBLE_DARK_STONE_SLAB);
    		world.setBlockToAir(x, y, z);
    	}
    	
    	else if (world.getBlock(x, y+1, z) == Blocks.DARK_STONE_SLAB) {
    		world.setBlock(x, y+1, z, Blocks.DOUBLE_DARK_STONE_SLAB);
    		world.setBlockToAir(x, y, z);
    	}
    }
    
}
