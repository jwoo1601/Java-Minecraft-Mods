package jw.minecraft.utility.math;

import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.Constants.NBT;

public class Vec3i implements INBTSerializable, Cloneable {
		
	public static final Vec3i ZERO() {
		return new Vec3i(0, 0, 0);
	}
	
	public int xCoord = 0;
	public int yCoord = 0;
	public int zCoord = 0;
	
	protected Vec3i(int x, int y, int z) {				
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
	public Object clone() throws CloneNotSupportedException {
		return new Vec3i(xCoord, yCoord, zCoord);
	}
	
	public static Vec3i createVector(int x, int y, int z) {
		return new Vec3i(x, y, z);
	}
	
	public Vec3 toVec3() {
		return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Vec3i) {
			Vec3i tmp = (Vec3i) o;
			return tmp.xCoord == this.xCoord && tmp.yCoord == this.yCoord && tmp.zCoord == this.zCoord;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + xCoord + "," + yCoord + "," + zCoord + ")";
	}

	private static final String KEYX = "x";
	private static final String KEYY = "y";
	private static final String KEYZ = "z";
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger(KEYX, xCoord);
		tag.setInteger(KEYY, yCoord);
		tag.setInteger(KEYZ, zCoord);
	}


	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYX, NBT.TAG_INT))
			xCoord = tag.getInteger(KEYX);
		else
			throw new InvalidFormatException("int xCoord");
		
		if (tag.hasKey(KEYY, NBT.TAG_INT))
			yCoord = tag.getInteger(KEYY);
		else
			throw new InvalidFormatException("int yCoord");
		
		if (tag.hasKey(KEYZ, NBT.TAG_INT))
			zCoord = tag.getInteger(KEYZ);
		else
			throw new InvalidFormatException("int zCoord");
	}
}
