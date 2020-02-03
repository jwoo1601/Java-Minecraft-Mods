package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_COMPOUND, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTCompoundAttribute extends NBTAttribute<NBTTagCompound> {
	
	public NBTCompoundAttribute(@Nonnull String key, @Nonnull NBTTagCompound value) {
		super(key, value);
	}
	
}
