package jwk.minecraft.kongagent.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import jwk.minecraft.kongagent.KongAgent;
import jwk.minecraft.kongagent.enumerations.NominationType;
import jwk.minecraft.kongagent.events.PlayerEventHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SignCommand implements ICommand {
	
	public static final String COMMAND_NAME = "Sign Command";
	public static final String COMMAND_USAGE = "/표식";
	
	private static final List<String> ALIASES = new ArrayList<String>();
	
	static {
		ALIASES.add(COMMAND_NAME);
		ALIASES.add(COMMAND_USAGE.substring(1));
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return COMMAND_USAGE;
	}

	@Override
	public List getCommandAliases() {
		return ALIASES;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!KongAgent.globalAdminData.adminSet.contains(sender.getCommandSenderName())) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 명령어를 사용할 수 있는 권한이 없습니다."));
			return;
		}
		
		if (args.length == 0) {
			
			if (PlayerEventHandler.nominatedPlayerMap.isEmpty())
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.RESET + "이 " + EnumChatFormatting.GOLD + "있는 " + EnumChatFormatting.RESET + "플레이어가 없습니다."));
			
			else {
				StringBuilder builder = new StringBuilder(EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.RESET + "이 " + EnumChatFormatting.GOLD + "있는 " + EnumChatFormatting.RESET + "플레이어: ");
			
				int i = 0;
				for (Entry<String, NominationType> entry : PlayerEventHandler.nominatedPlayerMap.entrySet()) {
				
					if (i != 0)
					builder.append(", ");
				
					if (entry.getValue() == NominationType.SPY)
						builder.append(EnumChatFormatting.RED);
			
					builder.append(entry.getKey() + EnumChatFormatting.WHITE);
					i++;
					}
				
				sender.addChatMessage(new ChatComponentText(builder.toString()));
			}
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "잘못된 명령어입니다. (사용법: " + COMMAND_USAGE + ")"));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return KongAgent.globalAdminData.adminSet.contains(sender.getCommandSenderName());
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) { return null; }

	@Override
	public boolean isUsernameIndex(String[] args, int index) { return false; }
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof ICommand) {
			ICommand cmd = (ICommand) o;
			
			return cmd.getCommandName().compareTo(COMMAND_NAME);
		}
		
		return 0;
	}
}
