package caramel.attribute;

import javax.annotation.Nonnull;

public class LongArrayAttribute extends AbstractAttribute<long[]> {

	public LongArrayAttribute(@Nonnull String key, @Nonnull long[] value) {
		super(key, value);
	}

}
