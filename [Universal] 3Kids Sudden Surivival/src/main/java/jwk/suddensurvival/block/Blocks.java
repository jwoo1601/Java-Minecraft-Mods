package jwk.suddensurvival.block;

import cpw.mods.fml.common.registry.GameRegistry;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.block.SlabBuilder.SlabWrapper;
import jwk.suddensurvival.item.ItemBlockWool;
import jwk.suddensurvival.tab.Tab3Kids;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCloth;

public class Blocks {
	
	public static final BlockPollutedStone POLLUTED_STONE = new BlockPollutedStone();
	public static final BlockPollutedStoneStairs POLLUTED_STONE_STAIRS = new BlockPollutedStoneStairs();
	
	public static final BlockDarkStone DARK_STONE = new BlockDarkStone();
	public static final BlockDarkStoneStairs DARK_STONE_STAIRS = new BlockDarkStoneStairs();
	public static final BlockDarkStoneSlab DOUBLE_DARK_STONE_SLAB = new BlockDarkStoneSlab(true);
	public static final BlockDarkStoneSlab DARK_STONE_SLAB = new BlockDarkStoneSlab(false);
	
	public static final BlockTransparent TRANSPARENT = new BlockTransparent();
	
	public static final BlockMetal METAL = new BlockMetal();
	public static final BlockPillarImpl METAL_PILLAR = new BlockPillarImpl(Material.iron, "metalPillar", ModInfo.ID + ":metal_pillar_top", ModInfo.ID + ":metal_pillar_side");
	public static final BlockSidedSlab.SidedSlabWrapper METAL_SLAB = new BlockSidedSlab.SidedSlabWrapper(new BlockSidedSlab(Material.iron), new BlockSidedSlab(Material.iron, true));
	public static final BlockStairsImpl METAL_STAIRS = new BlockStairsImpl("metalStairs", METAL, 0);
	public static final SlabWrapper METAL_SLAB_PLAIN = SlabBuilder.newSlab("metalSlabPlain", Material.iron);
	
	public static final SlabWrapper CORRODED_STONE_SLAB = SlabBuilder.newSlab("corrodedStoneSlab", Material.rock);
	public static final SlabWrapper CORRODED_DARK_STONE_SLAB = SlabBuilder.newSlab("corrodedDarkStoneSlab", Material.rock);
	
	public static final BlockPollutedSand POLLUTED_SAND = new BlockPollutedSand();
	
	public static final SlabWrapper SPONGE_SLAB = SlabBuilder.newSlab("spongeSlab", Material.sponge);
	public static final BlockFenceImpl SPONGE_FENCE = new BlockFenceImpl("spongeFence", ModInfo.ID + ":sponge", Material.sponge);
	
	public static final BlockGold GOLD = new BlockGold();
	
	public static final BlockWool WOOL = new BlockWool();
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(POLLUTED_STONE, POLLUTED_STONE.NAME);
		GameRegistry.registerBlock(POLLUTED_STONE_STAIRS, POLLUTED_STONE_STAIRS.NAME);
		
		GameRegistry.registerBlock(DARK_STONE, DARK_STONE.NAME);
		GameRegistry.registerBlock(DARK_STONE_STAIRS, DARK_STONE_STAIRS.NAME);
		GameRegistry.registerBlock(DARK_STONE_SLAB, DARK_STONE_SLAB.NAME);
		GameRegistry.registerBlock(DOUBLE_DARK_STONE_SLAB, DARK_STONE_SLAB.DOUBLE_NAME);
		
		GameRegistry.registerBlock(TRANSPARENT, TRANSPARENT.NAME);
		
		GameRegistry.registerBlock(METAL, METAL.NAME);
		
		CORRODED_STONE_SLAB.setTextureName(ModInfo.ID + ":corroded_stone").setHardness(2.F).setResistance(10.F).setStepSound(Block.soundTypePiston).setUseNeighborBrightness().setCreativeTab(Tab3Kids.INSTANCE).registerBlocks();
		CORRODED_DARK_STONE_SLAB.setTextureName(ModInfo.ID + ":corroded_dark_stone").setHardness(2.F).setResistance(10.F).setStepSound(Block.soundTypePiston).setUseNeighborBrightness().setCreativeTab(Tab3Kids.INSTANCE).registerBlocks();
		
		GameRegistry.registerBlock(POLLUTED_SAND, POLLUTED_SAND.NAME);
		
		METAL_PILLAR.setHardness(5F).setResistance(10F).setStepSound(Block.soundTypeMetal).setCreativeTab(Tab3Kids.INSTANCE);
		GameRegistry.registerBlock(METAL_PILLAR, METAL_PILLAR.getBlockName());
		
		SPONGE_SLAB.setTextureName(ModInfo.ID + ":sponge").setHardness(0.6F).setStepSound(Block.soundTypeGrass).setUseNeighborBrightness().setCreativeTab(Tab3Kids.INSTANCE).registerBlocks();
		SPONGE_FENCE.setHardness(0.6F).setStepSound(Block.soundTypeGrass).setCreativeTab(Tab3Kids.INSTANCE);
		GameRegistry.registerBlock(SPONGE_FENCE, SPONGE_FENCE.getBlockName());
		
		METAL_SLAB.setName("metalSlab").setTopTextureName(ModInfo.ID + ":metal_slab_top").setBottomTextureName(ModInfo.ID + ":metal_slab_bottom").setSideTextureName(ModInfo.ID + ":metal_slab_side")
		          .setHardness(5F).setResistance(10F).setStepSound(Block.soundTypeMetal).setCreativeTab(Tab3Kids.INSTANCE).registerBlocks();
		
		GameRegistry.registerBlock(GOLD, BlockGold.NAME);
		
		GameRegistry.registerBlock(METAL_STAIRS, METAL_STAIRS.getBlockName());
		METAL_SLAB_PLAIN.setTextureName(BlockMetal.TEXTURE_NAME).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setCreativeTab(Tab3Kids.INSTANCE).registerBlocks();
		
		GameRegistry.registerBlock(WOOL, ItemBlockWool.class, "wool");
	}
	
}
