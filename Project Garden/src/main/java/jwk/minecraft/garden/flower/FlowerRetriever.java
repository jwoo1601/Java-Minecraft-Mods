package jwk.minecraft.garden.flower;

import static jwk.minecraft.garden.flower.Flowers.*;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
//import flowercraftmod.blocks.BlockFCFlower;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.item.ItemUniversalWand;
import jwk.minecraft.garden.network.PacketFlowerProperty;
import jwk.minecraft.garden.network.PacketFlowerProperty.Type;
import jwk.minecraft.garden.repository.FlowerPropsRepository;
import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class FlowerRetriever {

	public static final boolean isFlowerBlock(Block block) {
		return block instanceof BlockFlower || block instanceof BlockDoublePlant;// || block instanceof BlockFCFlower;
	}
	
	private static final boolean isUpperBlock(FlowerCache cache) {
		
		if (cache.UpperBlock == DOUBLE_PLANT && cache.LowerBlock == DOUBLE_PLANT && (cache.UpperBlockMetadata >= 8 && cache.UpperBlockMetadata <= 11))
			return true;
		
		return false;
	}
	
	public static final void processFlowerProperty(World world, EntityPlayer player, BlockPos pos, FlowerCache cache) {
		int dimensionId = world.provider.dimensionId;
		FlowerPropsRepository repository = ProjectGarden.repository[Flowers.getIndexFromId(dimensionId)];
		if (repository == null)
			return;
		
		if (!repository.getFlowerFactory().isPositionInside(pos))
			return;
		
		FlowerProperty prevProperty = null;
		boolean isUpperBlock = isUpperBlock(cache);
		
		if (isUpperBlock)
			prevProperty = repository.get(pos.add(0, -1, 0));
		else
			prevProperty = repository.get(pos);
		
		boolean isPrevExist = true;
		FlowerProperty currentProperty = null;
		// check whether previous property of the position is null or not
		if (prevProperty == null) {
			currentProperty = newFlowerProperty(isUpperBlock, pos, cache);
			repository.put(currentProperty);
			isPrevExist = false;
		}
		else
			currentProperty = prevProperty;
		
		ItemStack heldItem = player.getHeldItem();
		// check whether player's held item is UniversalWand-Direction or not
		if (heldItem != null && ItemUniversalWand.isDirectionWand(heldItem)) {
			currentProperty.setFacing(nextDirection(currentProperty.getFacing()));
			isPrevExist = false;
		}
		
		if (!isPrevExist)
			ProjectGarden.NET_HANDLER.sendToDimension(new PacketFlowerProperty(Type.SINGLE, currentProperty), dimensionId);
		
		ProjectGarden.NET_HANDLER.sendToDimension(new PacketFlowerProperty(Type.DISPLAY, currentProperty.getPosition()), dimensionId);			
	}
	
	private static final BlockType getTypeFromCache(FlowerCache cache) {
		
		if (cache.UpperBlock == FLOWER1 || cache.UpperBlock == FLOWER2 || cache.UpperBlock == FCFLOWER)
			return BlockType.SINGLE;
		
		return BlockType.DOUBLE;
	}
	
	private static final FlowerProperty newFlowerProperty(boolean isUpperBlock, BlockPos pos, FlowerCache cache) {
		BlockType type = getTypeFromCache(cache);
		
		switch (type) {
		
		case SINGLE:
			
			if (cache.UpperBlock == FLOWER1)
				return new FlowerProperty(pos, 1);
			
			else if (cache.UpperBlock == FLOWER2)
				return new FlowerProperty(pos, cache.UpperBlockMetadata + 2);
			
			else if (cache.UpperBlock == FCFLOWER)
				return new FlowerProperty(pos, cache.UpperBlockMetadata + 15);
			
			break;
			
		case DOUBLE:
			
			if (isUpperBlock) {
				
				switch (cache.LowerBlockMetadata) {
				
				case 0:
					return new FlowerProperty(pos.add(0, -1, 0), 11);
					
				case 1:
					return new FlowerProperty(pos.add(0, -1, 0), 12);
					
				case 4:
					return new FlowerProperty(pos.add(0, -1, 0), 13);
					
				case 5:
					return new FlowerProperty(pos.add(0, -1, 0), 14);
				}
			}
			
			else {
				
				switch (cache.UpperBlockMetadata) {
				
				case 0:
					return new FlowerProperty(pos, 11);
					
				case 1:
					return new FlowerProperty(pos, 12);
					
				case 4:
					return new FlowerProperty(pos, 13);
					
				case 5:
					return new FlowerProperty(pos, 14);
				}
			}
			
			break;		
		}
		
		return null;
	}	
	
	// N - E - S - W
	private static EnumFacing nextDirection(EnumFacing facing) {
		
		switch (facing) {
		
		case NORTH:
			return EnumFacing.EAST;
			
		case EAST:
			return EnumFacing.SOUTH;
			
		case SOUTH:
			return EnumFacing.WEST;
			
		case WEST:
			return EnumFacing.NORTH;
			
		default:
			break;
			
		}
		
		return null;
	}
	
	static enum BlockType {
		
		SINGLE,
		DOUBLE;
		
	}
	
	public static class FlowerCache {
		
		public final Block UpperBlock;
		public final int UpperBlockMetadata;
		
		public final Block LowerBlock;
		public final int LowerBlockMetadata;
		
		public FlowerCache(Block upper, int upperMetadata, Block lower, int lowerMetadata) {
			UpperBlock = upper;
			UpperBlockMetadata = upperMetadata;
			LowerBlock = lower;
			LowerBlockMetadata = lowerMetadata;
		}
		
	}
	
}
