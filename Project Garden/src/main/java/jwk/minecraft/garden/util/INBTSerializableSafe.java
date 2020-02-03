package jwk.minecraft.garden.util;

import jwk.minecraft.garden.exception.NBTLoadException;
import jwk.minecraft.garden.exception.NBTSaveException;
import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializableSafe {

	void write(NBTTagCompound tagCompound) throws NBTSaveException;
	
	void read(NBTTagCompound tagCompound) throws NBTLoadException;
	
}
