package noah.tpblockmod.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noah.tpblockmod.ModInfo;
import noah.tpblockmod.ModMain;
import noah.tpblockmod.block.BlockTeleport;
import noah.tpblockmod.proxy.Place;
import noah.tpblockmod.proxy.ServerProcessor;

public class RegisterPlaceCommand implements ICommand {
	private List<String> aliases;
	
	public RegisterPlaceCommand() {
		this.aliases = new ArrayList<String>();
		this.aliases.add(ModInfo.R_P_NAME);
		this.aliases.add(ModInfo.R_P_USAGE.split(" ")[0]);
	}

	@Override
	public String getCommandName() {
		return ModInfo.R_P_NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + ModInfo.R_P_USAGE;
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
				sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.YELLOW + "Registered Place List"));
				
				if (ServerProcessor.PlaceList.size() > 0) {
					for (int i=0; i<ServerProcessor.PlaceList.size(); i++) {
						Place p = ServerProcessor.PlaceList.get(i);
						
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + "Registered Place: " + p.getPlaceName() + " " + "Target Place Pos: " + p.getPlacePosition().toString()));
						System.out.println(Minecraft.getMinecraft().theWorld.isRemote);
						
					}					
				}
			}
			
			else {
				//if (ModMain.isOp(sender.getName())) {
				if (!ServerProcessor.isAlreadyExist(args[0])){
					
					if(ModMain.isTPBlock(sender.getEntityWorld(), sender.getPosition())) {
						ServerProcessor.PlaceList.add(new Place(args[0], new BlockPos(sender.getPosition())));
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.GREEN + ModInfo.R_P_SUCCESS));
						}
					else
						sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.R_P_ERR2));
					}				
				else
					sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.R_P_ERR1));
				//}
				//else
				//	sender.addChatMessage(new ChatComponentText(ModInfo.MOD_INFO_PREFIX + EnumChatFormatting.RED + ModInfo.R_P_ERR0));			
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
