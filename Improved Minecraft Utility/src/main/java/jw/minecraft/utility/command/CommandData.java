package jw.minecraft.utility.command;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jw.minecraft.utility.callback.ErrorCallback;
import jw.minecraft.utility.catchable.CauseCommandSender;
import jw.minecraft.utility.catchable.CommonError;
import jw.minecraft.utility.catchable.ErrorBase;
import jw.minecraft.utility.catchable.ICause;
import jw.minecraft.utility.permissions.IPermission;
import jw.minecraft.utility.permissions.PermissionManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandData {
	
	public final Object Instance;
	
	public final String CommandName;
	
	public final String CommandUsage;
	
	public final IPermission[] Permissions;
	
	public final IOutputHandler OutputHandler;
	
	public final Sub[] SubCommands;
	
	public final ErrorCallback Callback;
	
	public CommandData(@Nonnull Object instance, @Nonnull String commandName, @Nonnull String commandUsage, @Nonnull IPermission[] permissions,
			           @Nonnull Sub[] subCommands, @Nullable ErrorCallback callback, @Nullable IOutputHandler outputHandler) {
		Instance = checkNotNull(instance, "instance must not be null!");
		CommandName = checkNotNull(commandName, "commandName must not be null!");
		CommandUsage = checkNotNull(commandUsage, "commandUsage must not be null!");
		Permissions = checkNotNull(permissions, "permissions must not be null!");
		SubCommands = checkNotNull(subCommands, "instance must not be null!");
		Callback = callback;
		OutputHandler = outputHandler;
	}
	
	public final void execute(ICommandSender executor, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String msg = (args.length == 0? "" : args[0]);
		boolean isExecuted = false;
		
		for (Sub sub : SubCommands) {
			
			if (sub.Usage.equals(msg)) {
				
				if (args.length == 0)
					sub.execute(executor, args);
				
				else {
					int l = args.length - 1;
					String[] copy = new String[l];
					System.arraycopy(args, 1, copy, 0, l);
					sub.execute(executor, copy);
				}
				
				isExecuted = true;
				break;
			}
		}
		
		if (!isExecuted && Callback != null)
			Callback.invoke((ErrorBase) new CommonError.WrongCommandError().cause(new CauseCommandSender(executor)));
	}
	
	public boolean hasPermission(EntityPlayerMP executor) {
		boolean result = false;
		
		for (IPermission p : Permissions) {
			
			if (PermissionManager.hasPermission(p, executor)) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	
	public static class Sub {
		
		public final String Usage;
		
		public final int MinArgsLength;
		
		public final int MaxArgsLength;
		
		public final String Description;
		
		public final boolean AllowNonPlayerSender;
		
		private final IExecutable Target;
		
		private final ErrorCallback Callback;
		
		public Sub(@Nonnull IExecutable target, @Nonnull String usage, int minArgsLength, int maxArgsLength, @Nonnull String desc, 
				   boolean allowNonPlayerSender, @Nullable ErrorCallback callback) {
			checkNotNull(target, "target must not be null!");
			Usage = checkNotNull(usage, "usage must not be null!");
			Description = checkNotNull(desc, "description must not be null!");
			AllowNonPlayerSender = allowNonPlayerSender;
			checkArgument(minArgsLength >= 0, "minArgsLength must not be negative");
			checkArgument(maxArgsLength >= 0, "minArgsLength must not be negative");
			checkArgument(maxArgsLength >= minArgsLength, "maxArgsLength must be greater than or equal to minArgsLength");
			
			Target = target;
			MinArgsLength = minArgsLength;
			MaxArgsLength = maxArgsLength;
			Callback = callback;
		}
		
		public final void execute(ICommandSender executor, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if (!AllowNonPlayerSender && !(executor instanceof EntityPlayerMP))
				throwError((ErrorBase) new CommonError.NonPlayerError().cause(new CauseCommandSender(executor)));
			else if (args.length < MinArgsLength || args.length > MaxArgsLength)
				throwError((ErrorBase) new CommonError.WrongCommandError().cause(new CauseCommandSender(executor)));
			
			Target.execute(executor, args);
		}
		
		private void throwError(ErrorBase error) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			if (Callback != null)
				Callback.invoke(error);
		}		
	}
}
