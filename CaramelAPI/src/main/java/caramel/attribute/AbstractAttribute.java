package caramel.attribute;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

public abstract class AbstractAttribute<T> implements IAttribute<T> {
	
	protected String key = null;
	protected T value = null;
	
	protected AbstractAttribute(@Nonnull String key, @Nonnull T value) {
		this.key = checkNotNull(key);
		this.value = checkNotNull(value);
	}
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "Attribute: <Key = " + key  + " Value = " + value.toString() + ">";
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof IAttribute) {
			IAttribute attr = (IAttribute) object;
			
			return key.equals(attr.getKey()) && value.equals(attr.getValue());
		}
		
		return false;
	}

}
