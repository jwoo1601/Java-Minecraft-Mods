package jw.minecraft.utility.catchable;

import net.minecraft.command.ICommandSender;

public class CauseCommandSender implements ICause<ICommandSender> {
	
	private final ICommandSender Cause;
	
	public CauseCommandSender(ICommandSender cause) {
		Cause = cause;
	}

	@Override
	public ICommandSender getCause() {
		return Cause;
	}

}
