package jw.minecraft.hastestick.commands;

import java.util.ArrayList;
import java.util.List;

import jw.minecraft.hastestick.HasteStickMod;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class AdminCommand implements ICommand {

	public static final String NAME = "Admin Command";
	public static final String USAGE = "/운영자 추가 [닉네임] 또는 /운영자 제거 [닉네임]";
	
	private static List<String> aliases = new ArrayList<String>();
	
	static {
		aliases.add(NAME);
		aliases.add(USAGE.substring(1).split(" ")[0]);
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return USAGE;
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 1 && args[0].equals("초기화")) {
			HasteStickMod.globalAdminData.adminSet.clear();
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA +  " 운영자 목록" + EnumChatFormatting.WHITE + "이 초기화 되었습니다."));
		}
		
		else if (args.length == 2) {
			
			if (args[0].equals("추가") && !args[1].equals("") || !args[1].equals(" ")) {
				
				if (HasteStickMod.globalAdminData.adminSet.add(args[1]))
					sender.addChatMessage(new ChatComponentText(args[1]+ " (이)가" + EnumChatFormatting.AQUA +  " 운영자" + EnumChatFormatting.WHITE + "로 등록되었습니다."));				
				else
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 플레이어는 이미 운영자로 등록되어 있습니다!"));
			}
			
			else if (args[0].equals("제거") && !args[1].equals("") || !args[1].equals(" ")) {
				
				if (HasteStickMod.globalAdminData.adminSet.remove(args[1]))
					sender.addChatMessage(new ChatComponentText(args[1] + " (이)가" + EnumChatFormatting.AQUA +  " 운영자" + EnumChatFormatting.WHITE + "에서 제거되었습니다."));				
				else
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 플레이어는 운영자가 아닙니다!"));
			}
			
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "올바른 명령어가 아닙니다."));
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "올바른 명령어가 아닙니다."));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return isOp(sender.getCommandSenderName());
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
	public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
