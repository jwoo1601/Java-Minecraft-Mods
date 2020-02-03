package caramel.math;

import caramel.nbt.INBTSerializable;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

@NBTSerializable
public class BlockPos {
	
	public static final BlockPos zero() {
		return new BlockPos(0, 0, 0);
	}
	
	@NBTTag(type = NBTTagType.TAG_INT, key="__valueof(a)")
	protected int xCoord = 0;
	@NBTTag(type = NBTTagType.TAG_INT)
	protected int yCoord = 0;
	@NBTTag(type = NBTTagType.TAG_INT)
	protected int zCoord = 0;
	
	private String a = "haha";
	
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
	
	@Override
	public Object clone() {
		return new BlockPos(xCoord, yCoord, zCoord);
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
	
}
