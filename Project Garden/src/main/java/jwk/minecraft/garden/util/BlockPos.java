package jwk.minecraft.garden.util;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockPos implements INBTSerializable {
	
	public static final BlockPos zero() {
		return new BlockPos(0, 0, 0);
	}
	
	protected int xCoord = 0;
	protected int yCoord = 0;
	protected int zCoord = 0;
	
	public BlockPos(@Nonnull Vector3f vec) {
		this((int) vec.x, (int) vec.y, (int) vec.z);
	}
	
	public BlockPos(int x, int y, int z) {				
		if (x == -0)
			x = 0;
		if (y == -0)
			y = 0;
		if (z == -0)
			z = 0;

		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}
	
	public int getX() { return xCoord; }
	
	public int getY() { return yCoord; }
	
	public int getZ() { return zCoord; }
	
	public BlockPos add(BlockPos pos) {
		return new BlockPos(xCoord + pos.xCoord, yCoord + pos.yCoord, zCoord + pos.zCoord);
	}
	
	public BlockPos add(int x, int y, int z) {
		return new BlockPos(xCoord + x, yCoord + y, zCoord + z);
	}
	
	@Override
	public BlockPos clone() {
		return new BlockPos(xCoord, yCoord, zCoord);
	}
	
	@Override
	public int hashCode() {
		int hXY = xCoord ^ yCoord;
		int hYZ = yCoord ^ zCoord;
		int hZX = zCoord ^ xCoord;
		
		return (((hXY >> 10) ^ hYZ) >> 10 ^ hZX);
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object != null && object instanceof BlockPos) {
			BlockPos vec = (BlockPos) object;
			
			return vec.xCoord == this.xCoord && vec.yCoord == this.yCoord && vec.zCoord == this.zCoord;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + xCoord + "," + yCoord + "," + zCoord + ")";
	}
	
	
	private static final String KEY_BLOCK_POS = "BlockPos";
	private static final String KEY_X_COORD = "X";
	private static final String KEY_Y_COORD = "Y";
	private static final String KEY_Z_COORD = "Z";

	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagBlockPos = new NBTTagCompound();
		
		tagBlockPos.setInteger(KEY_X_COORD, xCoord);
		tagBlockPos.setInteger(KEY_Y_COORD, yCoord);
		tagBlockPos.setInteger(KEY_Z_COORD, zCoord);
		
		tagCompound.setTag(KEY_BLOCK_POS, tagBlockPos);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagBlockPos = null;
		
		if (tagCompound.hasKey(KEY_BLOCK_POS, NBT.TAG_COMPOUND))
			tagBlockPos = tagCompound.getCompoundTag(KEY_BLOCK_POS);
		
		else
			throw new IllegalArgumentException();
		
		int x = 0;		
		if (tagBlockPos.hasKey(KEY_X_COORD, NBT.TAG_INT))
			x = tagBlockPos.getInteger(KEY_X_COORD);
		
		else
			throw new IllegalArgumentException();
		
		int y = 0;		
		if (tagBlockPos.hasKey(KEY_Y_COORD, NBT.TAG_INT))
			y = tagBlockPos.getInteger(KEY_Y_COORD);
		
		else
			throw new IllegalArgumentException();
		
		int z = 0;		
		if (tagBlockPos.hasKey(KEY_Z_COORD, NBT.TAG_INT))
			z = tagBlockPos.getInteger(KEY_Z_COORD);
		
		else
			throw new IllegalArgumentException();
		
		xCoord = x;
		yCoord = y;
		zCoord = z;
	}
	
}
