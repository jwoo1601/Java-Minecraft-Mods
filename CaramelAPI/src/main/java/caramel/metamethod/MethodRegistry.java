package caramel.metamethod;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public class MethodRegistry {

	private static final Map<String, IMetaMethod> map = new ConcurrentHashMap<String, IMetaMethod>();
	
	public static final String PATTERN_ID = "^[_a-zA-Z][_a-zA-Z0-9]*$";
	
	public static final ReturnCache RETURN_CACHE = new ReturnCache();
	
	public static final boolean register(@Nonnull String id, @Nonnull IMetaMethod method) {
		checkNotNull(id);
		checkNotNull(method);
		
		if (!Pattern.matches(PATTERN_ID, id))
			return false;
		
		else if (map.containsKey(id))
			return false;
		
		map.put(id, method);
		return true;
	}
	
	public static final boolean unregister(@Nonnull String id) {
		checkNotNull(id);
		
		if (map.containsKey(id)) {
			map.remove(id);
			return true;
		}
		
		return false;
	}
	
	public static final IMetaMethod get(@Nonnull String id) {
		return map.get(checkNotNull(id));
	}
	
	public static final boolean isEmpty() { return map.isEmpty(); }
	
	public static final boolean contains(@Nonnull String id) { return map.containsKey(checkNotNull(id)); }
	
	public static int size() { return map.size(); }
	
	
	private static void registerMethods() {
		register(MetaValueOf.NAME, new MetaValueOf());
		register(MetaMemberOf.NAME, new MetaMemberOf());
	}
	
	static {
		registerMethods();
	}
	
}
