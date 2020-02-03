package jwk.minecraft.garden.currency.player;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.UUID;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.currency.AbstractCurrencyData;
import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.exception.EntryAlreadyExistException;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.SimpleTeam;
import jwk.minecraft.garden.util.NBTUUID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class PlayerCurrencyData extends AbstractCurrencyData<NBTUUID> {

	public PlayerCurrencyData(@Nonnull ICurrency currency) {
		super(currency);
	}

	@Override
	public void read(NBTTagCompound tagCompound) {
		NBTTagCompound tagCurrency = null;
		
		if (tagCompound.hasKey(KEY_CURRENCY, NBT.TAG_COMPOUND))
			tagCurrency = tagCompound.getCompoundTag(KEY_CURRENCY);
		else
			throw new IllegalArgumentException();
		
		String id = null;
		
		if (tagCurrency.hasKey(KEY_ID, NBT.TAG_STRING))
			id = tagCurrency.getString(KEY_ID);
		else
			throw new IllegalArgumentException();
		
		if (!currency.getDisplayName().equals(id))
			throw new RuntimeException("the target currency file had been extremely damaged!");
		
		NBTTagCompound tagEntries = null;
		if (tagCurrency.hasKey(KEY_ENTRIES, NBT.TAG_COMPOUND))
			tagEntries = tagCurrency.getCompoundTag(KEY_ENTRIES);
		else
			throw new IllegalArgumentException();
		
		int size = 0;
		if (tagEntries.hasKey(KEY_SIZE, NBT.TAG_INT))
			size = tagEntries.getInteger(KEY_SIZE);
		else
			throw new IllegalArgumentException();
		
		if (size != 0) {
			
			for (int i=0; i < size; i++) {
				NBTTagCompound tagEntry = null;
				String key = KEY_ENTRY_BASE + i;
				
				if (tagEntries.hasKey(key , NBT.TAG_COMPOUND))
					tagEntry = tagEntries.getCompoundTag(key);
				else
					throw new IllegalArgumentException();
							
				NBTUUID target = NBTUUID.fromUUID(UUID.randomUUID());
				if (tagEntry.hasKey(KEY_KEY))
					target.fromNBT(tagEntry.getTag(KEY_KEY));
				else
					throw new IllegalArgumentException();
				
				long value = 0L;				
				if (tagEntry.hasKey(KEY_VALUE, NBT.TAG_LONG))
					value = tagEntry.getLong(KEY_VALUE);
				else
					throw new IllegalArgumentException();
				
				checkArgument(value >= 0);
				
				if (objects.containsKey(target))
					throw new EntryAlreadyExistException(target);
				
				objects.put(target, value);
			}
		}
	}

}
