package jw.minecraft.utility.blockguard.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Nonnull;

import jw.minecraft.utility.blockguard.region.IRegion;
import jw.minecraft.utility.blockguard.region.PipedRegion;
import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class RegionChunk<V extends IRegion> implements INBTSerializable {
	
	private static long HandleCount = 0x00;
	

	
	
	private final long HANDLE;
	
	private List<RegionMap<V>> list;
	
	private RegionChunk(long handle) {
		HANDLE = handle;
		list = new ArrayList<RegionMap<V>>();
	}
	
	private RegionChunk(long handle, List<RegionMap<V>> list) {
		if (list == null)
			throw new NullPointerException("List<RegionMap<? extends IRegion>> list");
		
		HANDLE = handle;
		this.list = list;
	}
	
	public RegionChunk<V> add(@Nonnull RegionMap<V> target) {
		if (target == null)
			throw new NullPointerException("RegionMap target");
		
		list.add(target);
		return this;
	}
	
	public RegionChunk<V> delete(@Nonnull RegionMap<V> target) {
		if (target == null)
			throw new NullPointerException("RegionMap target");
		
		if (list.contains(target))
			list.remove(target);
		else
			throw new NoSuchElementException("target");
		
		return this;
	}
	
	public List<RegionMap<V>> getList() { return list; }
	
	static enum ClassType {
		PIPEDREGION((byte)0x00, PipedRegion.class);
		
		private final byte Mask;
		private final Class Type;
		
		private ClassType(byte mask, Class<? extends IRegion> type) {
			Mask = mask;
			Type = type;
		}
		
		public byte getMask() { return Mask; }
		
		public Class getType() { return Type; }
		
		public static Class<? extends IRegion> getTypeFromMask(byte mask) {
			switch (mask) {
			case 0x00:
				return PipedRegion.class;
			default:
				return null;
			}
		}
		
		public static byte getMaskFromType(Class<? extends IRegion> type) {
			if (type.equals(PipedRegion.class))
				return 0x00;
			else
				return -1;
		}
	}
	
	private static final String KEYSIZE = "size";
	static final String KEYTYPE = "type";
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		int size = list.size();
		tag.setInteger(KEYSIZE, size);
		
		if (size != 0) {
			Class<? extends IRegion> typeClass = list.get(0).getTypeClass();			
			tag.setByte(KEYTYPE, ClassType.getMaskFromType(typeClass));		
			
			for (int index=0; index < list.size(); index++) {
				NBTTagCompound tag_map = new NBTTagCompound();
				
				list.get(index).writeToNBT(tag_map);
				tag.setTag(Integer.toHexString(index), tag_map);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEYSIZE, NBT.TAG_INT)) {
			int size = tag.getInteger(KEYSIZE);
			
			if (size != 0) {
				if (tag.hasKey(KEYTYPE, NBT.TAG_BYTE)) {
					Class<? extends IRegion> typeClass = ClassType.getTypeFromMask(tag.getByte(KEYTYPE));
					
					for (int index=0; index < size; index++) {
						String key = Integer.toHexString(index);
						
						if (tag.hasKey(key, NBT.TAG_COMPOUND)) {
							NBTTagCompound tag_map = tag.getCompoundTag(key);
							
							RegionMap<V> map = new RegionMap<V>(true, typeClass, Mode.PROTECT);
							map.readFromNBT(tag_map);
							list.add(map);
						}
						
						else
							throw new InvalidFormatException("RegionMap[" + index + "]");
					}
				}
				
				else
					throw new InvalidFormatException("KEYTYPE");
			}
		}
		
		else
			throw new InvalidFormatException("KEYSIZE");
	}	
	
	
	public long handle() { return HANDLE; }
	
	public static <T extends IRegion> RegionChunk<T> newInstance(Class<T> t) {
		return new RegionChunk<T>(HandleCount++);
	}
	
	public static <T extends IRegion> RegionChunk<T> newInstance(Class<T> t, @Nonnull List<RegionMap<T>> list) {		
		return new RegionChunk<T>(HandleCount++, list);
	}
}
