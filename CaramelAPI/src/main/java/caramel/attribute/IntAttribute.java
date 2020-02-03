package caramel.attribute;

import javax.annotation.Nonnull;

public class IntAttribute extends AbstractAttribute<Integer> {

	public IntAttribute(@Nonnull String key, @Nonnull int value) {
		super(key, value);
	}
	
}
