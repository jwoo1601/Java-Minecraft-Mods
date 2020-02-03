package caramel.attribute;

import javax.annotation.Nonnull;

public class DoubleAttribute extends AbstractAttribute<Double> {

	public DoubleAttribute(@Nonnull String key, @Nonnull double value) {
		super(key, value);
	}
	
}
