package jw.minecraft.utility.blockguard.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jw.minecraft.utility.blockguard.region.IRegion;
import jw.minecraft.utility.blockguard.region.PipedRegion;
import jw.minecraft.utility.exception.ElementAlreadyExistException;
import jw.minecraft.utility.exception.InvalidFormatException;
import jw.minecraft.utility.math.IComponent;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.nbt.INBTSerializable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class RegionMap<V extends IRegion> implements INBTSerializable {
	
	private Manager manager = new Manager();
	
	private transient Class<? extends IRegion> typeClass;
	
	private Map<String, V> instance;
	
	private Mode currentMode;
	
	public RegionMap(boolean sync, @Nonnull Class<? extends IRegion> type, Mode mode) {
		if (type == null)
			throw new NullPointerException("Class<? extends IRegion> type");
		
		if (sync)
			instance = new ConcurrentHashMap<String, V>();
		else
			instance = new HashMap<String, V>();
		
		typeClass = type;
		currentMode = mode;
	}
	
	public boolean isPositionWithinAnyRegion(Vec3i position) {
		for (V region : instance.values()) {
			if (region.isPoisitionInside(position))
				return true;
		}
		
		return false;
	}
	
	public class Manager {
		
		public boolean isValidId(@Nonnull String id) {
			if (id == null)
				throw new NullPointerException("String id");
			
			return !id.equals("") && !id.equals(" ");
		}
		
		public boolean isValidRegion(@Nonnull V region) {
			for (V target : instance.values()) {
				IComponent comp = target.getComponent();
				
				if (comp.rangeEquals(region.getComponent()) || target.isPoisitionInside(region.getComponent().getOrigin()) || target.isPoisitionInside(region.getComponent().getEnd()))
					return false;
			}
			
			return true;
		}
	}
	
	public boolean putIfAbsent(@Nonnull V region) {
		if (region == null)
			throw new NullPointerException("region");
		
		String id = region.getId();
		if (instance.containsKey(id) || !manager.isValidId(id) || !manager.isValidRegion(region))
			return false;
		
		instance.put(id, region);
		return true;
	}
	
	public RegionMap<V> safePutIfAbsent(@Nonnull V region) {
		if (region == null)
			throw new NullPointerException("region");
		
		String id = region.getId();
		if (instance.containsKey(id))
			throw new ElementAlreadyExistException("id");
		
		if (!manager.isValidId(id))
			throw new IllegalArgumentException("region.id");
		
		if (!manager.isValidRegion(region))
			throw new IllegalArgumentException("region.component");
		
		instance.put(id, region);		
		return this;
	}
	
	public boolean removeIfExist(@Nonnull String id) {
		if (id == null)
			throw new NullPointerException("String id");
		
		if (instance.containsKey(id)) {
			instance.remove(id);
			return true;
		}
		
		return false;
	}
	
	public RegionMap<V> safeRemoveIfExist(@Nonnull String id) {
		if (id == null)
			throw new NullPointerException("String id");
		
		if (instance.containsKey(id))
			instance.remove(id);
		else
			throw new NoSuchElementException("id");
		
		return this;
	}
	
	
	public Class<? extends IRegion> getTypeClass() { return typeClass; }
	
	public Manager getManager() { return manager; }
	
	public int size() { return instance.size(); }
	
	public Mode getMode() { return currentMode; }
	
	public RegionMap<V> setMode(Mode mode){
		if (mode == null)
			throw new NullPointerException("Mode mode");
		
		currentMode = mode;
		return this; 
	}
	
	public boolean isEmpty() { return instance.isEmpty(); }
	
	public boolean containsId(@Nonnull String id) {
		if (id == null)
			throw new NullPointerException("String id");
		
		return instance.containsKey(id);
	}
	
	public String getIdRangeEquals(@Nonnull IComponent component) {
		if (component == null)
			throw new NullPointerException("IComponent component");
		
		for (V region : instance.values()) {
			if (region.getComponent().rangeEquals(component))
				return region.getId();
		}
		
		return null;
	}
	
	public V getClone(@Nonnull String id) throws CloneNotSupportedException {
		if (id == null)
			throw new NullPointerException("String id");
		
		if (instance.containsKey(id))
			return (V) instance.get(id).clone();
		else
			return null;
	}
	
	public V safeGetClone(@Nonnull String id) throws CloneNotSupportedException {
		if (id == null)
			throw new NullPointerException("String id");
		
		if (instance.containsKey(id))
			return (V) instance.get(id).clone();
		else
			throw new NoSuchElementException("id");
	}
	
	
	private static final String KEYMODE = "mode";
	private static final String KEYSIZE = "size";
	private static final String KEYREGION = "region";

	@Override
	public void writeToNBT(NBTTagCompound tag) {			
		tag.setByte(KEYMODE, currentMode.Flag);
		
		int size = instance.size();
		tag.setInteger(KEYSIZE, size);
		
		if (size != 0) {
			NBTTagCompound tag_region = new NBTTagCompound();
			
			IRegion[] array = instance.values().toArray(new IRegion[size]);
			for (int index=0; index < size; index++) {
				NBTTagCompound tag_value = new NBTTagCompound();		
				
				array[index].writeToNBT(tag_value);				
				tag_region.setTag(Integer.toHexString(index), tag_value);
			}
			
			tag.setTag(KEYREGION, tag_region);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {			
		if (tag.hasKey(KEYMODE, NBT.TAG_BYTE)) {
			currentMode = Mode.getMode(tag.getByte(KEYMODE));
			
			if (tag.hasKey(KEYSIZE, NBT.TAG_INT)) {
				int size = tag.getInteger(KEYSIZE);
				
				if (size != 0) {
					if (tag.hasKey(KEYREGION, NBT.TAG_COMPOUND)) {
						NBTTagCompound tag_region = tag.getCompoundTag(KEYREGION);
						
						for (int index=0; index < size; index++) {
							String key = Integer.toHexString(index);
							
							if (tag_region.hasKey(key, NBT.TAG_COMPOUND)) {
								try {
									IRegion temp = typeClass.newInstance();
									temp.readFromNBT(tag_region.getCompoundTag(key));
									safePutIfAbsent((V) temp);
								    } 
								catch (InstantiationException e) { e.printStackTrace();	}
								catch (IllegalAccessException e) { e.printStackTrace();	}
							}
								
							else
								throw new InvalidFormatException("Region[" + key + "]");
						}
					}
					
					else
						throw new InvalidFormatException("KEYREGION");
				}
			}
			
			else
				throw new InvalidFormatException("KEYSIZE");
		}
		
		else
			throw new InvalidFormatException("KEYSTATE");
	}
}
