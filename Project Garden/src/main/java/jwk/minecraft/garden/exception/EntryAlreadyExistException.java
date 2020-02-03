package jwk.minecraft.garden.exception;

public class EntryAlreadyExistException extends RuntimeException {

	public EntryAlreadyExistException(String message) {
		super(message);
	}
	
	public EntryAlreadyExistException(Throwable t) {
		super(t);
	}
	
	public EntryAlreadyExistException(String message, Throwable t) {
		super(message, t);
	}
	
	public EntryAlreadyExistException(Object key) {
		super("key: " + key.toString() + " is already exist!");
	}
}
