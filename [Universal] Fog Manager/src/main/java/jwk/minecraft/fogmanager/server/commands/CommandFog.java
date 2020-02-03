package jwk.minecraft.fogmanager.server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jwk.minecraft.fogmanager.FogManager;
import jwk.minecraft.fogmanager.animation.AnimatedFog;
import jwk.minecraft.fogmanager.config.Configuration.FogConfig;
import jwk.minecraft.fogmanager.enumerations.EnumFogQuality;
import jwk.minecraft.fogmanager.enumerations.EnumFogRenderOption;
import jwk.minecraft.fogmanager.network.PacketFogAnimation;
import jwk.minecraft.fogmanager.network.PacketFogAnimation.Type;
import jwk.minecraft.fogmanager.utils.Color4f;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandFog implements ICommand {
	
	public static final String COMMAND_NAME = "Fog Command";
	public static final String COMMAND_USAGE = "/포그 예약 <플레이어> <색상코드> <시작 지점> <거리> <속도> <지속시간> <밀도> [품질] [옵션] 또는 /포그 제거 <플레이어> 또는 /포그 시작 <플레이어> 또는 /포그 종료 <플레이어> /포그 방귀 <플레이어>";
	
	public static final String SUBCOMMAND_RESERVE = "예약";
	public static final String SUBCOMMAND_DELETE = "제거";
	public static final String SUBCOMMAND_START = "시작";
	public static final String SUBCOMMAND_STOP = "중지";
	public static final String SUBCOMMAND_FART = "방귀";
	
	public static final AnimatedFog FART = new AnimatedFog(-1.f, 1200L, new FogConfig(true, EnumFogQuality.HIGH, EnumFogRenderOption.NICEST, 0.0f, new Color4f(135, 94, 0, 255), 10.0f, 5.0f));
	
	private static List<String> aliases = new ArrayList<String>();
	
	static {
		aliases.add(COMMAND_NAME);
		aliases.add(COMMAND_USAGE.substring(1).split(" ")[0]);
	}

	@Override
	public String getCommandName() { return COMMAND_NAME; }

	@Override
	public String getCommandUsage(ICommandSender sender) { return COMMAND_USAGE; }

	@Override
	public List getCommandAliases() { return aliases; }
	
	
	private static class Parser {
		
		public static final String PATTERN_COLOR_STRING = "^#([0-9a-fA-F]{2})([0-9a-fA-F]{2})([0-9a-fA-F]{2})";
		
		public static final String QUALITY_LOW = "-l";
		public static final String QUALITY_MEDIUM = "-m";
		public static final String QUALITY_HIGH = "-h";
		
		public static final String OPTION_DONT_CARE = "-dc";
		public static final String OPTION_FASTEST = "-f";
		public static final String OPTION_NICEST = "-n";
		
		
		public static EntityPlayerMP parsePlayer(String name) {
			List<EntityPlayerMP> list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
			
			EntityPlayerMP result = null;
			for (EntityPlayerMP player : list) {
				
				if (player.getDisplayName().equals(name)) {
					result = player;
					break;
				}
			}
			
			return result;
		}
		
		public static Color4f parseColor(String colorString) {
			Matcher matcher = Pattern.compile(PATTERN_COLOR_STRING).matcher(colorString);
			
			int[] colour = new int[3];
			if (matcher.find()) {
				
				for (int i=0; i < colour.length; i++)
					colour[i] = Integer.parseInt(matcher.group(i + 1), 16);
			}
			else
				return null;
			
			return new Color4f(colour[0], colour[1], colour[2], 1.0f);
		}
		
		public static float parseClampFloat(String floatString) {
			int tmp = Integer.valueOf(floatString);

			
			if (tmp <= 0 || tmp > 10000)
				return -1;
			else {
				int count = 5 - floatString.length();
				StringBuilder builder = new StringBuilder(floatString);
				
				for (int i=0; i < count; i++)
					builder = builder.insert(0, "0");
				
				return Float.valueOf(builder.insert(1, ".").toString());
			}
		}
		
		public static EnumFogQuality parseQuality(String qualityString) {
			
			if (qualityString.equals(QUALITY_LOW))
				return EnumFogQuality.LOW;
			
			else if (qualityString.equals(QUALITY_MEDIUM))
				return EnumFogQuality.MEDIUM;
			
			else if (qualityString.equals(QUALITY_HIGH))
				return EnumFogQuality.HIGH;
			
			else
				return null;
		}
		
		public static EnumFogRenderOption parseRenderOption(String optionString) {
			
			if (optionString.equals(OPTION_DONT_CARE))
				return EnumFogRenderOption.DONT_CARE;
			
			else if (optionString.equals(OPTION_FASTEST))
				return EnumFogRenderOption.FASTEST;
			
			else if (optionString.equals(OPTION_NICEST))
				return EnumFogRenderOption.NICEST;
			
			else
				return null;
		} 
	
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		String sub = args[0];
		
		if (sub.equals(SUBCOMMAND_RESERVE)) {
			
			if (args.length < 8)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "명령어 사용이 잘못되었습니다."));
			
			else
			{
				EntityPlayerMP targetPlayer = Parser.parsePlayer(args[1]);
			
				if (targetPlayer == null)
					sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 닉네임에 해당하는 플레이어가 존재하지 않습니다."));
			
				else {
						Color4f color = Parser.parseColor(args[2]);
					
						if (color == null)
							sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "올바른 색상코드가 아닙니다. 형식 : #<16진수 빨강><16진수 초록><16진수 파랑>"));
						
						else {
							float startDepth = Float.parseFloat(args[3]);
							
							if (startDepth < 0)
								sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "시작 지점 값이 올바르지 않습니다. (0과 같거나 커야합니다.)"));
							
							else {
								float distance = Float.parseFloat(args[4]);
								
								if (distance < 0)
									sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "거리 값이 올바르지 않습니다. (0과 같거나 커야합니다.)"));
								
								else {
									float speed = Float.parseFloat(args[5]);
									
									if (speed == 0)
										sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "속도 값이 올바르지 않습니다. (0이 아니어야 합니다.)"));
									
									else {
										long duration = Long.parseLong(args[6]);
									
										if (duration <= 0)
											sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "지속시간이 올바르지 않습니다. (0보다 커야합니다)"));
									
										else {											
											float density = Parser.parseClampFloat(args[7]);
											
											if (density == -1)
												sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "밀도 값이 올바르지 않습니다. (범위: 1~10000)"));
											
											else {
												EnumFogQuality quality = EnumFogQuality.HIGH;
												EnumFogRenderOption option = EnumFogRenderOption.DONT_CARE;
											
												if (args.length == 9)
													quality = Parser.parseQuality(args[8]);
											
												else if (args.length == 10) {
													quality = Parser.parseQuality(args[8]);
													option = Parser.parseRenderOption(args[9]);
												}
											
												if (quality == null)
													sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "품질이 올바르지 않습니다. (값: -l, -m, -h)"));
											
												else if (option == null)
													sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "옵션이 올바르지 않습니다. (값: -dc, -f, -n)"));
											
												else
													FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.ADD, new AnimatedFog(speed, duration, new FogConfig(true, quality, option, density, color, startDepth, distance))), targetPlayer);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		
		else if (sub.equals(SUBCOMMAND_DELETE) && args.length == 2) {
			EntityPlayerMP targetPlayer = Parser.parsePlayer(args[1]);
			
			if (targetPlayer == null)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 닉네임에 해당하는 플레이어가 존재하지 않습니다."));
		
			else
				FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.DELETE, null), targetPlayer);
		}
		
		else if (sub.equals(SUBCOMMAND_START) && args.length == 2) {
			EntityPlayerMP targetPlayer = Parser.parsePlayer(args[1]);
			
			if (targetPlayer == null)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 닉네임에 해당하는 플레이어가 존재하지 않습니다."));
		
			else
				FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.START, null), targetPlayer);
		}
		
		else if (sub.equals(SUBCOMMAND_STOP) && args.length == 2) {
			EntityPlayerMP targetPlayer = Parser.parsePlayer(args[1]);
			
			if (targetPlayer == null)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 닉네임에 해당하는 플레이어가 존재하지 않습니다."));
		
			else
				FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.STOP, null), targetPlayer);
		}
		
		else if (sub.equals(SUBCOMMAND_FART) && args.length == 2) {
			EntityPlayerMP targetPlayer = Parser.parsePlayer(args[1]);
			
			if (targetPlayer == null)
				sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "해당 닉네임에 해당하는 플레이어가 존재하지 않습니다."));
		
			else {
				FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.ADD, FART), targetPlayer);
				FogManager.NET_HANDLER.sendTo(new PacketFogAnimation(Type.START, null),  targetPlayer);
			}
		}
		
		else
			sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "명령어 사용이 잘못되었습니다."));
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true; //isOp(sender.getCommandSenderName());
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
	public List addTabCompletionOptions(ICommandSender sender, String[] args) {	return null; }

	@Override
	public boolean isUsernameIndex(String[] args, int index) { return false; }
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof ICommand) {
			ICommand target = (ICommand) o;
			
			return COMMAND_NAME.compareTo(((ICommand) o).getCommandName());
		}
		
		return 0;
	}
}
