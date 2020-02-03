package mint.seobaragi.item;

import static library.Message.NAME;
import static library.Message.RESET_DIRT;
import static library.Message.RESET_TYPE_A;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.List;

import library.Reference;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDirtReset extends Item
{
	public ItemDirtReset()
	{
		super();
		this.setUnlocalizedName("dirtResetItem");
		this.setTextureName("seobaragi:TtaemiriTowel");
		this.setMaxStackSize(1);
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p4)
	{
		list.add("");
		list.add(RESET_TYPE_A);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,	EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}
		
		if(!world.isRemote)
		{
			PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
			
			player.addChatMessage(new ChatComponentText(NAME + RESET_DIRT));
			stat.setDirtLevel(0);
		}
		return stack;
	}
}
