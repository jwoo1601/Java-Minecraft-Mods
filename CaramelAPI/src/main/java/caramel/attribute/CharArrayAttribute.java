package caramel.attribute;

import javax.annotation.Nonnull;

public class CharArrayAttribute extends AbstractAttribute<char[]> {

	public CharArrayAttribute(@Nonnull String key, @Nonnull char[] value) {
		super(key, value);
	}

}
