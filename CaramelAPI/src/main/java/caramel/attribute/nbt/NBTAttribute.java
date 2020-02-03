package caramel.attribute.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import caramel.attribute.AbstractAttribute;
import caramel.attribute.IAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTSerializable.NBTTag;
import caramel.nbt.NBTTagType;
import net.minecraft.nbt.NBTBase;

public class NBTAttribute<T extends NBTBase> extends AbstractAttribute<T> {
	
	protected NBTAttribute(@Nonnull String key, @Nonnull T value) {
		super(key, value);
	}
	
	public NBTAttribute<T> copy() {
		return new NBTAttribute<T> (new String(key), (T) value.copy());
	}
	
	@Override
	public String toString() {
		return "NBTAttribute: <Key = " + key  + " Value = " + value.toString() + ">";
	}

}
