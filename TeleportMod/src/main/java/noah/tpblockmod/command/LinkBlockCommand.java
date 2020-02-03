package noah.tpblockmod.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import noah.tpblockmod.ModInfo;
import noah.tpblockmod.ModMain;
import noah.tpblockmod.proxy.Place;
import noah.tpblockmod.proxy.ServerProcessor;

public class LinkBlockCommand implements ICommand {
	private List<String> aliases;
	
	public LinkBlockCommand() {
		this.aliases = new ArrayList<String>();
		this.aliases.add(ModInfo.L_B_NAME);
		this.aliases.add(ModInfo.L_B_USAGE.split(" ")[0]);
	}

	@Override
	public String getCommandName() {
		return ModInfo.L_B_NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + ModInfo.L_B_USAGE;
	}

	@Override
	public List<String> getCommandAliases() {
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0)
			sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.COMMON_ERR0));
		else {
			if (args[0].equals("list")) {
				sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.YELLOW + "Linked Block List"));
				
				if (ServerProcessor.LinkedBlockList.size() > 0) {
					for (int i=0; i<ServerProcessor.LinkedBlockList.size(); i++) {
						Place p = ServerProcessor.LinkedBlockList.get(i);
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + "Block Pos: " + p.getPlacePosition().toString() + " " + "Linked Place: " + p.getPlaceName()));
					}
				}
					
			}
			else
			{
				if (ModMain.isTPBlock(sender.getEntityWorld(), sender.getPosition())) {
					if (ModMain.searchPlaceByName(args[0]) != null) {
						ServerProcessor.LinkedBlockList.add(new Place(args[0], sender.getPosition()));
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.GREEN + ModInfo.L_B_SUCCESS));
						}
					else
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.L_B_ERR0));
					}
				else
					sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.R_P_ERR2));
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		//if (ModMain.isOp(sender.getName()))
		return true;
	//else
		//return false;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
	
	@Override
 	public int compareTo(ICommand o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
