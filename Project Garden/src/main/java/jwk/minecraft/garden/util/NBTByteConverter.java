package jwk.minecraft.garden.util;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class NBTByteConverter {

	public static final long MAX_NBT_SIZE = 2097152L;
	
	public static void writeNBT(ByteBuf buffer, NBTTagCompound compound) throws IOException {
		byte[] bytes = CompressedStreamTools.compress(compound);
		buffer.writeShort((short)bytes.length);
		buffer.writeBytes(bytes);
	}

	public static NBTTagCompound readNBT(ByteBuf buffer) throws IOException {
		byte[] bytes = new byte[buffer.readShort()];
		buffer.readBytes(bytes);
		return CompressedStreamTools.func_152457_a(bytes, new NBTSizeTracker(MAX_NBT_SIZE));
	}
		 
}
