package jwk.minecraft.garden.team;

import jwk.minecraft.garden.exception.NBTLoadException;
import jwk.minecraft.garden.exception.NBTSaveException;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.INBTSerializableSafe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class TeamChest implements INBTSerializableSafe {
	
	private int dimensionId = -2;
	
	private BlockPos posLowerChest = null;
	private BlockPos posUpperChest = null;
	
	public TeamChest() { }
	
	public TeamChest(World world, BlockPos posLower) {
		set(world, posLower);
	}
	
	public TeamChest(int dId, BlockPos posLower, BlockPos posUpper) {
		dimensionId = dId;
		posLowerChest = posLower;
		posUpperChest = posUpper;
	}
	
	public void reset() {
		dimensionId = -2;
		posLowerChest = null;
		posUpperChest = null;
	}
	
	public boolean checkSelf() {
		World world = getWorld();
		
		if (world == null)
			return false;
		
		TileEntity lower = world.getTileEntity(posLowerChest.getX(), posLowerChest.getY(), posLowerChest.getZ());
		if (lower == null || !(lower instanceof TileEntityChest))
			return false;
		
		TileEntity upper = world.getTileEntity(posUpperChest.getX(), posUpperChest.getY(), posUpperChest.getZ());
		if (upper == null || !(upper instanceof TileEntityChest))
			return false;
		
		return true;
	}
	
	public World getWorld() { return MinecraftServer.getServer().worldServerForDimension(dimensionId); }
	
	public int getDimemsionId() { return dimensionId; }
	
	public BlockPos getPosLowerChest() { return posLowerChest; }
	
	public BlockPos getPosUpperChest() { return posUpperChest; }
	
	public boolean set(World world, BlockPos pos) {
		EnumDirection direction = getDirection(world, pos);
		
		if (direction != EnumDirection.NONE) {
			dimensionId = world.provider.dimensionId;
			posLowerChest = pos;
			posUpperChest = pos.add(direction.Position);
			
			return true;
		}
		
		return false;
	}
	
	public TileEntityChest getLowerChest() {
		World world = getWorld();
		
		if (world == null)
			return null;
		
		TileEntity te = world.getTileEntity(posLowerChest.getX(), posLowerChest.getY(), posLowerChest.getZ());
		if (te == null || !(te instanceof TileEntityChest))
			return null;
		
		return (TileEntityChest) te;
	}
	
	public TileEntityChest getUpperChest() {
		World world = getWorld();
		
		if (world == null)
			return null;
		
		TileEntity te = world.getTileEntity(posUpperChest.getX(), posUpperChest.getY(), posUpperChest.getZ());
		if (te == null || !(te instanceof TileEntityChest))
			return null;
		
		return (TileEntityChest) te;
	}
	
	public boolean setInventorySlotContents(int index, ItemStack stack) {
		
		if (index >= 0 && index < 27) {
			TileEntityChest te = getLowerChest();
			
			if (te == null)
				return false;
			
			te.setInventorySlotContents(index, stack);
			return true;
		}
		
		else if (index >= 27 && index < 54) {
			TileEntityChest te = getUpperChest();
			
			if (te == null)
				return false;
			
			te.setInventorySlotContents(index - 27, stack);
			return true;
		}
		
		return false;
	}
	
	public ItemStack getStackInSlot(int index) {
		
		if (index >= 0 && index < 27) {
			TileEntityChest te = getLowerChest();
			
			if (te == null)
				return null;
			
			return te.getStackInSlot(index);
		}
		
		else if (index >= 27 && index < 54) {
			TileEntityChest te = getUpperChest();
			
			if (te == null)
				return null;
			
			return te.getStackInSlot(index - 27);
		}
		
		return null;
	}
	
	public int getSizeInventory() {
		return 54;
	}
	
	public static boolean validate(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		
		if (te == null || !(te instanceof TileEntityChest))
			return false;
		
		TileEntity te_north = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ() - 1);
		TileEntity te_south = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ() + 1);
		TileEntity te_east = world.getTileEntity(pos.getX() + 1, pos.getY(), pos.getZ());
		TileEntity te_west = world.getTileEntity(pos.getX() - 1, pos.getY(), pos.getZ());
		
		return (te_north != null && te_north instanceof TileEntityChest) ||
			   (te_south != null && te_south instanceof TileEntityChest) ||
			   (te_east != null && te_east instanceof TileEntityChest)   ||
			   (te_west != null && te_west instanceof TileEntityChest);
	}
	
	public static EnumDirection getDirection(World world, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
		
		if (te == null || !(te instanceof TileEntityChest))
			return EnumDirection.NONE;
		
		TileEntity te_north = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ() - 1);
		if (te_north != null && te_north instanceof TileEntityChest)
			return EnumDirection.NORTH;
		
		TileEntity te_south = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ() + 1);
		if (te_south != null && te_south instanceof TileEntityChest)
			return EnumDirection.SOUTH;
		
		TileEntity te_east = world.getTileEntity(pos.getX() + 1, pos.getY(), pos.getZ());
		if (te_east != null && te_east instanceof TileEntityChest)
			return EnumDirection.EAST;
		
		TileEntity te_west = world.getTileEntity(pos.getX() - 1, pos.getY(), pos.getZ());
		if (te_west != null && te_west instanceof TileEntityChest)
			return EnumDirection.WEST;
		
		return EnumDirection.NONE;
	}
	
	public static TeamChest newTeamChest(World world, BlockPos pos) {
		TeamChest target = null;
		EnumDirection direction = getDirection(world, pos);
		
		if (direction != EnumDirection.NONE) {
			target = new TeamChest();
			target.dimensionId = world.provider.dimensionId;
			target.posLowerChest = pos;
			target.posUpperChest = pos.add(direction.Position);
		}
		
		return target;
	}
	
	private static final String KEY_DIMENSION_ID = "Dimension ID";
	private static final String KEY_LOWER_CHEST = "Lower Chest";
	private static final String KEY_UPPER_CHEST = "Upper Chest";
	
	@Override
	public void write(NBTTagCompound tagCompound) throws NBTSaveException {
		tagCompound.setInteger(KEY_DIMENSION_ID, dimensionId);
		
		if (posLowerChest == null)
			throw new NBTSaveException("failed to save team chest data", new NullPointerException("posLowerChest"));
		else if (posUpperChest == null)
			throw new NBTSaveException("failed to save team chest data", new NullPointerException("posUpperChest"));
		
		NBTTagCompound tagLower = new NBTTagCompound();
		posLowerChest.write(tagLower);
		tagCompound.setTag(KEY_LOWER_CHEST, tagLower);
		
		NBTTagCompound tagUpper = new NBTTagCompound();
		posUpperChest.write(tagUpper);
		tagCompound.setTag(KEY_UPPER_CHEST, tagUpper);
	}

	@Override
	public void read(NBTTagCompound tagCompound) throws NBTLoadException {
		int dId = -2;
		
		if (tagCompound.hasKey(KEY_DIMENSION_ID, NBT.TAG_INT))
			dId = tagCompound.getInteger(KEY_DIMENSION_ID);
		else
			throw new NBTLoadException("failed to load team chest data : could not find key=" + KEY_DIMENSION_ID);
		
		BlockPos lower = BlockPos.zero();
		if (tagCompound.hasKey(KEY_LOWER_CHEST, NBT.TAG_COMPOUND))
			lower.read(tagCompound.getCompoundTag(KEY_LOWER_CHEST));
		else
			throw new NBTLoadException("failed to load team chest data : could not find key=" + KEY_LOWER_CHEST);
		
		BlockPos upper = BlockPos.zero();
		if (tagCompound.hasKey(KEY_UPPER_CHEST, NBT.TAG_COMPOUND))
			upper.read(tagCompound.getCompoundTag(KEY_UPPER_CHEST));
		else
			throw new NBTLoadException("failed to load team chest data : could not find key=" + KEY_UPPER_CHEST);
		
		dimensionId = dId;
		posLowerChest = lower;
		posUpperChest = upper;
	}

	public static enum EnumDirection {
		
		NONE(0, 0, 0),
		NORTH(0, 0, -1),
		SOUTH(0, 0, 1),
		EAST(1, 0, 0),
		WEST(-1, 0, 0);
		
		public final BlockPos Position;
		
		private EnumDirection(int x, int y, int z) {
			Position = new BlockPos(x, y, z);
		}
		
	}
	
}
