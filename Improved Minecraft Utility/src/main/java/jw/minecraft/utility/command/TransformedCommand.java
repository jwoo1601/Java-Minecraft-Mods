package jw.minecraft.utility.command;

import static com.google.common.base.Preconditions.checkNotNull;
import static jw.minecraft.utility.LogHelper.error;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import jw.minecraft.utility.catchable.CommonError;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class TransformedCommand implements ICommand {
	
	private final CommandData internal;
	
	private final List<String> aliases;
	
	public TransformedCommand(@Nonnull CommandData base) {
		internal = checkNotNull(base, "base must not be null!");
		aliases = new ArrayList<String>();
		aliases.add(internal.CommandName);
		aliases.add(internal.CommandUsage.substring(1).split(" ")[0]);
	}

	@Override
	public int compareTo(Object target) {
		if (target instanceof ICommand)
			return ((ICommand)target).getCommandName().compareTo(getCommandName());
		else
			throw new IllegalArgumentException("target is not a instance of ICommand");
	}

	@Override
	public String getCommandName() {
		return internal.CommandName;
	}

	@Override
	public String getCommandUsage(ICommandSender executor) {
		return internal.CommandUsage;
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}
	
	private void internalExecute(ICommandSender executor, String[] args) {
		try { internal.execute(executor, args);	}
		catch (Exception e) { e.printStackTrace(); }
	}

	@Override
	public void processCommand(ICommandSender executor, String[] args) {
		if (executor instanceof EntityPlayerMP) {
			if (internal.hasPermission((EntityPlayerMP) executor))
				internalExecute(executor, args);
			else
				executor.addChatMessage(CommonError.NoPermission.getChatComponent());
		}
		
		else
			internalExecute(executor, args);
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender executor) {
		if (executor instanceof EntityPlayerMP)
			return internal.hasPermission((EntityPlayerMP) executor);
			
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender executor, String[] args) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	
}
