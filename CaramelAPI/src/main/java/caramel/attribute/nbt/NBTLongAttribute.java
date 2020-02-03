package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.LongAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagLong;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_LONG, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTLongAttribute extends NBTAttribute<NBTTagLong> {

	public NBTLongAttribute(@Nonnull String key, @Nonnull long value) {
		super(key, new NBTTagLong(value));
	}
	
	public NBTLongAttribute(@Nonnull String key, @Nonnull NBTTagLong value) {
		super(key, value);
	}
	
	public NBTLongAttribute(@Nonnull LongAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public long getValueToLong() {
		return value.func_150291_c();
	}
	
	public LongAttribute toPrimitive() {
		return new LongAttribute(key, getValueToLong());
	}
	
}
