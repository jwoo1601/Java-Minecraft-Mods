package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.ByteAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagInt;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_BYTE, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTByteAttribute extends NBTAttribute<NBTTagByte> {

	public NBTByteAttribute(@Nonnull String key, @Nonnull byte value) {
		super(key, new NBTTagByte(value));
	}
	
	public NBTByteAttribute(@Nonnull String key, @Nonnull NBTTagByte value) {
		super(key, value);
	}
	
	public NBTByteAttribute(@Nonnull ByteAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public byte getValueToByte() {
		return value.func_150290_f();
	}
	
	public ByteAttribute toPrimitive() {
		return new ByteAttribute(key, getValueToByte());
	}

}
