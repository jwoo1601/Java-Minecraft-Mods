package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.DoubleAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_DOUBLE, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTDoubleAttribute extends NBTAttribute<NBTTagDouble> {
	
	public NBTDoubleAttribute(@Nonnull String key, @Nonnull double value) {
		super(key, new NBTTagDouble(value));
	}
	
	public NBTDoubleAttribute(@Nonnull String key, @Nonnull NBTTagDouble value) {
		super(key, value);
	}
	
	public NBTDoubleAttribute(@Nonnull DoubleAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public double getValueToDouble() {
		return value.func_150286_g();
	}
	
	public DoubleAttribute toPrimitive() {
		return new DoubleAttribute(key, getValueToDouble());
	}
	
}
