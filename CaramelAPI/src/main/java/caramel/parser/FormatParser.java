package caramel.parser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import caramel.parser.exception.ParsingException;
import caramel.parser.exception.NoSuchFieldException;

public class FormatParser {

	public static final String PATTERN_VALUEOF = "(__valueof\\()([_a-zA-Z][_a-zA-Z0-9]+)(\\))";
	public static final String PATTERN_MEMBEROF = "(__valueof\\()([_a-zA-Z][_a-zA-Z0-9]+),([_a-zA-Z][_a-zA-Z0-9]+)(\\))";
	
	public static final String TOKEN_SEPERATOR = ":";
	
	private static final String TOKEN_PREFIX = "#";
	private static final String TOKEN_VALUEOF = "VOF";
	private static final String TOKEN_MEMBEROF = "MOF";
	
	public static final Object tryParseValueOf(Object obj, List<Field> fields, String str) {
		Matcher matcher = Pattern.compile(PATTERN_VALUEOF).matcher(str);
		String fieldName;
		
		if (matcher.find())
			fieldName = matcher.group(2);
		else
			return str;
		
		for (Field f : fields) {
			f.setAccessible(true);
		
			if (f.getName().equals(fieldName)) {
				
				try { return f.get(obj); }
				catch (Exception e) { throw new ParsingException(e); }
			}
			
		}
		
		throw new NoSuchFieldException(fieldName);
	}
	
	
	
}
