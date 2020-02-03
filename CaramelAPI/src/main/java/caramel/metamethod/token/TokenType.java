package caramel.metamethod.token;

import javax.annotation.Nonnull;

public enum TokenType {

	PARAMS("PAR", (byte) 0x00),
	VALUEOF("VOF", (byte) 0x01),
	MEMBEROF("MOF", (byte) 0x02);
	
	public final String TokenString;
	
	public final byte Value;
	
	private TokenType(String tokenString, byte value) {
		TokenString = tokenString;
		Value = value;
	}
	
	public static final TokenType fromString(@Nonnull String tokenString) {
		
		if (tokenString.equals(PARAMS.TokenString))
			return TokenType.PARAMS;
		
		else if (tokenString.equals(VALUEOF.TokenString))
			return TokenType.VALUEOF;
		
		else if (tokenString.equals(MEMBEROF.TokenString))
			return TokenType.MEMBEROF;
		
		else
			throw new IllegalArgumentException();
	}
	
}
