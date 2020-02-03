package mint.seobaragi.item;

import static library.Message.DRINK;
import static library.Message.NAME;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;
import library.Reference;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemSikhye extends Item
{
	public ItemSikhye()
	{
		super();
		this.setUnlocalizedName("sikhye");
		this.setTextureName("seobaragi:Sikhye");
		this.setMaxStackSize(32);
		this.setCreativeTab(Reference.tabSeoneng);
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
			
			player.addChatMessage(new ChatComponentText(NAME + DRINK));
			stat.setWaterLevel(stat.waterLevel + 10);
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
