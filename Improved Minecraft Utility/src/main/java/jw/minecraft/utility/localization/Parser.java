package jw.minecraft.utility.localization;

import static jw.minecraft.utility.Common.translateToLocal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.util.StatCollector;

public abstract class Parser {
	
	public static enum ParseType {
		TRANSLATE("(#translate\\()([a-zA-Z0-9.\\[\\]\\(\\)]+)(\\))", 2),
		SUBCOMMAND("(#sub\\()([0-9]+)(\\))", 2);
		
		public final String Pattern;
		
		public final int Index;
		
		private ParseType(String pattern, int index) {
			Pattern = pattern;
			Index = index;
		}
	}
	
	public static String[] Arguments = null;
	
	public final static String parse(@Nonnull String target, @Nonnull ParseType... types) {
		if (types != null && types.length != 0) {
			for (int i=0; i < types.length; i++) {
				StringBuilder result = new StringBuilder();
				Matcher matcher = Pattern.compile(types[i].Pattern).matcher(target);
				int argindex = 0;
				
				while(matcher.find()) {
					result.append(" " + matcher.group(types[i].Index));
				}
				
				switch(types[i]) {
				case TRANSLATE:
					String check = result.toString();
					
					if (check != null && !check.equals("")) {
						String[] tmp = check.split(" ");
						
						for(int j=1; j < tmp.length; j++)
							target = target.replaceFirst(types[i].Pattern, translateToLocal(tmp[j]));
					}
					break;
					
				case SUBCOMMAND:
					String check2 = result.toString();
					
					if (check2 != null && !check2.equals("")) {
						String[] tmp2 = check2.split(" ");
						
						synchronized (Parser.class) {
							if (Arguments == null || Arguments.length - argindex < 1)
								throw new IllegalArgumentException("String[] Arguments");
							else if (Arguments[argindex] == null)
								throw new NullPointerException("Arguments[" + argindex + "]");
							
							for(int j=1; j < tmp2.length; j++)
								target = target.replaceFirst(types[i].Pattern, translateToLocal("imu." + Arguments[argindex++] + ".command.sub[" + tmp2[j] + "]"));
						}
					}
					break;
				}
			}
		}
		
		return target;
	}
}
