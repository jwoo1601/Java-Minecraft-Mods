package caramel.attribute.nbt;

import javax.annotation.Nonnull;

import caramel.attribute.IntAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_INT, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTIntAttribute extends NBTAttribute<NBTTagInt> {
	
	public NBTIntAttribute(@Nonnull String key, @Nonnull int value) {
		super(key, new NBTTagInt(value));
	}
	
	public NBTIntAttribute(@Nonnull String key, @Nonnull NBTTagInt value) {
		super(key, value);
	}
	
	public NBTIntAttribute(@Nonnull IntAttribute attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public int getValueToInt() {
		return value.func_150287_d();
	}
	
	public IntAttribute toPrimitive() {
		return new IntAttribute(key, getValueToInt());
	}

}
