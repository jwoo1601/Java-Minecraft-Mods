package noah.teleport.place;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTData {
	
	void writeToNBTData(NBTTagCompound nbtdata);
	void readFromNBTData(NBTTagCompound nbtdata);
}
