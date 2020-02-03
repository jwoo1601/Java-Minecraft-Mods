package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.ShortAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagShort;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_SHORT, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTShortAttribute extends NBTAttribute<NBTTagShort> {

	public NBTShortAttribute(@Nonnull String key, @Nonnull short value) {
		super(key, new NBTTagShort(value));
	}
	
	public NBTShortAttribute(@Nonnull String key, @Nonnull NBTTagShort value) {
		super(key, value);
	}
	
	public NBTShortAttribute(@Nonnull ShortAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public short getValueToShort() {
		return value.func_150289_e();
	}
	
	public ShortAttribute toPrimitive() {
		return new ShortAttribute(key, getValueToShort());
	}
	
}
