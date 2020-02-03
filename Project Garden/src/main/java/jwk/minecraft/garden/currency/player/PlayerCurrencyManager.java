package jwk.minecraft.garden.currency.player;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.AbstractCurrencyManager;
import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketCurrency.Action;
import jwk.minecraft.garden.util.NBTUUID;
import jwk.minecraft.garden.util.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class PlayerCurrencyManager extends AbstractCurrencyManager<NBTUUID> {
	
	/**
	 * creates new Currency Manager from specified arguments
	 * @param amount the amount to increase/decrease through {@link #increase(EntityPlayer)} or {@link #decrease(EntityPlayer)}
	 */
	public PlayerCurrencyManager(@Nonnull ICurrency currency, long defaultAmount) {
		super(new PlayerCurrencyData(currency), currency, defaultAmount);
	}
	
	public boolean register(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		return super.register(NBTUUID.fromUUID(player.getUniqueID()));
	}
	
	public boolean unregister(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		return super.unregister(NBTUUID.fromUUID(player.getUniqueID()));
	}
	
	public boolean isPlayerDataExist(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		return super.isDataExist(NBTUUID.fromUUID(player.getUniqueID()));
	}
	
	public long increase(@Nonnull EntityPlayer player) { return increase(player, DefaultAmount); }
	
	public long increase(@Nonnull EntityPlayer player, long amount) {
		checkNotNull(player);
		
		return super.increase(NBTUUID.fromUUID(player.getUniqueID()), amount);
	}
	
	public long decrease(EntityPlayer player) { return decrease(player, DefaultAmount); }
	
	public long decrease(@Nonnull EntityPlayer player, long amount) {
		checkNotNull(player);
		
		return super.decrease(NBTUUID.fromUUID(player.getUniqueID()), amount);
	}
	
	public long set(@Nonnull EntityPlayer player, long value) {
		checkNotNull(player);

		long result = super.set(NBTUUID.fromUUID(player.getUniqueID()), value);
		
		if (result != -1)
			ProjectGarden.NET_HANDLER.sendTo(new PacketCurrency(Action.SET, value), (EntityPlayerMP) player); 				

		return result;
	}
	
	/**
	 * retrieves and gets the player's current value
	 * @param player the target player
	 * @return returns current value if player's data exists (otherwise returns -1)
	 */
	public long get(@Nonnull EntityPlayer player) {
		checkNotNull(player);
		
		return super.get(NBTUUID.fromUUID(player.getUniqueID()));
	}

	@Override
	public void onDataChanged(@Nonnull NBTUUID target, long value) {
		checkNotNull(target);
		
		EntityPlayerMP player = PlayerUtil.getPlayerFromUUID(target.getUniqueID());
		
		if (player != null)
			ProjectGarden.NET_HANDLER.sendTo(new PacketCurrency(Action.SET, value), player);
	}
	
}
