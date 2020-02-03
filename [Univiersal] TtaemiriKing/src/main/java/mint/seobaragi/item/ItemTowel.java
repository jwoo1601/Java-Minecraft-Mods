package mint.seobaragi.item;

import static library.Message.CANCLEAR;
import static library.Message.HUNGRY;
import static library.Message.NOTCLEAR;
import static library.Message.NOTDIRT;
import static library.Message.USE_TOWEL;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;
import library.Reference;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTowel extends Item
{
	public ItemTowel()
	{
		super();
		this.setUnlocalizedName("towel");
		this.setTextureName("seobaragi:Towel");
		this.setMaxStackSize(1);
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
		}
		
		if(!world.isRemote)
		{
			((PropertyPlayerStat) player.getExtendedProperties(ID)).setCanClean(true);			
			player.addChatMessage(new ChatComponentText(CANCLEAR));
		}
		return stack;
	}
}
