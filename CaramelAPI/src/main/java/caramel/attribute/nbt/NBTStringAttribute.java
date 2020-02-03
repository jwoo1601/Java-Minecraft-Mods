package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.StringAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_STRING, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTStringAttribute extends NBTAttribute<NBTTagString> {

	public NBTStringAttribute(@Nonnull String key, @Nonnull String value) {
		super(key, new NBTTagString(value));
	}
	
	public NBTStringAttribute(@Nonnull String key, @Nonnull NBTTagString value) {
		super(key, value);
	}
	
	public NBTStringAttribute(@Nonnull StringAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public String getValueToString() {
		return value.func_150285_a_();
	}
	
	public StringAttribute toPrimitive() {
		return new StringAttribute(key, getValueToString());
	}
	
}
