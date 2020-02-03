package jwk.minecraft.garden.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {

	void write(NBTTagCompound tagCompound);
	
	void read(NBTTagCompound tagCompound);
	
}
