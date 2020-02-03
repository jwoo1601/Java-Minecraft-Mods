package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.IntArrayAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_INT_ARRAY, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTIntArrayAttribute extends NBTAttribute<NBTTagIntArray> {
	
	public NBTIntArrayAttribute(@Nonnull String key, @Nonnull int[] value) {
		super(key, new NBTTagIntArray(value));
	}
	
	public NBTIntArrayAttribute(@Nonnull String key, @Nonnull NBTTagIntArray value) {
		super(key, value);
	}
	
	public NBTIntArrayAttribute(@Nonnull IntArrayAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public int[] getValueToIntArray() {
		return value.func_150302_c();
	}
	
	public IntArrayAttribute toPrimitive() {
		return new IntArrayAttribute(key, getValueToIntArray());
	}
	
}
