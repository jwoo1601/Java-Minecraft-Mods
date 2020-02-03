package jwk.minecraft.garden.item;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.FlowerGuiHandler.GuiType;
import jwk.minecraft.garden.network.PacketWarp;
import jwk.minecraft.garden.tab.Tab3Kids;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWarpClock extends Item {

	public static final String NAME = "warpClock";
	public static final String TEXTURE_NAME = "clock";
	
	public ItemWarpClock() {
		this.setUnlocalizedName(NAME)
		    .setMaxDamage(0)
		    .setMaxStackSize(1)
		    .setTextureName(TEXTURE_NAME)
		    .setCreativeTab(Tab3Kids.INSTANCE);
	}
	
	@Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			ITeam team = TeamRegistry.getTeamOf(player);
			InventoryWarp inv = null;
			
			if (team != null)
				inv = team.getManager().getInventoryWarp();
			
			ProjectGarden.NET_HANDLER.sendTo(new PacketWarp(jwk.minecraft.garden.network.PacketWarp.Action.SET_FLOWER_SHOP, inv), (EntityPlayerMP) player);
			player.openGui(ProjectGarden.instance, GuiType.WARP.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		
		return stack;
	}
	
}
