package jwk.minecraft.garden.exception;

public class NBTException extends Exception {
	
	public NBTException() { }

	public NBTException(String msg) {
		super(msg);
	}
	
	public NBTException(Throwable cause) {
		super(cause);
	}
	
	public NBTException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
