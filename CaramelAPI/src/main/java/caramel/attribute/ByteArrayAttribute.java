package caramel.attribute;

import javax.annotation.Nonnull;

public class ByteArrayAttribute extends AbstractAttribute<byte[]> {

	public ByteArrayAttribute(@Nonnull String key, @Nonnull byte[] value) {
		super(key, value);
	}

}
