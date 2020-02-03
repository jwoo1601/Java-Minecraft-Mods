package jwk.minecraft.garden.command;

import static net.minecraft.util.EnumChatFormatting.*;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.item.ItemUniversalWand;
import jwk.minecraft.garden.item.ItemUniversalWand.Action;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class CommandEnchant extends CommandInterface {
	
	public static final String SUBCOMMAND_DIRECTION = "방향";
	public static final String SUBCOMMAND_SET_TEAM_MEMBER = "팀";
	
	public static final String SUBCOMMAND_TEAM_CHEST = "팀창고";
	public static final String SUB_SUBCOMMAND_OPEN = "열기";
	public static final String SUB_SUBCOMMAND_SET = "설정";
	
	public static final String SUBCOMMAND_TEAM_FLOWER_SHOP = "꽃가게";

	public CommandEnchant() {
		super("설정부여 명령어", "/설정부여 방향|팀 <팀이름>|팀창고 [열기|설정] <팀이름>|꽃가게 <팀이름>");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (!(sender instanceof EntityPlayerMP)) {
			sender.addChatMessage(new ChatComponentText("명령어를 사용할 수 없습니다."));
			return;
		}
		
		if (args.length == 0) {
			sender.addChatMessage(super.ERR_WRONG_COMMAND);
			return;
		}
		
		String sub = args[0];
		int length = args.length - 1;
		EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
		ItemStack heldItem = player.getHeldItem();
		
		if (sub.equals(SUBCOMMAND_DIRECTION) && length == 0) {
			
			if (heldItem == null || heldItem.getItem() != ProjectGarden.proxy.UNIVERSAL_WAND) {
				player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "설정부여 " + WHITE + "가 " + RED + "불가능" + WHITE + "한 아이템 입니다"));
				return;
			}
			
			else if (!ItemUniversalWand.isDefaultWand(heldItem)) {
				player.addChatComponentMessage(ProjectGarden.toFormatted("이미 " + AQUA + "설정부여 " + WHITE + "가 되어 있습니다"));
				return;
			}
			
			int idx = player.inventory.currentItem;
			ItemStack newStack = new ItemStack(ProjectGarden.proxy.UNIVERSAL_WAND, 1);
			
			ItemUniversalWand.setWandAs(newStack, Action.SET_DIRECTION);			
			player.inventory.setInventorySlotContents(idx, newStack);
		}
		
		else if (sub.equals(SUBCOMMAND_SET_TEAM_MEMBER) && length == 1) {
			
			if (heldItem == null || heldItem.getItem() != ProjectGarden.proxy.UNIVERSAL_WAND) {
				player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "설정부여 " + WHITE + "가 " + RED + "불가능" + WHITE + "한 아이템 입니다"));
				return;
			}
			
			else if (!ItemUniversalWand.isDefaultWand(heldItem)) {
				player.addChatComponentMessage(ProjectGarden.toFormatted("이미 " + AQUA + "설정부여 " + WHITE + "가 되어 있습니다"));
				return;
			}
			
			String teamName = args[1];
			
			if (!TeamRegistry.contains(teamName)) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			int idx = player.inventory.currentItem;
			ItemStack newStack = new ItemStack(ProjectGarden.proxy.UNIVERSAL_WAND, 1);
			
			ItemUniversalWand.setWandAs(newStack, Action.SETUP_TEAM_MEMBER);
			
			String oldName = newStack.getDisplayName();
			String newName = teamName + oldName;
			
			newStack.setStackDisplayName(newName);
			player.inventory.setInventorySlotContents(idx, newStack);
		}
		
		else if (sub.equals(SUBCOMMAND_TEAM_CHEST) && length == 2) {
			String sub2 = args[1];
			
			if (sub2.equals(SUB_SUBCOMMAND_OPEN)) {
				
				if (heldItem == null || heldItem.getItem() != ProjectGarden.proxy.UNIVERSAL_WAND) {
					player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "설정부여 " + WHITE + "가 " + RED + "불가능" + WHITE + "한 아이템 입니다"));
					return;
				}
				
				else if (!ItemUniversalWand.isDefaultWand(heldItem)) {
					player.addChatComponentMessage(ProjectGarden.toFormatted("이미 " + AQUA + "설정부여 " + WHITE + "가 되어 있습니다"));
					return;
				}
				
				String teamName = args[2];
				
				if (!TeamRegistry.contains(teamName)) {
					sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
					return;
				}
				
				int idx = player.inventory.currentItem;
				ItemStack newStack = new ItemStack(ProjectGarden.proxy.UNIVERSAL_WAND, 1);
				
				ItemUniversalWand.setWandAs(newStack, Action.OPEN_TEAM_CHEST);
				
				String oldName = newStack.getDisplayName();
				String newName = teamName + oldName;
				
				newStack.setStackDisplayName(newName);
				player.inventory.setInventorySlotContents(idx, newStack);
			}
			
			else if (sub2.equals(SUB_SUBCOMMAND_SET)) {
				
				if (heldItem == null || heldItem.getItem() != ProjectGarden.proxy.UNIVERSAL_WAND) {
					player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "설정부여 " + WHITE + "가 " + RED + "불가능" + WHITE + "한 아이템 입니다"));
					return;
				}
				
				else if (!ItemUniversalWand.isDefaultWand(heldItem)) {
					player.addChatComponentMessage(ProjectGarden.toFormatted("이미 " + AQUA + "설정부여 " + WHITE + "가 되어 있습니다"));
					return;
				}
				
				String teamName = args[2];
				
				if (!TeamRegistry.contains(teamName)) {
					sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
					return;
				}
				
				int idx = player.inventory.currentItem;
				ItemStack newStack = new ItemStack(ProjectGarden.proxy.UNIVERSAL_WAND, 1);
				
				ItemUniversalWand.setWandAs(newStack, Action.SET_TEAM_CHEST);
				
				String oldName = newStack.getDisplayName();
				String newName = teamName + oldName;
				
				newStack.setStackDisplayName(newName);
				player.inventory.setInventorySlotContents(idx, newStack);
			}
			
			else
				player.addChatComponentMessage(super.ERR_WRONG_COMMAND);
		}
		
		else if (sub.equals(SUBCOMMAND_TEAM_FLOWER_SHOP) && length == 1) {
			
			if (heldItem == null || heldItem.getItem() != ProjectGarden.proxy.UNIVERSAL_WAND) {
				player.addChatComponentMessage(ProjectGarden.toFormatted(AQUA + "설정부여 " + WHITE + "가 " + RED + "불가능" + WHITE + "한 아이템 입니다"));
				return;
			}
			
			else if (!ItemUniversalWand.isDefaultWand(heldItem)) {
				player.addChatComponentMessage(ProjectGarden.toFormatted("이미 " + AQUA + "설정부여 " + WHITE + "가 되어 있습니다"));
				return;
			}
			
			String teamName = args[1];
			
			if (!TeamRegistry.contains(teamName)) {
				sender.addChatMessage(ProjectGarden.toFormatted("팀 " + AQUA + teamName + WHITE + " 이(가) 존재하지 않습니다."));
				return;
			}
			
			int idx = player.inventory.currentItem;
			ItemStack newStack = new ItemStack(ProjectGarden.proxy.UNIVERSAL_WAND, 1);
			
			ItemUniversalWand.setWandAs(newStack, Action.SET_WARP_DATA);
			
			String oldName = newStack.getDisplayName();
			String newName = teamName + oldName;
			
			newStack.setStackDisplayName(newName);
			player.inventory.setInventorySlotContents(idx, newStack);
		}
		
		else
			player.addChatComponentMessage(super.ERR_WRONG_COMMAND);
	}

}
