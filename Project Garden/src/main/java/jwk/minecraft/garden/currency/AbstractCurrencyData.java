package jwk.minecraft.garden.currency;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import jwk.minecraft.garden.exception.EntryAlreadyExistException;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.util.INBTConvertable;
import jwk.minecraft.garden.util.INBTSerializable;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class AbstractCurrencyData<T extends INBTConvertable> implements INBTSerializable {

	protected ICurrency currency;
	protected Map<T, Long> objects;	
	
	protected AbstractCurrencyData(@Nonnull ICurrency currency) {
		this.currency = currency;
		objects = new ConcurrentHashMap<T, Long>();
	}
	
	protected static final String KEY_CURRENCY = "Currency Data";
	protected static final String KEY_ID = "Id";
	protected static final String KEY_ENTRIES = "Entries";
	protected static final String KEY_SIZE = "Size";
	protected static final String KEY_ENTRY_BASE = "Entry -";
	protected static final String KEY_KEY = "Key";
	protected static final String KEY_VALUE = "Value";
	
	@Override
	public void write(NBTTagCompound tagCompound) {
		NBTTagCompound tagCurrency = new NBTTagCompound();		
		tagCurrency.setString(KEY_ID, currency.getDisplayName());
		
		NBTTagCompound tagEntries = new NBTTagCompound();
		int size = objects.size();
		tagEntries.setInteger(KEY_SIZE, size);
		
		if (size != 0) {
			List<Entry<T, Long>> entries = Lists.newArrayList(objects.entrySet().iterator());
			
			for (int i=0; i < size; i++) {
				NBTTagCompound tagEntry = new NBTTagCompound();
				
				tagEntry.setTag(KEY_KEY, entries.get(i).getKey().toNBT());
				tagEntry.setLong(KEY_VALUE, entries.get(i).getValue());
				
				tagEntries.setTag(KEY_ENTRY_BASE + i, tagEntry);
			}
		}
		
		tagCurrency.setTag(KEY_ENTRIES, tagEntries);
		tagCompound.setTag(KEY_CURRENCY, tagCurrency);
	}

	@Override
	public abstract void read(NBTTagCompound tagCompound);
	
}
