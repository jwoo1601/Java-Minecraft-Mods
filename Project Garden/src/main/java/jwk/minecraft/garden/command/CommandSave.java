package jwk.minecraft.garden.command;

import static net.minecraft.util.EnumChatFormatting.*;

import cpw.mods.fml.common.registry.GameRegistry;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketShop.Action;
import jwk.minecraft.garden.shop.ShopManager;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

public class CommandSave extends CommandInterface {
	
	public static final String SUBCOMMAND_TEAM = "팀";
	public static final String SUBCOMMAND_CURRENCY = "돈";

	public CommandSave() {
		super("저장 명령어", "/저장 또는 /저장 [팀|돈]");
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if (args.length == 0) {
			ProjectGarden.proxy.saveServerResources();
			sender.addChatMessage(ProjectGarden.toFormatted(GOLD + "전체 저장" + WHITE + "이 완료되었습니다."));
		}
		
		else if (args.length == 1) {
			String sub = args[0];
			
			if (sub.equals(SUBCOMMAND_TEAM)) {
				TeamRegistry.onSave();
				sender.addChatMessage(ProjectGarden.toFormatted(GOLD + "팀 데이터 저장" + WHITE + "이 완료되었습니다."));
			}
			
			else if (sub.equals(SUBCOMMAND_CURRENCY)) {
				CurrencyYD.INSTANCE.getManager().onSave();
				sender.addChatMessage(ProjectGarden.toFormatted(GOLD + "돈 데이터 저장" + WHITE + "이 완료되었습니다."));
			}
			
			else
				sender.addChatMessage(ERR_WRONG_COMMAND);
		}
		
		else
			sender.addChatMessage(ERR_WRONG_COMMAND);
	}

}
