package jwk.minecraft.garden.command;

import static net.minecraft.util.EnumChatFormatting.*;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandTeamCurrency extends CommandInterface {
	
	public static final String SUBCOMMAND_BALANCE = "잔고";
	public static final String SUBCOMMAND_INCREASE = "증감";
	public static final String SUBCOMMAND_CHANGE = "변경";

	public CommandTeamCurrency() {
		super("돈 명령어", "/통장 잔고 <팀이름> 또는 /통장 증감 <팀이름> +/-<값> 또는 /통장 변경 <팀이름> <값>");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if (args.length == 0) {
			sender.addChatMessage(super.ERR_WRONG_COMMAND);
			return;
		}
		
		String sub = args[0];
		int length = args.length - 1;
		
		if (sub.equals(SUBCOMMAND_BALANCE) && length == 1) {
			String teamName = args[1];
			ITeam team = TeamRegistry.get(teamName);
			
			if (team == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			long balance = CurrencyYD.INSTANCE.getManager().get(team);
			sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 의 잔고는 " + GOLD + balance + " YD " + WHITE + "입니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_INCREASE) && length == 2) {
			String teamName = args[1];
			ITeam team = TeamRegistry.get(teamName);
			
			if (team == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			long value = Long.parseLong(args[2]);
			long result = 0L;
			
			if (value == 0) {
				sender.addChatMessage(ProjectGarden.toFormatted(AQUA + "증감시킬 금액 " + WHITE + "은 " + RED + "0" + WHITE + " 보다 커야 합니다."));
				return;
			}
			else if (value < 0)
				result = CurrencyYD.INSTANCE.getManager().decrease(team, Math.abs(value));
			else if (value > 0)
				result = CurrencyYD.INSTANCE.getManager().increase(team, value);	
			
			sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 의 잔고가 " + YELLOW + value + WHITE + " 만큼 증감되어 " + GOLD + result + " YD " + WHITE + "(으)로 변경되었습니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_CHANGE) && length == 2) {
			String teamName = args[1];
			ITeam team = TeamRegistry.get(teamName);
			
			if (team == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			long value = Long.parseLong(args[2]);
			
			if (value < 0) {
				sender.addChatMessage(ProjectGarden.toFormatted(AQUA + "증감시킬 금액 " + WHITE + "은 " + RED + "0" + WHITE + " 이상이어야 합니다."));
				return;
			}	
			
			long prev = CurrencyYD.INSTANCE.getManager().set(team, value);
			sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 의 잔고가 " + YELLOW + prev + WHITE + " 에서 " + GOLD + value + " YD " + WHITE + "(으)로 변경되었습니다."));
		}
		
		else
			sender.addChatMessage(super.ERR_WRONG_COMMAND);
	}

}
