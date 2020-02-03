package caramel.parser.exception;

public class NoSuchFieldException extends ParsingException {

	public NoSuchFieldException(String fieldName) {
		super("cannot find the field which named " + fieldName);
	}

}
