package caramel.system.command;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import caramel.metamethod.MethodRegistry;
import caramel.system.SystemStack;
import caramel.system.command.exception.CommandException;

public class CommandPush implements ISystemCommand<Object> {
	
	public static final String COMMAND_STRING = "push";

	@Override
	public String getCommandString() {
		return COMMAND_STRING;
	}

	@Override
	public void execute(@Nonnull SystemStack stack, Object... args) {
		checkNotNull(stack);
		checkNotNull(args);
		
		if (args.length == 0)
			throw new IllegalArgumentException("The length of args must be greater than 0");
		
		for (int i=0; i < args.length; i++)
			stack.push(args[i]);
	}

}
