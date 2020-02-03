package jwk.minecraft.garden.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class NBTFileManager {

	private File target;
	
	public NBTFileManager(@Nonnull File file) {
		target = checkNotNull(file);
	}
	
	public NBTFileManager(@Nonnull String fullPath) {
		target = new File(checkNotNull(fullPath));
	}
	
	public NBTFileManager(@Nonnull String path, @Nonnull String name) {
		target = new File(checkNotNull(path + "\\" + name));
	}
	
	public void writeToFile(boolean override, @Nonnull NBTTagCompound compound) throws IOException {
		checkNotNull(compound);		
		
		if (!override && target.exists())
			throw new FileAlreadyExistsException(target.getAbsolutePath());
		else if (!target.exists())
			target.mkdirs();			
		
		CompressedStreamTools.safeWrite(compound, target);
	}
	
	public NBTTagCompound readFromFile() throws IOException {
		
		if (target.exists())
			return CompressedStreamTools.read(target);
		
		throw new FileNotFoundException(target.getAbsolutePath());
	}
	
}
