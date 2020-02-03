package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.ByteArrayAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_BYTE_ARRAY, key = "__valueof(key)", value = "__valueof(__memberof(value))") })
@NBTSerializable
public class NBTByteArrayAttribute extends NBTAttribute<NBTTagByteArray> {
	
	public NBTByteArrayAttribute(@Nonnull String key, @Nonnull byte[] value) {
		super(key, new NBTTagByteArray(value));
	}
	
	public NBTByteArrayAttribute(@Nonnull String key, @Nonnull NBTTagByteArray value) {
		super(key, value);
	}
	
	public NBTByteArrayAttribute(@Nonnull ByteArrayAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public byte[] getValueToByteArray() {
		return value.func_150292_c();
	}
	
	public ByteArrayAttribute toPrimitive() {
		return new ByteArrayAttribute(key, getValueToByteArray());
	}
	
}
