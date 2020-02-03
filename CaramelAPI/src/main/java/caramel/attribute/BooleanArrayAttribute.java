package caramel.attribute;

import javax.annotation.Nonnull;

public class BooleanArrayAttribute extends AbstractAttribute<boolean[]> {

	public BooleanArrayAttribute(@Nonnull String key, @Nonnull boolean[] value) {
		super(key, value);
	}

}
