package jwk.minecraft.garden.currency;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.network.PacketCurrency;
import jwk.minecraft.garden.network.PacketCurrency.Action;
import jwk.minecraft.garden.util.INBTConvertable;
import jwk.minecraft.garden.util.NBTFileManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractCurrencyManager<T extends INBTConvertable> implements ICurrencyManager<T> {
	
	public static final String CURRENCY_DEFAULT_PATH = ProjectGarden.getSaveDirectory() + "\\currency";
	
	public final long DefaultAmount;
	
	protected ICurrency currency;
	protected AbstractCurrencyData<T> data;
	
	private NBTFileManager fileManager;
	
	protected AbstractCurrencyManager(@Nonnull AbstractCurrencyData<T> data, @Nonnull ICurrency currency, long defaultAmount) {
		checkNotNull(data);
		checkNotNull(currency);
		checkArgument(defaultAmount > 0);
		
		DefaultAmount = defaultAmount;
		this.currency = currency;
		this.data = data;
		this.fileManager = new NBTFileManager(CURRENCY_DEFAULT_PATH + "\\" + currency.getDisplayName() + ".dat");
	}

	@Override
	public boolean register(@Nonnull T target) {
		checkNotNull(target);
		
		if (!data.objects.containsKey(target)) {
			data.objects.put(target, 0L);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean unregister(@Nonnull T target) {
		checkNotNull(target);
		
		if (data.objects.containsKey(target)) {
			data.objects.remove(target);
			return true;
		}
		
		return false;
	}

	public long increase(@Nonnull T target) { return increase(target, DefaultAmount); }
	
				@Override
				public long increase(@Nonnull T target, long amount) {
		checkArgument(amount >= 0, "the value to be added must be greater than or equal to 0");
		
		long prev = get(target);
		
		if (prev == -1)
			return -1;
		
		long result = prev + amount;
		
		set(target, prev + amount);
		return result;
	}

	public long decrease(@Nonnull T target) { return decrease(target, DefaultAmount); }
	
	@Override
	public long decrease(@Nonnull T target, long amount) {
		checkArgument(amount >= 0, "the value to be subtracted must be greater than or equal to 0");
		
		long prev = get(target);
		
		if (prev == -1)
			return -1;
		
		long result = prev - amount;
		
		set(target, result >= 0? result : 0);
		return result;
	}

	@Override
	public long set(@Nonnull T target, long value) {
		checkNotNull(target);
		checkArgument(value >= 0, "the value to be replaced must be greater than or equal to 0");
		
		if (!isDataExist(target))
			return -1;
		
		return data.objects.put(target, value);
	}

	@Override
	public long get(@Nonnull T target) {
		
		if (isDataExist(target))
			return data.objects.get(target);
		
		return -1;
	}

	@Override
	public boolean isDataExist(@Nonnull T target) {
		checkNotNull(target);
		
		return data.objects.containsKey(target);
	}

	@Override
	public int getDataSize() { return data.objects.size(); }

	@Override
	public void onLoad() {
		try {
			data.read(fileManager.readFromFile());
		}
		
		catch (FileNotFoundException e) {
			ProjectGarden.logger.info("no previous currency file detected <path=" + e.getMessage() + ">");
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onUnload() {
		data.objects.clear();
	}
	
	@Override
	public void onSave() {
		if (data.objects.isEmpty())
			return;
		
		try {
			NBTTagCompound compound = new NBTTagCompound();
			
			data.write(compound);
			fileManager.writeToFile(true, compound);
		}		
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}	

	@Override
	public abstract void onDataChanged(@Nonnull T target, long value);
	
	protected Map<T, Long> getObjects() { return data.objects; }

}
