package caramel.metamethod;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

public class ReturnCache {

	private ReturnType returnType = ReturnType.VOID;
	private Object theObject = null;
	
	public void setReturn(@Nonnull Class<?> clazz) {
		this.returnType = ReturnType.CLASS;
		this.theObject = checkNotNull(clazz);
	}
	
	public void setReturn(@Nonnull Object object) {
		this.returnType = ReturnType.OBJECT;
		this.theObject = checkNotNull(object);
	}
	
	public void setReturnToVoid() {
		this.returnType = ReturnType.VOID;
		this.theObject = null;
	}
	
	public void setReturnToNull() {
		this.returnType = ReturnType.NULL;
		this.theObject = null;
	}
	
	public ReturnType getReturnType() { return returnType; }
	
	public Object getObject() { return theObject; }
	
}
