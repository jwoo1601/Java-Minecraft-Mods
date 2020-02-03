package caramel.math;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

public class Tuple4<K, V, V2, V3> {
	
	public final K Key;
	
	public final V FirstValue;
	
	public final V2 SecondValue;
	
	public final V3 ThirdValue;
	
	public Tuple4(@Nonnull K key, @Nonnull V firstValue, @Nonnull V2 secondValue, @Nonnull V3 thirdValue) {
		Key = checkNotNull(key);
		FirstValue = checkNotNull(firstValue);
		SecondValue = checkNotNull(secondValue);
		ThirdValue = checkNotNull(thirdValue);
	}
	
}
