package jwk.minecraft.garden.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.network.PacketShop;
import jwk.minecraft.garden.network.PacketShop.Action;
import jwk.minecraft.garden.shop.PlayerShopCache;
import jwk.minecraft.garden.shop.ShopData.ShopType;
import jwk.minecraft.garden.shop.ShopManager;
import jwk.minecraft.garden.util.SoundUtil.SoundType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import scala.util.Random;

public class PlayerUtil {
	
	public static <P extends EntityPlayer> P getPlayerFromName(@Nonnull String name) {
		checkNotNull(name);
		
		List<P> playerList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for (P player : playerList) {
			
			if (player.getDisplayName().equals(name))
				return player;
		}
		
		return null;
	}

	public static <P extends EntityPlayer> P getPlayerFromUUID(@Nonnull UUID uuid) {
		checkNotNull(uuid);
		
		List<P> playerList = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
		
		for (P player : playerList) {
			
			if (player.getUniqueID().equals(uuid))
				return player;
		}
		
		return null;
	}
	
	public static boolean isPlayerOnline(@Nonnull String name) {
		return (getPlayerFromName(name) != null);
	}
	
	public static boolean isPlayerOnline(@Nonnull UUID uuid) {
		return (getPlayerFromUUID(uuid) != null);
	}
	
	
	public static void sendOpenShop(@Nonnull EntityPlayerMP player, @Nonnull ShopManager manager) {
		if (manager == null)
			return;
		
		PlayerShopCache.put(player, manager);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.SET_SHOP_DATA, manager.getData(), null), player);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.OPEN_SHOP, null, null), player);
		
		if (manager.getShopType() == ShopType.PURCHASE)
			SoundUtil.playSoundTo(player, SoundType.OPEN_PURCHASE_SHOP_GUI);
		else
			SoundUtil.playSoundTo(player, SoundType.OPEN_SALE_SHOP_GUI);
	}
	
	public static void sendOpenShopSetup(@Nonnull EntityPlayerMP player, @Nonnull ShopManager manager) {
		PlayerShopCache.put(player, manager);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.SET_SHOP_DATA, manager.getData(), null), player);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.OPEN_SHOP_SETUP, null, null), player);
	}
	
	public static void sendCloseShop(@Nonnull EntityPlayerMP player) {
		ShopManager manager = PlayerShopCache.get(player);
		
		if (manager == null)
			return;
		
		PlayerShopCache.remove(player);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.CLOSE_SHOP, null, null), player);
		
		if (manager.getShopType() == ShopType.PURCHASE)
			SoundUtil.playSoundTo(player, SoundType.CLOSE_PURCHASE_SHOP_GUI);
		else
			SoundUtil.playSoundTo(player, SoundType.CLOSE_SALE_SHOP_GUI);
	}
	
	public static void sendCloseShopSetup(@Nonnull EntityPlayerMP player) {
		PlayerShopCache.remove(player);
		ProjectGarden.NET_HANDLER.sendTo(new PacketShop(Action.CLOSE_SHOP_SETUP, null, null), player);
	}
	
}
