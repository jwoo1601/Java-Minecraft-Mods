package caramel.system.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

public class CommandRegistry {

private static final Map<String, ISystemCommand> map = new ConcurrentHashMap<String, ISystemCommand>();
	
	public static final String PATTERN_ID = "^[_a-zA-Z][_a-zA-Z0-9]*$";
	
	public static final String COMMAND_PREFIX = "#";
	
	public static final boolean register(@Nonnull String id, @Nonnull ISystemCommand command) {
		checkNotNull(id);
		checkNotNull(command);
		
		if (!Pattern.matches(PATTERN_ID, id))
			return false;
		
		String cid = COMMAND_PREFIX + id;
		if (map.containsKey(cid))
			return false;
		
		map.put(cid, command);
		return true;
	}
	
	public static final boolean unregister(@Nonnull String id) {
		checkNotNull(id);
		
		String cid = COMMAND_PREFIX + id;		
		if (map.containsKey(cid)) {
			map.remove(cid);
			return true;
		}
		
		return false;
	}
	
	public static final ISystemCommand get(@Nonnull String id) {
		return map.get(COMMAND_PREFIX + checkNotNull(id));
	}
	
	public static final boolean isEmpty() { return map.isEmpty(); }
	
	public static final boolean contains(@Nonnull String id) { return map.containsKey(COMMAND_PREFIX + checkNotNull(id)); }
	
	public static int size() { return map.size(); }
	
	
	private static void registerCommands() {
		register(CommandPush.COMMAND_STRING, new CommandPush());
		register(CommandPop.COMMAND_STRING, new CommandPop());
		register(CommandCall.COMMAND_STRING, new CommandCall());
		register(CommandReturn.COMMAND_STRING, new CommandReturn());
	}
	
	static {
		registerCommands();
	}
	
}
