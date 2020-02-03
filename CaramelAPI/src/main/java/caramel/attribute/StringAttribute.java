package caramel.attribute;

import javax.annotation.Nonnull;

public class StringAttribute extends AbstractAttribute<String> {
	
	public StringAttribute(@Nonnull String key, @Nonnull char value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull char[] value) {
		super(key, String.valueOf(value));
	}

	public StringAttribute(@Nonnull String key, @Nonnull String value) {
		super(key, value);
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull byte value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull short value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull int value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull long value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull float value) {
		super(key, String.valueOf(value));
	}
	
	public StringAttribute(@Nonnull String key, @Nonnull double value) {
		super(key, String.valueOf(value));
	}
	
}
