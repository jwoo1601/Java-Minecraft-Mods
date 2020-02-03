package jwk.minecraft.kongagent.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemBlueWaterGun extends Item {

	public static final String ITEM_NAME = "blue_water_gun";
	
	public ItemBlueWaterGun() {
		this.setUnlocalizedName(ITEM_NAME);
		this.setTextureName("kongagent:blue_water_gun");
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target) { return true; }
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
    	list.add(EnumChatFormatting.WHITE + "이 " + EnumChatFormatting.GOLD + "물총" + EnumChatFormatting.WHITE + "으로");
    	list.add("\u00a7a" + EnumChatFormatting.GOLD + "상대" + EnumChatFormatting.WHITE + "에게");
    	list.add("\u00a7a" + EnumChatFormatting.WHITE + "성급함 " + EnumChatFormatting.GOLD + "표식" + EnumChatFormatting.WHITE + "을");
    	list.add("\u00a7a" + EnumChatFormatting.WHITE + "남길 수 있습니다");
    }
}
