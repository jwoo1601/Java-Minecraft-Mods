package caramel.attribute;

import javax.annotation.Nonnull;

public class CharAttribute extends AbstractAttribute<Character> {

	public CharAttribute(@Nonnull String key, @Nonnull char value) {
		super(key, value);
	}
	
}
