package mint.seobaragi.item;

import java.util.List;

import library.Message;
import library.Reference;
import mint.seobaragi.entity.EntityDust;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ThrowableDust extends Item
{
	public ThrowableDust()
	{
		super();
		this.setUnlocalizedName("throwableDust");
		this.setTextureName("seobaragi:ThrowableDust");
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("");
		list.add(Message.DUST_TYPE_B);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,	EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			--stack.stackSize;
			//			e.entityPlayer.inventory.decrStackSize(e.entityPlayer.inventory.currentItem, 1);
		}
		
		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote)
		{
			world.spawnEntityInWorld(new EntityDust(world, player));
		}
		return stack;	
	}
}
