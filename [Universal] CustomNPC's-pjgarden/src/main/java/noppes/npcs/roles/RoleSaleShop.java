package noppes.npcs.roles;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Random;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.shop.ShopManager;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import noppes.npcs.entity.EntityNPCInterface;

public class RoleSaleShop extends RoleInterface {

	private String shopName;
	private ShopManager manager;
	
	public RoleSaleShop(EntityNPCInterface npc) {
		super(npc);

		shopName = "sale_" + npc.getUniqueID().toString().replace("-", "").substring(0, 5) + "_" + npc.getCommandSenderName();
		manager = new ShopManager(shopName, ShopType.SALE);
	}

	private static final String KEY_SHOP_NAME = "Shop Name";
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setString(KEY_SHOP_NAME, shopName);
		
		if (!npc.isRemote())
			manager.onSave();
		
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		String name = null;
		if (compound.hasKey(KEY_SHOP_NAME, NBT.TAG_STRING))
			name = compound.getString(KEY_SHOP_NAME);
		else
			throw new IllegalArgumentException();
		
		if (!name.equals(shopName))
			return;
		
		if (!npc.isRemote())
			manager.onLoad();
	}

	@Override
	public void interact(EntityPlayer player) {
		npc.say(player, npc.advanced.getInteractLine());
		
		PlayerUtil.sendOpenShop((EntityPlayerMP) player, manager);
	}
	
	public String getShopName() { return shopName; }
	
	public ShopManager getManager() { return manager; }

}
