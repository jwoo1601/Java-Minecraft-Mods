package jw.minecraft.utility.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {
	
	static final String NULL = "null";
	
	void writeToNBT(NBTTagCompound tag);
	
	void readFromNBT(NBTTagCompound tag);
}
