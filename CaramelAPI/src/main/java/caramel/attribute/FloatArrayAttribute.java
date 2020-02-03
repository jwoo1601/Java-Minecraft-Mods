package caramel.attribute;

import javax.annotation.Nonnull;

public class FloatArrayAttribute extends AbstractAttribute<float[]> {

	public FloatArrayAttribute(@Nonnull String key, @Nonnull float[] value) {
		super(key, value);
	}

}
