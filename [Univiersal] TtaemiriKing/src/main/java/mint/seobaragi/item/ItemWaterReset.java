package mint.seobaragi.item;

import static library.Message.DRINK;
import static library.Message.NAME;
import static library.Message.RESET_TYPE_B;
import static library.Message.RESET_WATER;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.List;

import library.Reference;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWaterReset extends Item
{
	public ItemWaterReset()
	{
		super();
		this.setUnlocalizedName("waterResetItem");
		this.setTextureName("seobaragi:SeonengMilk");
		this.setMaxStackSize(1);
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p4)
	{
		list.add("");
		list.add(RESET_TYPE_B);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}
		
		if(!world.isRemote)
		{
			PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
			
			player.addChatMessage(new ChatComponentText(NAME + RESET_WATER));
			player.curePotionEffects(stack);
			stat.resetWaterLevelAndSendPacket();
		}
		return stack;
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,	EntityPlayer player)
	{
		PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
		
		if (stat.waterLevel < 50)
		{
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}
		return stack;
	}
}