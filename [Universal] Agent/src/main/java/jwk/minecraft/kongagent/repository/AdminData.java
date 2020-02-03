package jwk.minecraft.kongagent.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants.NBT;

public class AdminData extends WorldSavedData {

	public static final String FILE_NAME = "AdminData";
	
	public AdminData() {
		super(FILE_NAME);
	}
	
    public AdminData(String str) {
    	super(str);
	}
	
	public Set<String> adminSet = Sets.newConcurrentHashSet();
	
	
	public static AdminData get(World world) {
		MapStorage storage = world.mapStorage;
		AdminData instance = (AdminData) storage.loadData(AdminData.class, FILE_NAME);
		
		if (instance == null) {
			instance = new AdminData();
			storage.setData(FILE_NAME, instance);
		}
		
		return instance;
	}

	private static final String KEY_ADMIN_DATA = "admin_data";
	private static final String KEY_ADMIN_NUM = "admin_num";
	private static final String KEY_ADMIN = "admin -";

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		NBTTagCompound tag_admin = new NBTTagCompound();
		
		int size = adminSet.size();
		tag_admin.setInteger(KEY_ADMIN_NUM, size);
		
		String[] array = adminSet.toArray(new String[size]);
		for (int i=0; i < size; i++)
			tag_admin.setString(KEY_ADMIN + Integer.toHexString(i), array[i]);
		
		tag.setTag(KEY_ADMIN_DATA, tag_admin);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		if (tag.hasKey(KEY_ADMIN_DATA, NBT.TAG_COMPOUND)) {
			NBTTagCompound tag_admin = tag.getCompoundTag(KEY_ADMIN_DATA);
			
			if (tag_admin.hasKey(KEY_ADMIN_NUM, NBT.TAG_INT)) {
				int size = tag_admin.getInteger(KEY_ADMIN_NUM);
				
				if (!adminSet.isEmpty())
					adminSet.clear();
				for (int i=0; i < size; i++) {
					
					String key = KEY_ADMIN + Integer.toHexString(i);					
					if (tag_admin.hasKey(key, NBT.TAG_STRING))
						adminSet.add(tag_admin.getString(key));
					
					else
						throw new IllegalArgumentException("tag does not have appropriate key(" + key + ")");
					
				}
			}
			
			else
				throw new IllegalArgumentException("tag does not have appropriate key(" + KEY_ADMIN_NUM + ")");
		}
		
		else
			throw new IllegalArgumentException("tag does not have appropriate key( " + KEY_ADMIN_DATA + ")");
	}
}
