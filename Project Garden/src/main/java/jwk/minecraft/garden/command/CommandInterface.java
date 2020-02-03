package jwk.minecraft.garden.command;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public abstract class CommandInterface implements ICommand {
	
	public static final ChatComponentText ERR_WRONG_COMMAND = new ChatComponentText(EnumChatFormatting.RED + "명령어 사용이 잘못되었습니다.");
	
	protected final boolean DefaultPermission;
	protected final String CommandName;
	protected final String CommandUsage;
	private final List<String> Aliases = Lists.newArrayList();
	
	protected CommandInterface(@Nonnull String name, @Nonnull String usage) {
		this(name, usage, false);
	}
	
	protected CommandInterface(@Nonnull String name, @Nonnull String usage, boolean defaultPermission) {
		CommandName = checkNotNull(name);
		CommandUsage = checkNotNull(usage);
		DefaultPermission = defaultPermission;
		
		Aliases.add(CommandName);
		Aliases.add(CommandUsage.substring(1).split(" ")[0]);
	}

	@Override
	public final String getCommandName() { return CommandName; }

	@Override
	public final String getCommandUsage(ICommandSender sender) { return CommandUsage; }

	@Override
	public final List getCommandAliases() { return Aliases; }

	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);

	@Override
	public final boolean canCommandSenderUseCommand(ICommandSender sender) {
		return DefaultPermission? true : isOp(sender.getCommandSenderName());
	}
	
	public static final boolean isOp(String username) {
		String[] userlist = MinecraftServer.getServer().getConfigurationManager().func_152606_n();
		
		for (String name : userlist) {
			if (name.equals(username))
				return true;
		}
		
		return false;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) { return null; }

	@Override
	public boolean isUsernameIndex(String[] args, int index) { return false; }

	@Override
	public int compareTo(Object obj) {
		
		if (obj instanceof ICommand) {
			ICommand cmd = (ICommand) obj;
			
			return CommandName.compareTo(cmd.getCommandName());
		}
		
		return 0;
	}
}
