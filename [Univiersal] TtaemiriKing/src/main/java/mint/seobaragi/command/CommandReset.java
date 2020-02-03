package mint.seobaragi.command;

import static library.Reference.getPlayerByName;
import static library.Reference.getStatFromPlayer;

import java.util.List;

import com.google.common.collect.Lists;

import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandReset implements ICommand {
	
	public static final String COMMAND_NAME = "Reset Command for TtaemiriKing";
	public static final String COMMAND_USAGE = "/초기화 <더러움|수분|더러움게이지|때밀이|전체> [닉네임]";
	private static List<String> aliases = Lists.newArrayList();
	
	private static final String SUBCOMMAND_DIRT_LEVEL = "더러움";
	private static final String SUBCOMMAND_WATER_LEVEL = "수분";
	private static final String SUBCOMMAND_DIRT_GAUGE = "더러움게이지";
	private static final String SUBCOMMAND_CAN_CLEAN = "때밀이";
	private static final String SUBCOMMAND_ALL = "전체";
	
	private static final ChatComponentText ERR_CMD_USAGE = new ChatComponentText(EnumChatFormatting.RED + "명령어 사용이 올바르지 않습니다.");
	private static final ChatComponentText ERR_INVALID_PLAYER = new ChatComponentText(EnumChatFormatting.RED + "해당 플레이어가 존재하지 않습니다.");
	private static final ChatComponentText INF_CMD_PROCESS = new ChatComponentText("해당 항목이 정상적으로 " + EnumChatFormatting.AQUA + "초기화 " + EnumChatFormatting.WHITE + "되었습니다.");
	
	static {
		aliases.add(COMMAND_NAME);
		aliases.add(COMMAND_USAGE.substring(1).split(" ")[0]);
	}

	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return COMMAND_USAGE;
	}

	@Override
	public List getCommandAliases() {
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length < 1 || args.length > 2)
			sender.addChatMessage(ERR_CMD_USAGE);
		
		else {
			boolean shouldSendToAll = false;
			EntityPlayer player = null;
			
			if (args.length == 1)
				player = CommandBase.getCommandSenderAsPlayer(sender);
			
			else {
				String name = args[1];				
				
				 if (name.equals(""))
					player = CommandBase.getCommandSenderAsPlayer(sender);
				
				 else if(name.equals("@a"))
					shouldSendToAll = true;
						
				else
					player = getPlayerByName(name);
			}
			
			if (!shouldSendToAll && player == null) {
				sender.addChatMessage(ERR_INVALID_PLAYER);
				return;
			}
			
			
			String sub = args[0];
			if (sub.equals(SUBCOMMAND_DIRT_LEVEL)) {
				
				if (shouldSendToAll) {
					List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
					
					for (EntityPlayer p : list)
						getStatFromPlayer(p).resetDirtLevelAndSendPacket();
				}
				
				else
					getStatFromPlayer(player).resetDirtLevelAndSendPacket();
				
				sender.addChatMessage(INF_CMD_PROCESS);
			}
			
			else if (sub.equals(SUBCOMMAND_WATER_LEVEL)) {
				
				if (shouldSendToAll) {
					List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
					
					for (EntityPlayer p : list)
						getStatFromPlayer(p).resetWaterLevelAndSendPacket();
				}
				
				else
					getStatFromPlayer(player).resetWaterLevelAndSendPacket();
				
				sender.addChatMessage(INF_CMD_PROCESS);
			}
			
			else if (sub.equals(SUBCOMMAND_DIRT_GAUGE)) {
				
				if (shouldSendToAll) {
					List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
					
					for (EntityPlayer p : list)
						getStatFromPlayer(p).resetDirtGaugeAndSendPacket();
				}
				
				else
					getStatFromPlayer(player).resetDirtGaugeAndSendPacket();
				
				sender.addChatMessage(INF_CMD_PROCESS);
			}
			
			else if (sub.equals(SUBCOMMAND_CAN_CLEAN)) {
				
				if (shouldSendToAll) {
					List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
					
					for (EntityPlayer p : list)
						getStatFromPlayer(p).resetCanCleanAndSendPacket();
				}
				
				else
					getStatFromPlayer(player).resetCanCleanAndSendPacket();
				
				sender.addChatMessage(INF_CMD_PROCESS);
			}
			
			else if (sub.equals(SUBCOMMAND_ALL)) {
				
				if (shouldSendToAll) {
					List<EntityPlayer> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
					
					for (EntityPlayer p : list)
						getStatFromPlayer(p).resetAllAndSendPacket();

				}
			
				else
					getStatFromPlayer(player).resetAllAndSendPacket();
				
				sender.addChatMessage(INF_CMD_PROCESS);
			}
			
			else
				sender.addChatMessage(ERR_CMD_USAGE);
		}
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
	public List addTabCompletionOptions(ICommandSender sender, String[] args) { return null; }

	@Override
	public boolean isUsernameIndex(String[] args, int idx) { return false;	}
	
	@Override
	public int compareTo(Object obj) {
		
		if (obj != null && obj instanceof ICommand) {
			ICommand cmd = (ICommand) obj;
			
			return COMMAND_NAME.compareTo(cmd.getCommandName());
		}
		
		return -2;
	}


}
