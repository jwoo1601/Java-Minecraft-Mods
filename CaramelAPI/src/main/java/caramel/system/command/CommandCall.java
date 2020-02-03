package caramel.system.command;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import caramel.metamethod.MethodRegistry;
import caramel.system.SystemStack;
import caramel.system.command.exception.CommandException;

public class CommandCall implements ISystemCommand<String> {
	
	public static final String COMMAND_STRING = "call";

	@Override
	public String getCommandString() {
		return COMMAND_STRING;
	}

	@Override
	public void execute(@Nonnull SystemStack stack, @Nonnull String... args) {
		checkNotNull(stack);
		checkNotNull(args);
		
		if (args.length != 1)
			throw new IllegalArgumentException("The length of args must be equal to 1");
		
		MethodRegistry.get(args[0]).call(stack);
	}

}
