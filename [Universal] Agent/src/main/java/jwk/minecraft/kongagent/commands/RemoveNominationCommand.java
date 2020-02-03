package jwk.minecraft.kongagent.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import jwk.minecraft.kongagent.KongAgent;
import jwk.minecraft.kongagent.enumerations.NominationType;
import jwk.minecraft.kongagent.events.PlayerEventHandler;
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
		if (!KongAgent.globalAdminData.adminSet.contains(sender.getCommandSenderName())) {
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 명령어를 사용할 수 있는 권한이 없습니다."));
			return;
		}
		
		if (args.length == 0) {
			String name = sender.getCommandSenderName();
			
			if (PlayerEventHandler.nominatedPlayerMap.containsKey(name)) {
				EntityPlayer player = PlayerEventHandler.getPlayerByName(name);
				player.removePotionEffect(Potion.digSpeed.id);
				PlayerEventHandler.nominatedPlayerMap.remove(name);
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "당신" + EnumChatFormatting.WHITE + "의 " + EnumChatFormatting.GOLD + "효과" + EnumChatFormatting.RESET + "가 제거되었습니다."));
			}
			
			else
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "당신은 지목되어있지 않습니다."));
		}
		
		else if (args.length == 1) {
			String name = args[0];
			
			if (name.equals("@a")) {
				
				if (PlayerEventHandler.nominatedPlayerMap.isEmpty()) {
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "지목되어있는 플레이어가 없습니다."));
					return;
				}
				
				for (Entry<String, NominationType> entry : PlayerEventHandler.nominatedPlayerMap.entrySet()) {
					
					if (PlayerEventHandler.nominatedPlayerMap.containsKey(entry.getKey())) {
						EntityPlayer player = PlayerEventHandler.getPlayerByName(entry.getKey());
						
						if (player.isPotionActive(Potion.digSpeed))
							player.removePotionEffect(Potion.digSpeed.id);
						
						PlayerEventHandler.nominatedPlayerMap.remove(entry.getKey());
					}
				}
				
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "모든 플레이어들" + EnumChatFormatting.RESET + "의 " + EnumChatFormatting.GOLD + "효과" + EnumChatFormatting.RESET + "가 제거되었습니다."));
			}
			
			else {
				
				if (PlayerEventHandler.nominatedPlayerMap.containsKey(name)) {
					EntityPlayer player = PlayerEventHandler.getPlayerByName(name);
					player.removePotionEffect(Potion.digSpeed.id);
					PlayerEventHandler.nominatedPlayerMap.remove(name);
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + name + EnumChatFormatting.RESET + " 님의 " + EnumChatFormatting.GOLD + "효과" + EnumChatFormatting.RESET + "가 제거되었습니다."));
				}
			
				else
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "대상이 지목되어있지 않습니다."));
			}
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "올바른 명령어가 아닙니다."));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return KongAgent.globalAdminData.adminSet.contains(sender.getCommandSenderName());
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
		if (o instanceof ICommand) {
			ICommand cmd = (ICommand) o;
			
			return cmd.getCommandName().compareTo(NAME);
		}
		
		return 0;
	}
	
}
