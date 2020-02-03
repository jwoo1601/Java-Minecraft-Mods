package caramel.nbt.exception;

public class IllegalTypeException extends SerializationException {

	public IllegalTypeException(Class<?> type) {
		super(type.getName() + "is not a legal type!");
	}

}
