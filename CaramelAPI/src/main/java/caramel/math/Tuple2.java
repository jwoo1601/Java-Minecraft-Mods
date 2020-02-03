package caramel.math;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

public class Tuple2<K, V> {
	
	public final K Key;
	
	public final V Value;
	
	public Tuple2(@Nonnull K key, @Nonnull V value) {
		Key = checkNotNull(key);
		Value = checkNotNull(value);
	}
	
}
