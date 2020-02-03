package jwk.minecraft.garden;

import cpw.mods.fml.common.network.IGuiHandler;
import jwk.minecraft.garden.client.gui.GuiTeamChest;
import jwk.minecraft.garden.client.gui.GuiWarp;
import jwk.minecraft.garden.item.ItemUniversalWand;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.warp.ContainerWarp;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FlowerGuiHandler implements IGuiHandler {
	
	public static enum GuiType {
		
		TEAMCHEST,
		WARP;
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		GuiType type = GuiType.values()[ID];
		
		switch (type) {
		
		case TEAMCHEST:
			ItemStack heldItem = player.getHeldItem();
			
			if (heldItem != null && ItemUniversalWand.isTeamChestWand(heldItem)) {
				String teamName = ItemUniversalWand.getTeamName(heldItem.getDisplayName());
				
				if (teamName == null)
					return null;
				
				ITeam team = TeamRegistry.get(teamName);
				
				if (team != null)
					return new ContainerTeamChest(player.inventory, team.getManager().getInventoryTeam());
			}
			
			break;
			
		case WARP:
			ITeam team = TeamRegistry.getTeamOf(player);
			
			if (team == null)
				return new ContainerWarp(ProjectGarden.proxy.DEFAULT_INV_WARP);
			else
				return new ContainerWarp(team.getManager().getInventoryWarp());
		}
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		GuiType type = GuiType.values()[ID];
		
		switch (type) {
		
		case TEAMCHEST:
			ItemStack heldItem = player.getHeldItem();
			
			if (heldItem != null && ItemUniversalWand.isTeamChestWand(heldItem)) {
				InventoryTeamChest invTeam = ProjectGarden.proxy.getInventoryTeam();
				
				if (invTeam != null)
					return new GuiTeamChest(player.inventory, invTeam);
				
				ProjectGarden.logger.error("Could not open Team Chest");
				ProjectGarden.logger.error(": No Current InventoryTeamChest Cache");
			}
			
		case WARP:
			InventoryWarp invWarp = ProjectGarden.proxy.getInventoryWarp();
			
			return new GuiWarp(invWarp);
			
		}
		
		return null;
	}

}
