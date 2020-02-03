package caramel.attribute;

import javax.annotation.Nonnull;

public class LongAttribute extends AbstractAttribute<Long> {

	public LongAttribute(@Nonnull String key, @Nonnull long value) {
		super(key, value);
	}
	
}
