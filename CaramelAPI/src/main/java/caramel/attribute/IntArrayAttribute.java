package caramel.attribute;

import javax.annotation.Nonnull;

public class IntArrayAttribute extends AbstractAttribute<int[]> {

	public IntArrayAttribute(@Nonnull String key, @Nonnull int[] value) {
		super(key, value);
	}

}
