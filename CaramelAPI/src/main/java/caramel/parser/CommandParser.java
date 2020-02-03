package caramel.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import caramel.system.command.CommandRegistry;
import caramel.system.command.ISystemCommand;

public class CommandParser {
	
	public static final String PATTERN_VALUEOF = "(#valueof\\()([_a-zA-Z][_a-zA-Z0-9]+)(\\))";
	public static final String PATTERN_MEMBEROF = "(#memberof\\()([_a-zA-Z][_a-zA-Z0-9]+),([_a-zA-Z][_a-zA-Z0-9]+)(\\))";
	
	public static final String PATTERN_ID = "[_a-zA-Z][_a-zA-Z0-9]*";
	public static final String PATTERN_META_METHOD = "(#" + PATTERN_ID + "\\()((" + PATTERN_ID + ")";
	
//	public static final List<String> parse(String str) {
//		checkNotNull(str);
//		List<String>
//		
//		int index = str.indexOf(CommandRegistry.COMMAND_PREFIX);
//		if (index == -1)
//			return
//	}
	
	public static final List<ISystemCommand> parseToSystemCommand(@Nonnull List<String> cmdList) {
		checkNotNull(cmdList);
		
		List<ISystemCommand> list = Lists.newArrayList();
		
		for (String cmd : cmdList) {
			ISystemCommand syscmd = CommandRegistry.get(cmd);
			
			if (syscmd == null)
				throw new IllegalArgumentException();
			
			list.add(syscmd);
		}
		
		return list;
	}
	
}
