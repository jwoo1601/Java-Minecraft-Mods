package mint.seobaragi.item;

import static library.ColorCode.Ca;
import static library.ColorCode.Cf;
import static library.Message.DIRTCHUNK;
import static library.Message.ERROR_1;
import static library.Message.HUNGRY;
import static library.Message.NOTCLEAR;
import static library.Message.NOTDIRT;
import static library.Message.USE_TOWEL;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.ArrayList;
import java.util.Random;

import library.Reference;
import mint.seobaragi.SeonengItems;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemTtaemiriTowel extends Item
{
	public ItemTtaemiriTowel()
	{
		super();
		this.setUnlocalizedName("ttaemiriTowel");
		this.setTextureName("seobaragi:TtaemiriTowel");
		this.setMaxStackSize(32);
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);
		Random rand = new Random();
		
		boolean Type_Hungry = player.getFoodStats().getFoodLevel() <= 0;
		boolean Type_Dirt = stat.dirtLevel <= 0;		
		
		int i = 1 + rand.nextInt(3);
		ItemStack ret = new ItemStack(SeonengItems.dirtChunk, i);
		ret.setStackDisplayName(Ca + player.getDisplayName() + Cf + DIRTCHUNK);
		
		
		if(Type_Hungry)
		{
			if(!world.isRemote)
				player.addChatMessage(new ChatComponentText(HUNGRY));
		}
		
		else if(!stat.canClean)
		{
			if(!world.isRemote)
				player.addChatMessage(new ChatComponentText(NOTCLEAR));
		}
		
		else if(Type_Dirt)
		{
			if(!world.isRemote)
				player.addChatMessage(new ChatComponentText(NOTDIRT));
		}
		
		else if(!Type_Hungry && !Type_Dirt && stat.canClean)
		{
			if(!player.capabilities.isCreativeMode)
				--stack.stackSize;
			
			if(!world.isRemote)
			{
				player.addChatMessage(new ChatComponentText(USE_TOWEL));
				player.inventory.addItemStackToInventory(ret);
				
				stat.setDirtLevel(stat.dirtLevel - 1).setCanClean(false);
			}
		}
		
		//Error
		else
		{
			if(!world.isRemote)
				player.addChatMessage(new ChatComponentText(ERROR_1));
		}
		return stack;
	}
}
