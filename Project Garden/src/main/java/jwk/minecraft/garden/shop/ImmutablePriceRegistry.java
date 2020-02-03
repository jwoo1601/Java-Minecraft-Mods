package jwk.minecraft.garden.shop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jwk.minecraft.garden.item.category.Categories;
import jwk.minecraft.garden.item.category.ItemCategory;

public class ImmutablePriceRegistry {
	
	private static final Map<ItemCategory, Long> OBJECTS = new ConcurrentHashMap<ItemCategory, Long>();
	
	static {
		OBJECTS.put(Categories.Hedge, 100L);
		OBJECTS.put(Categories.HedgeCarpet, 100L);
		OBJECTS.put(Categories.FlowerVine, 100L);
		OBJECTS.put(Categories.Sapling, 200L);
		OBJECTS.put(Categories.Flowers, 200L);
	}

	public static boolean hasImmutablePrice(ItemCategory category) {
		return OBJECTS.containsKey(category);
	}
	
	public static long getPriceOf(ItemCategory category) {
		Long val = OBJECTS.get(category);
		
		return val == null? -1 : val;
	}
	
}
