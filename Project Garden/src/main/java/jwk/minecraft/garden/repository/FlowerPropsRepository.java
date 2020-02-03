package jwk.minecraft.garden.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.flower.FlowerProperty;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.SimpleRegion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class FlowerPropsRepository extends WorldSavedData {
	
	public static final String FILE_NAME = "FlowerProps";

	public static final SimpleRegion DEFAULT_FLOWER_FACTORY = new SimpleRegion(new BlockPos(-138, 63, -120), new BlockPos(-236, 4, -275));
	
	private SimpleRegion regionFlowerFactory = DEFAULT_FLOWER_FACTORY;
	
	private final Map<BlockPos, FlowerProperty> propertyMap = new ConcurrentHashMap<BlockPos, FlowerProperty> ();
	
	public FlowerPropsRepository() { super(FILE_NAME); }
	
	public FlowerPropsRepository(String name) { super(name); }
	
	public static FlowerPropsRepository get(World world) {
		MapStorage storage = world.perWorldStorage;
		int dimensionId = world.provider.dimensionId;
		FlowerPropsRepository instance = (FlowerPropsRepository) storage.loadData(FlowerPropsRepository.class, FILE_NAME);
		
		if (instance == null) {
			instance = new FlowerPropsRepository();
			storage.setData(FILE_NAME, instance);
		}
		
		return instance;
	}
	
	public SimpleRegion getFlowerFactory() { return regionFlowerFactory; }
	
	public FlowerPropsRepository setFlowerFactory(@Nonnull SimpleRegion region) {
		checkNotNull(region);
		
		regionFlowerFactory = region;
		return this;
	}
	
	public FlowerPropsRepository put(@Nonnull FlowerProperty property) {
		checkNotNull(property);
		
		if (!propertyMap.containsKey(property.getPosition()))
			propertyMap.put(property.getPosition(), property);
		
		return this;
	}
	
	public FlowerPropsRepository replace(@Nonnull FlowerProperty property) {
		checkNotNull(property);
		
		if (propertyMap.containsKey(property.getPosition()))
			propertyMap.put(property.getPosition(), property);
		
		return this;
	}
	
	public FlowerPropsRepository remove(@Nonnull BlockPos pos) {
		checkNotNull(pos);
		
		if (propertyMap.containsKey(pos))
			propertyMap.remove(pos);
		
		return this;
	}
	
	public FlowerPropsRepository remove(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		
		if (propertyMap.containsKey(pos))
			propertyMap.remove(pos);
		
		return this;
	}
	
	public FlowerProperty get(@Nonnull BlockPos pos) {
		checkNotNull(pos);
		
		return propertyMap.get(pos);
	}
	
	public FlowerProperty get(int x, int y, int z) {		
		return propertyMap.get(new BlockPos(x, y, z));
	}
	
	public int size() { return propertyMap.size(); }
	
	public boolean isEmpty() { return propertyMap.isEmpty(); }
	
	public boolean contains(@Nonnull BlockPos pos) {
		return propertyMap.containsKey(checkNotNull(pos));
	}
	
	public FlowerProperty[] toArray() {
		return propertyMap.values().toArray(new FlowerProperty[] {});
	}
	
	
	private static final String KEY_REPOSITORY = "Flower Repository";
	private static final String KEY_FLOWER_FACTORY = "Flower Factory";
	private static final String KEY_PROPERTIES = "Flower Properties";
	private static final String KEY_SIZE = "Size";
	private static final String KEY_PROPERTY_BASE = "Prop -";
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		NBTTagCompound tagRepository = null;
		
		if (tagCompound.hasKey(KEY_REPOSITORY, NBT.TAG_COMPOUND))
			tagRepository = tagCompound.getCompoundTag(KEY_REPOSITORY);
		else
			throw new IllegalArgumentException();
		
		NBTTagCompound tagFactory = null;
		if (tagRepository.hasKey(KEY_FLOWER_FACTORY, NBT.TAG_COMPOUND))
			tagFactory = tagRepository.getCompoundTag(KEY_FLOWER_FACTORY);
		else
			throw new IllegalArgumentException();
		
		regionFlowerFactory.read(tagFactory);
		
		NBTTagCompound tagProperties = null;
		if (tagRepository.hasKey(KEY_PROPERTIES, NBT.TAG_COMPOUND))
			tagProperties = tagRepository.getCompoundTag(KEY_PROPERTIES);
		else
			throw new IllegalArgumentException();
		
		int size = 0;
		if (tagProperties.hasKey(KEY_SIZE, NBT.TAG_INT))
			size = tagProperties.getInteger(KEY_SIZE);
		else
			throw new IllegalArgumentException();
		
		if (size != 0) {
			
			for (int i=0; i < size; i++) {
				FlowerProperty property = new FlowerProperty();
				String key = KEY_PROPERTY_BASE + i;
				
				if (tagProperties.hasKey(key, NBT.TAG_COMPOUND))
					property.read(tagProperties.getCompoundTag(key));
				else
					throw new IllegalArgumentException();
				
				propertyMap.put(property.getPosition(), property);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		NBTTagCompound tagRepository = new NBTTagCompound();
		
		NBTTagCompound tagFactory = new NBTTagCompound();
		regionFlowerFactory.write(tagFactory);
		tagRepository.setTag(KEY_FLOWER_FACTORY, tagFactory);
		
		NBTTagCompound tagProperties = new NBTTagCompound();
		
		int size = propertyMap.size();
		
		tagProperties.setInteger(KEY_SIZE, size);
		
		if (size != 0) {
			FlowerProperty[] array = new FlowerProperty[size];
			array = propertyMap.values().toArray(array);
		
			for (int i=0; i < size; i++) {
				NBTTagCompound tagProperty = new NBTTagCompound();
			
				array[i].write(tagProperty);
				tagProperties.setTag(KEY_PROPERTY_BASE + i, tagProperty);
			}
		}
		
		tagRepository.setTag(KEY_PROPERTIES, tagProperties);
		
		tagCompound.setTag(KEY_REPOSITORY, tagRepository);
	}

}
