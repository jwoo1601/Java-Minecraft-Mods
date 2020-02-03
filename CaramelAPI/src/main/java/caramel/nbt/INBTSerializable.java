package caramel.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {
	
	void read(NBTTagCompound tagCompound);
	
	void write(NBTTagCompound tagCompound);
	
}
