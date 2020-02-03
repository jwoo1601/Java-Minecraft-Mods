package jwk.minecraft.garden.item;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.tab.Tab3Kids;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.TeamRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemAttackTicket extends Item {

	public static final String NAME = "attackTicket";
	public static final String TEXTURE_NAME = "nether_star";
	
	public ItemAttackTicket() {
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
    @SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) { return true; }
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
    	list.add(GRAY.toString() + ITALIC + "랜덤으로 한가지 꽃을 뽑아");
    	list.add("\u00a7a" + GRAY + ITALIC + "상대팀 정원에 그 꽃을 전부 제거합니다.");
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if (!world.isRemote) {
			player.setPositionAndUpdate(-256.5D, 4.D, -551.4D);
			
			ITeam team = TeamRegistry.get("운영자");
			
			if (team != null)
				team.getManager().sendToAllMembers(ProjectGarden.toFormatted(GOLD + player.getCommandSenderName() + WHITE + " 님이 " + RED + "공격권" + WHITE + "을 사용했습니다"));
		}
		
		--stack.stackSize;
		
		return stack;
	}
    
}
