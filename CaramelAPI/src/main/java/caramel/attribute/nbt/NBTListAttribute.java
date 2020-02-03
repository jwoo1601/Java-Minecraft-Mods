package caramel.attribute.nbt;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;

import caramel.attribute.ListAttribute;
import caramel.nbt.AdditionalTags;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTTagType;
import caramel.nbt.NBTSerializable.NBTTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

@AdditionalTags(tags = { @NBTTag(type = NBTTagType.TAG_LIST, key = "__valueof(key)", value = "__valueof(value)") })
@NBTSerializable
public class NBTListAttribute<T extends NBTBase> extends NBTAttribute<NBTTagList> {
	
	public NBTListAttribute(@Nonnull String key, @Nonnull List<T> list) {		
		super(key, new NBTTagList());
		
		checkNotNull(list);
		
		for (T e : list)
			value.appendTag(e);
	}
	
	public NBTListAttribute(@Nonnull String key, @Nonnull NBTTagList value) {
		super(key, value);
	}
	
	public NBTListAttribute(@Nonnull ListAttribute<T> attribute) {
		this(attribute.getKey(), attribute.getValue());
	}
	
	public ListAttribute<T> toPrimitive() {
		try {
			return new ListAttribute<T> (key, (List<T>) NBTTagList.class.getField("tagList").get(value));
		}
		
		catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public boolean isEmpty() {
		return value.tagCount() == 0;
	}
	
	public int getSize() {
		return value.tagCount();
	}
	
	public T get(int index) {
		
		try {
			List<T> list = (List<T>) NBTTagList.class.getField("tagList").get(value);
			
			return list.get(index);
		}
		
		catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public void set(int index, T element) {
		value.func_150304_a(index, element);
	}
	
	public void append(T element) {
		value.appendTag(element);
	}
	
    public NBTBase removeAt(int index) {
        return value.removeTag(index);
    }
	
	public int getTagType() {
		return value.func_150303_d();
	}
	
}
