package caramel.math;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

public class Tuple3<K, V, V2> {
	
	public final K Key;
	
	public final V FirstValue;
	
	public final V2 SecondValue;
	
	public Tuple3(@Nonnull K key, @Nonnull V firstValue, @Nonnull V2 secondValue) {
		Key = checkNotNull(key);
		FirstValue = checkNotNull(firstValue);
		SecondValue = checkNotNull(secondValue);
	}
	
}
