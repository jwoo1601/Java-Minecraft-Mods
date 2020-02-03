package caramel.metamethod.exception;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;

public class NoSuchFieldException extends RuntimeException {

	public NoSuchFieldException(@Nonnull String fieldName) {		
		super("cannot find the field which named " + fieldName);
	}
	
	public NoSuchFieldException(@Nonnull Field field) {
		super("cannot find the field which named " + field.getName());
	}
	
}
