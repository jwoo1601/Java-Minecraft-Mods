package noah.minecraft.core.registry;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map;

public class AbstractRegistry<M extends Map<K, V>, K, V> implements IRegistry<K, V>{

	protected M map;
	
	@Override
	public boolean register(K key, V value) {
		checkNotNull(key, "key must not be null!");
		checkNotNull(value, "value must not be null!");
		
		if (!map.containsKey(key)) {
			map.put(key, value);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean unregister(K key) {
		checkNotNull(key, "key must not be null!");
		
		if (map.containsKey(key)) {
			map.remove(key);
			return true;
		}
		
		return false;
	}

	@Override
	public V get(K key) {
		checkNotNull(key, "key must not be null!");
		
		return map.get(key);
	}

	@Override
	public boolean contains(K key) {
		checkNotNull(key, "key must not be null!");
		
		return map.containsKey(key);
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

}
