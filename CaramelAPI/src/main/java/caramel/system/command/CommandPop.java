package caramel.system.command;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import caramel.metamethod.MethodRegistry;
import caramel.system.SystemStack;
import caramel.system.command.exception.CommandException;

public class CommandPop implements ISystemCommand<Integer> {
	
	public static final String COMMAND_STRING = "pop";

	@Override
	public String getCommandString() {
		return COMMAND_STRING;
	}

	@Override
	public void execute(@Nonnull SystemStack stack, Integer...args) {
		checkNotNull(stack);
		checkNotNull(args);
		
		if (args.length != 1)
			throw new IllegalArgumentException("The length of args must be equal to 0");
		
		for (int i=0; i < args[0]; i++)
			stack.pop();
	}

}
