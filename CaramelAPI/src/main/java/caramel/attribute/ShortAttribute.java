package caramel.attribute;

import javax.annotation.Nonnull;

public class ShortAttribute extends AbstractAttribute<Short> {

	public ShortAttribute(@Nonnull String key, @Nonnull short value) {
		super(key, value);
	}
	
}
