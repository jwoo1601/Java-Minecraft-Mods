package mint.seobaragi.item;

import static library.ColorCode.Ca;
import static library.ColorCode.Cf;
import static library.Message.NAME;
import static library.Message.TITLE;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;
import library.Reference;
import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWand extends Item
{
	public ItemWand()
	{
		super();
		this.setUnlocalizedName("seonengWand");
		this.setTextureName("seobaragi:SeonengWand");
		this.setMaxStackSize(1);
		this.setFull3D();
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		String Stat = player.getExtendedProperties(ID).toString();
		
		
		if(world.isRemote)
		{
			player.addChatMessage(new ChatComponentText(NAME + Stat));
		}
		return stack;
	}
	
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) entity;
			String Stat = playerMP.getExtendedProperties(ID).toString();
			
			player.addChatMessage(new ChatComponentText(NAME + Ca + playerMP.getDisplayName() + Cf + "의 " + Stat));
		}
		return bFull3D;
	}
}
