package jwk.minecraft.garden.team;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.List;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketTeam;
import jwk.minecraft.garden.network.PacketTeam.Action;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.util.NBTUserProfile;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class TeamEventListener {

	public void onDeliverContents(EntityPlayer player, ITeam team, long decreasedAmount, long result, List<ItemStack> delivered) {
		player.openContainer.detectAndSendChanges();
		
		player.addChatComponentMessage(ProjectGarden.toFormatted("팀 " + GOLD + team.getTeamName() + WHITE + " 의 " + AQUA + "배송" + WHITE + "이 완료되었습니다"));
		player.addChatComponentMessage(ProjectGarden.toFormatted("차감 금액: " + GOLD + decreasedAmount + " Y, 현재 잔고: " + GOLD + result + " Y"));
		
		team.getManager().sendToAllMembers(ProjectGarden.toFormatted("주문하신 " + GOLD + "물품" + WHITE + "들의 배송이 " + AQUA + "완료" + WHITE +"되었습니다"));
		team.getManager().sendToAllMembers(ProjectGarden.toFormatted("차감 금액: " + GOLD + decreasedAmount + " Y, 현재 잔고: " + GOLD + result + " Y"));
		team.getManager().sendToAllMembers(ProjectGarden.toFormatted(GOLD + "2" + WHITE + "분 뒤에 " + AQUA + "다시" + WHITE + " 이용하실 수 있습니다"));
		
		team.getManager().playSoundToAllMembers(SoundType.DELIVER_SUCCESS);
	}
	
	public static enum ErrorType {
		
		NO_CURRENT_DEST,
		NOT_ENOUGH_SPACE,
		NOT_ENOUGH_MONEY,
		NO_IMMUTABLE_PRICE;
		
	}
	
	public void onFailedToDeliver(EntityPlayer player, ITeam team, ErrorType type) {
		
		switch (type) {
		
		case NO_CURRENT_DEST:
			player.addChatComponentMessage(ProjectGarden.toFormatted("팀 " + GOLD + team.getTeamName() + WHITE + " 의 지정된 " + AQUA + "창고" + WHITE + "가 존재하지 않습니다"));
			break;
			
		case NOT_ENOUGH_SPACE:
			player.addChatComponentMessage(ProjectGarden.toFormatted("팀 " + GOLD + team.getTeamName() + WHITE + " 의 창고의 " + RED + "여유 공간" + WHITE + "이 " + RED +"부족" + WHITE + "합니다"));
			break;
			
		case NOT_ENOUGH_MONEY:
			player.addChatComponentMessage(ProjectGarden.toFormatted("팀 " + GOLD + team.getTeamName() + WHITE + " 의 통장 " + AQUA + "잔고" + WHITE + " 가 " + RED + "부족합니다"));
			break;
			
		case NO_IMMUTABLE_PRICE:
			player.addChatComponentMessage(ProjectGarden.toFormatted(GOLD + "배송" + AQUA + " 가능" + WHITE + "한 아이템이 없습니다"));
			break;
			
		}

	}
	
	public void onPlayerLogin(ITeam team, EntityPlayerMP player) {
		List<NBTUserProfile> entireList = team.getManager().getUserProfileList();
		ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(Action.SET, entireList, team.getTeamName()), player);
		ProjectGarden.NET_HANDLER.sendTo(new PacketCurrency(PacketCurrency.Action.SET, CurrencyYD.INSTANCE.getManager().get(team)), player);
		
		team.getManager().sendToAllMembersExcept(new PacketTeam(Action.JOIN, NBTUserProfile.fromGameProfile(player.getGameProfile()), null), player);
		
		if (team.getManager().getLeader() != null) {
			EntityPlayer leader = team.getManager().getLeaderAsPlayer();
			
			if (leader != null) {
				
				if (leader.getUniqueID().equals(player.getUniqueID()))
					team.getManager().sendToAllMembers(new PacketTeam(PacketTeam.Action.LEADER, NBTUserProfile.fromGameProfile(leader.getGameProfile()), null));
				else
					ProjectGarden.NET_HANDLER.sendTo(new PacketTeam(PacketTeam.Action.LEADER, NBTUserProfile.fromGameProfile(leader.getGameProfile()), null), player);
			}
		}
	}
	
	public void onPlayerLogout(ITeam team, EntityPlayerMP player) {
		team.getManager().sendToAllMembers(new PacketTeam(Action.LEAVE, NBTUserProfile.fromGameProfile(player.getGameProfile()), null));
	}
	
}
