package caramel.parser.exception;

import caramel.nbt.exception.SerializationException;

public class IllegalFormatException extends ParsingException {
	
	public IllegalFormatException() { super(); }

	public IllegalFormatException(String message) {
		super(message);
	}

}
