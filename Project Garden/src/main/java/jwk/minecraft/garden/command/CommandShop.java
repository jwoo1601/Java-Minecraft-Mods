package jwk.minecraft.garden.command;

import jwk.minecraft.garden.shop.ShopManager;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandShop extends CommandInterface {

	public static final String SUBCOMMAND_PURCHASE = "구매";
	public static final String SUBCOMMAND_SALE = "판매";
	public static final String SUBCOMMAND_PURCHASE_SETUP = "구매설정";
	public static final String SUBCOMMAND_SALE_SETUP = "판매설정";
	
	public CommandShop() {
		super("상점 명령어", "/비상상점 구매 또는 /비상상점 판매 또는 /비상상점 구매설정 또는 /비상상점 판매설정");
	}
	
	private ShopManager pManager = new ShopManager("구매 상점", ShopType.PURCHASE);
	private ShopManager sManager = new ShopManager("판매상점", ShopType.SALE);

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if (!(sender instanceof EntityPlayer)) {
			sender.addChatMessage(ProjectGarden.toFormatted("이 명령어를 이용할 수 없습니다"));
			return;
		}
	
		if (args.length == 0)
			sender.addChatMessage(ERR_WRONG_COMMAND);
		
		String sub = args[0];
		int length = args.length - 1;
		
		if (sub.equals(SUBCOMMAND_PURCHASE) && length == 0) {
			EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
			
			PlayerUtil.sendOpenShop(player, pManager);
		}
		
		else if (sub.equals(SUBCOMMAND_SALE) && length == 0) {
			EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
			
			PlayerUtil.sendOpenShop(player, sManager);
		}
		
		else if (sub.equals(SUBCOMMAND_PURCHASE_SETUP) && length == 0) {
			EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
			
			PlayerUtil.sendOpenShopSetup(player, pManager);
		}
		
		else if (sub.equals(SUBCOMMAND_SALE_SETUP) && length == 0) {
			EntityPlayerMP player = CommandBase.getCommandSenderAsPlayer(sender);
			
			PlayerUtil.sendOpenShopSetup(player, sManager);
		}
		
		else
			sender.addChatMessage(ERR_WRONG_COMMAND);
	}

}
