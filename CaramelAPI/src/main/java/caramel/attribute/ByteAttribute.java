package caramel.attribute;

import javax.annotation.Nonnull;

public class ByteAttribute extends AbstractAttribute<Byte> {

	public ByteAttribute(@Nonnull String key, @Nonnull byte value) {
		super(key, value);
	}
	
}
