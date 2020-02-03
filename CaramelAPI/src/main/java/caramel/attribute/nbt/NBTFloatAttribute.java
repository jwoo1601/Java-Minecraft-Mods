package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.FloatAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_FLOAT, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTFloatAttribute extends NBTAttribute<NBTTagFloat> {

	public NBTFloatAttribute(@Nonnull String key, @Nonnull float value) {
		super(key, new NBTTagFloat(value));
	}
	
	public NBTFloatAttribute(@Nonnull String key, @Nonnull NBTTagFloat value) {
		super(key, value);
	}
	
	public NBTFloatAttribute(@Nonnull FloatAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public float getValueToFloat() {
		return value.func_150288_h();
	}
	
	public FloatAttribute toPrimitive() {
		return new FloatAttribute(key, getValueToFloat());
	}
	
}
