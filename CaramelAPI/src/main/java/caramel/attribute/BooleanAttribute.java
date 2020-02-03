package caramel.attribute;

import javax.annotation.Nonnull;

public class BooleanAttribute extends AbstractAttribute<Boolean> {

	public BooleanAttribute(@Nonnull String key, @Nonnull boolean value) {
		super(key, value);
	}
	
}
