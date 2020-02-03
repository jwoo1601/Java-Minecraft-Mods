package caramel.attribute;

import javax.annotation.Nonnull;

public class ShortArrayAttribute extends AbstractAttribute<short[]> {

	public ShortArrayAttribute(@Nonnull String key, @Nonnull short[] value) {
		super(key, value);
	}

}
