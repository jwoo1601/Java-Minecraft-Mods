package jwk.minecraft.garden.shop;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;

import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerShopCache {

	private static final Map<UUID, ShopManager> map = new ConcurrentHashMap<UUID, ShopManager> ();
	
	public static boolean contains(UUID id) {
		return map.containsKey(id);
	}
	
	public static boolean contains(EntityPlayer player) {
		return map.containsKey(player.getUniqueID());
	}
	
	public static void put(EntityPlayer player, ShopManager data) {
		map.put(player.getUniqueID(), data);
	}
	
	public static void put(UUID id, ShopManager data) {
		map.put(id, data);
	}

	public static ShopManager get(EntityPlayer player) {
		return map.get(player.getUniqueID());
	}
	
	public static ShopManager get(UUID id) {
		return map.get(id);
	}
	
	public static boolean remove(EntityPlayer player) {
		
		if (map.containsKey(player.getUniqueID())) {
			map.remove(player.getUniqueID());
			return true;
		}
		
		return false;
	}
	
	public static boolean remove(UUID id) {
		
		if (map.containsKey(id)) {
			map.remove(id);
			return true;
		}
		
		return false;
	}
	
	public static int size() { return map.size(); }
	
	public static boolean isEmpty() { return map.isEmpty(); }
	
	public static void clear() { map.clear(); }
	
	public static List<EntityPlayerMP> getAllCustomers(ShopManager manager) {
		List<EntityPlayerMP> list = Lists.newArrayList();
		
		for (Entry<UUID, ShopManager> entry : map.entrySet()) {
			
			if (entry.getValue() == manager) {
				EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(entry.getKey());
				
				if (player != null)
					list.add(player);
			}
		}
		
		return list;
	}
	
}
