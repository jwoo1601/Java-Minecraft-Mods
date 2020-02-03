package mint.seobaragi.item;

import static library.Message.CAUSE_DIRTY;
import static library.Message.NAME;
import static library.Message.SUFFER_DIRTY;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import java.util.List;

import library.Message;
import library.Reference;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.packet.SeonengPacket.Type;
import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.proxy.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ProximityOnlyDust extends Item
{
	public ProximityOnlyDust()
	{
		super();
		this.setUnlocalizedName("proximityOnlyDust");
		this.setTextureName("seobaragi:ProximityOnlyDust");
		this.setCreativeTab(Reference.tabSeoneng);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("");
		list.add(Message.DUST_TYPE_A);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		World world = player.worldObj;
		
		if(entity instanceof EntityPlayerMP)
		{
			if(!player.capabilities.isCreativeMode)
			{
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
			
			if(!world.isRemote)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP) entity;
				PropertyPlayerStat stat = (PropertyPlayerStat) playerMP.getExtendedProperties(ID);
				
				player.addChatMessage(new ChatComponentText(playerMP.getDisplayName() + CAUSE_DIRTY));
				playerMP.addChatMessage(new ChatComponentText(SUFFER_DIRTY));
				
				stat.setDirtLevel(stat.dirtLevel + 1);
			}
		}
		return bFull3D;
	}
}
