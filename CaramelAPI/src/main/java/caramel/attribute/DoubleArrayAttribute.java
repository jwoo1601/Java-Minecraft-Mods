package caramel.attribute;

import javax.annotation.Nonnull;

public class DoubleArrayAttribute extends AbstractAttribute<double[]> {

	public DoubleArrayAttribute(@Nonnull String key, @Nonnull double[] value) {
		super(key, value);
	}

}
