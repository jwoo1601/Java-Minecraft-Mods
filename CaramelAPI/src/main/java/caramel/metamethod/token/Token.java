package caramel.metamethod.token;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Token {
	
	private TokenType type;
	private Object data = null;
	
	public Token(@Nonnull TokenType type) {
		this(type, null);
	}
	
	public Token(@Nonnull TokenType type, @Nullable Object data) {
		this.type = checkNotNull(type);
		this.data = data;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public boolean isTypeEquals(@Nonnull TokenType type) {
		return checkNotNull(type) == this.type;
	}
	
	public <T extends Object> T getDataAs() {
		
		if (data != null)
			return (T) data;
		
		return null;
	}
	
	public Object getData() {
		return data;
	}
	
	public Token setData(Object data) {
		this.data = data;
		
		return this;
	}
	
}
