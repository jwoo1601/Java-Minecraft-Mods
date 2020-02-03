package jwk.minecraft.garden.exception;

public class NBTLoadException extends NBTException {
	
	public NBTLoadException() { }

	public NBTLoadException(String msg) {
		super(msg);
	}
	
	public NBTLoadException(Throwable cause) {
		super(cause);
	}
	
	public NBTLoadException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
