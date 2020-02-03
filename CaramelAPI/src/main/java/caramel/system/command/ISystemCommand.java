package caramel.system.command;

import javax.annotation.Nonnull;

import caramel.system.SystemStack;

public interface ISystemCommand<T> {

	String getCommandString();
	
	void execute(@Nonnull SystemStack stack, T...args);	
	
}
