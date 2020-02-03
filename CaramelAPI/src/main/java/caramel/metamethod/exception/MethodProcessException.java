package caramel.metamethod.exception;

public class MethodProcessException extends RuntimeException {
	
	public MethodProcessException() {
		super();
	}
	
	public MethodProcessException(String message) {
		super(message);
	}
	
	public MethodProcessException(Throwable t) {
		super(t);
	}
	
	public MethodProcessException(String message, Throwable t) {
		super(message, t);
	}
	
}
