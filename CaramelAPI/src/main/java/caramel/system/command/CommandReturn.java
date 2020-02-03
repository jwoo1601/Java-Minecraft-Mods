package caramel.system.command;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import caramel.metamethod.MethodRegistry;
import caramel.system.SystemStack;

public class CommandReturn implements ISystemCommand<Object>{

	public static final String COMMAND_STRING = "return";

	@Override
	public String getCommandString() {
		return COMMAND_STRING;
	}

	@Override
	public void execute(@Nonnull SystemStack stack, @Deprecated Object... args) {
		checkNotNull(stack);
		
		switch (MethodRegistry.RETURN_CACHE.getReturnType()) {
		
		case VOID:
			return;
			
		case NULL:
			return;
			
		default:
			stack.push(MethodRegistry.RETURN_CACHE.getObject());
			return;
		}
	}
	
}
