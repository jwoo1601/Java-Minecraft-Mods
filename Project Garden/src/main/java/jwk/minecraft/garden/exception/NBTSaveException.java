package jwk.minecraft.garden.exception;

public class NBTSaveException extends NBTException {
	
	public NBTSaveException() { }

	public NBTSaveException(String msg) {
		super(msg);
	}
	
	public NBTSaveException(Throwable cause) {
		super(cause);
	}
	
	public NBTSaveException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
