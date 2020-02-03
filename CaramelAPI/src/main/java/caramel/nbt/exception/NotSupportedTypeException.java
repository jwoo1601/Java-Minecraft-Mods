package caramel.nbt.exception;

import caramel.nbt.NBTTagType;

public class NotSupportedTypeException extends SerializationException {
	
	public NotSupportedTypeException(NBTTagType tagType) {
		super(tagType + " is not a supported NBTTagType");
	}
	
	public NotSupportedTypeException(String message) {
		super(message);
	}
	
}
