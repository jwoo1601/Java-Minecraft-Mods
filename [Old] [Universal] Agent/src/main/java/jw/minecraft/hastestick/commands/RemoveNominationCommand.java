package jw.minecraft.hastestick.commands;

import java.util.ArrayList;
import java.util.List;

import jw.minecraft.hastestick.HasteStickMod;
import jw.minecraft.hastestick.events.PlayerEventHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class RemoveNominationCommand implements ICommand {

	public static final String NAME = "Remove Nomination Command";
	public static final String USAGE = "/효과제거 [닉네임]";
	
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
		if (args.length == 1 && !args[0].equals("")) {
			
			if (PlayerEventHandler.nominatedPlayerMap.containsKey(args[0])) {
				EntityPlayer player = PlayerEventHandler.getPlayerByName(args[0]);
				player.removePotionEffect(Potion.digSpeed.id);
				PlayerEventHandler.nominatedPlayerMap.remove(args[0]);
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + args[0] + EnumChatFormatting.RESET + " 님의 " + EnumChatFormatting.GOLD + "효과" + EnumChatFormatting.RESET + "가 제거되었습니다."));
			}
			
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "대상이 지목되어있지 않습니다."));
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "올바른 명령어가 아닙니다."));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return HasteStickMod.globalAdminData.adminSet.contains(sender.getCommandSenderName());
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
