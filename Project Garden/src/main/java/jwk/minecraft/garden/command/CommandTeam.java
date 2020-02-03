package jwk.minecraft.garden.command;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.Iterator;
import java.util.List;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.SimpleTeam;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;

public class CommandTeam extends CommandInterface {
	
	public static final String SUBCOMMAND_ADD = "추가";
	public static final String SUBCOMMAND_REMOVE = "제거";
	public static final String SUBCOMMAND_LEADER = "대표";
	public static final String SUBCOMMAND_JOIN = "가입";
	public static final String SUBCOMMAND_LEAVE = "탈퇴";
	public static final String SUBCOMMAND_LIST = "목록";
	public static final String SUBCOMMAND_MEMBER = "멤버";

	public CommandTeam() {
		super("팀 관리 명령어", "/팀 추가 <팀이름> 또는 /팀 제거 <팀이름> 또는 /팀 가입 <팀이름> <닉네임> 또는 /팀 탈퇴 <닉네임> 또는 /팀 대표 <닉네임> /팀 목록 또는 /팀 멤버 <팀이름>");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if (args.length == 0) {
			sender.addChatMessage(super.ERR_WRONG_COMMAND);
			return;
		}
		
		String sub = args[0];
		int length = args.length - 1;
		
		if (sub.equals(SUBCOMMAND_ADD) && length == 1) {
			String teamName = args[1];
			
			if (TeamRegistry.register(new SimpleTeam(teamName)))
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 성공적으로 " + GOLD + "추가" + WHITE + "되었습니다."));
			else
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) " + RED +"이미" + WHITE + " 존재합니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_REMOVE) && length == 1) {
			String teamName = args[1];
			
			if (TeamRegistry.unregister(teamName))
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 성공적으로 " + GOLD + "제거" + WHITE + "되었습니다."));
			else
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_JOIN) && length == 2) {
			String teamName = args[1];
			String playerName = args[2];
			
			if (!TeamRegistry.contains(teamName)) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			EntityPlayer player = PlayerUtil.getPlayerFromName(playerName);
			
			if (player == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) 현재 접속중이 아닙니다."));
				return;
			}
			
			ITeam currentTeam = TeamRegistry.getTeamOf(player);
			
			if (currentTeam != null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) " + RED + "이미 " + AQUA + currentTeam.getTeamName() + WHITE + " 팀에 소속되어 있습니다."));
				return;
			}
			
			ITeam team = TeamRegistry.get(teamName);
			
			if (team.getManager().isDataExist(player)) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) " + RED + "이미" + WHITE + " 해당 팀에 소속되어 있습니다."));
				return;
			}
			
			team.getManager().register(player);
			sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 이(가) 팀 " + AQUA + team.getTeamName() + WHITE + " 에 가입되었습니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_LEAVE) && length == 1) {
			String playerName = args[1];
			EntityPlayer player = PlayerUtil.getPlayerFromName(playerName);
			
			if (player == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) 현재 접속중이 아닙니다."));
				return;
			}
			
			ITeam currentTeam = TeamRegistry.getTeamOf(player);
			
			if (currentTeam == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) " + AQUA + "팀" + WHITE + " 에 소속되어 있지 않습니다."));
				return;
			}
			
			currentTeam.getManager().unregister(player);
			sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 이(가) 팀 " + AQUA + currentTeam.getTeamName() + WHITE + " 에서 탈퇴되었습니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_LEADER) && length == 1) {
			String playerName = args[1];
			EntityPlayer player = PlayerUtil.getPlayerFromName(playerName);
			
			if (player == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) 현재 접속중이 아닙니다."));
				return;
			}
			
			ITeam currentTeam = TeamRegistry.getTeamOf(player);
			
			if (currentTeam == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) " + AQUA + "팀" + WHITE + " 에 소속되어 있지 않습니다."));
				return;
			}
			
			if (currentTeam.getManager().isLeader(player)) {
				sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 은(는) " + RED + "이미" + WHITE + " 해당 팀의 대표입니다."));
				return;
			}
			
			currentTeam.getManager().setLeader(player);
			sender.addChatMessage(ProjectGarden.toFormatted("플레이어 " + GOLD + playerName + WHITE + " 이(가) 팀 " + AQUA + currentTeam.getTeamName() + WHITE + " 의 대표로 임명되었습니다."));
		}
		
		else if (sub.equals(SUBCOMMAND_LIST) && length == 0) {
			
			if (TeamRegistry.isEmpty()) {
				sender.addChatMessage(ProjectGarden.toFormatted("생성된 " + AQUA + "팀" + WHITE + "이 없습니다"));
				return;
			}
			
			StringBuilder builder = new StringBuilder(AQUA + "팀" + WHITE + " 목록: ");
			Iterator<ITeam> iter = TeamRegistry.iterator();
			int k = 0;
			
			while (iter.hasNext()) {
				
				if (k == 0) {
					builder.append(iter.next().getTeamName());
					k = 1;
				}
				
				else
					builder.append(", " + iter.next().getTeamName());
			}
			
			sender.addChatMessage(ProjectGarden.toFormatted(builder.toString()));
		}
		
		else if (sub.equals(SUBCOMMAND_MEMBER) && length  == 1) {
			String teamName = args[1];
			ITeam team = TeamRegistry.get(teamName);
			
			if (team == null) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			List<EntityPlayerMP> onlineMembers = team.getManager().getOnlineMembers();
			
			if (onlineMembers.isEmpty()) {
				sender.addChatMessage(ProjectGarden.toFormatted("현재 " + AQUA + "접속중" + WHITE + "인  " + GOLD + "멤버" + WHITE + "가 없습니다"));
				return;
			}
			
			StringBuilder builder = new StringBuilder("팀 " + AQUA + teamName + WHITE + " 의 접속중인 " + GOLD + "멤버" + WHITE + " 목록: ");
			
			for (int l=0; l < onlineMembers.size(); l++) {
				EntityPlayerMP player = onlineMembers.get(l);
				
				if (l == 0) {
					
					if (team.getManager().isLeader(player))
						builder.append(GOLD);
					
					builder.append(player.getDisplayName());
				}
				
				else {
					builder.append(WHITE + ", ");
					
					if (team.getManager().isLeader(player))
						builder.append(GOLD);
					
					builder.append(player.getDisplayName());
				}
			}
			
			sender.addChatMessage(ProjectGarden.toFormatted(builder.toString()));
		}
		
		else
			sender.addChatMessage(super.ERR_WRONG_COMMAND);
	}

}
