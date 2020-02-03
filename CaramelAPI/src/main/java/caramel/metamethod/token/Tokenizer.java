package caramel.metamethod.token;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import caramel.parser.exception.IllegalFormatException;

public class Tokenizer {
	
	public static final String TOKEN_PREFIX = "#";
	public static final String TOKEN_PARAM_DELIMITER = ":";

	public static List<Token> tokenize(@Nonnull String unformatted) {
		unformatted = checkNotNull(unformatted);		
		int idx = unformatted.indexOf(TOKEN_PREFIX);
		
		if (idx == -1)
			throw new IllegalFormatException();
		
		String[] strs = unformatted.split(TOKEN_PREFIX.substring(idx));
		List<Token> tokenList = Lists.newArrayList();
		
		for (int i=strs.length; i > 0; i--) {
			TokenType type = TokenType.fromString(strs[i]);
			Token current = new Token(type, null);
			
			if (type == TokenType.PARAMS) {
				String[] params = strs[i].split(TOKEN_PARAM_DELIMITER);
				List<String> dataList = Lists.newArrayList();
				
				for (int j=1; j < params.length; j++)
					dataList.add(params[j]);
				
				current.setData(dataList);
			}
			
			tokenList.add(current);
		}
		
		return tokenList;
	}
	
}
