package jwk.minecraft.garden.currency.team;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.currency.AbstractCurrencyManager;
import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketCurrency.Action;
import jwk.minecraft.garden.team.ITeam;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

public class TeamCurrencyManager extends AbstractCurrencyManager<ITeam> {

	public TeamCurrencyManager(@Nonnull ICurrency currency, long defaultAmount) {
		super(new TeamCurrencyData(currency), currency, defaultAmount);
	}

	@Override
	public void onDataChanged(@Nonnull ITeam target, long value) {
		checkNotNull(target);
		
		target.getManager().sendToAllMembers(new PacketCurrency(Action.SET, value));
	}
	
	@Override
	public long increase(@Nonnull ITeam target, long amount) {
		long result = super.increase(target, amount);
		
		onDataChanged(target, result);
		return result;
	}
	
	@Override
	public long decrease(@Nonnull ITeam target, long amount) {
		long result = super.decrease(target, amount);
		
		onDataChanged(target, result);
		return result;
	}
	
	@Override
	public long set(@Nonnull ITeam target, long value) {
		long prev = super.set(target, value);
		
		onDataChanged(target, value);
		return prev;
	}
	
}
