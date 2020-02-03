package jwk.minecraft.garden.currency;

import java.util.Map.Entry;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.util.IManaged;
import jwk.minecraft.garden.util.INBTConvertable;

public interface ICurrencyManager<T extends INBTConvertable> extends IManaged {

	boolean register(@Nonnull T target);
	
	boolean unregister(@Nonnull T target);
	
	long increase(@Nonnull T target, long amount);
	
	long decrease(@Nonnull T target, long amount);
	
	long set(@Nonnull T target, long value);
	
	long get(@Nonnull T target);
	
	boolean isDataExist(@Nonnull T target);
	
	int getDataSize();
	
	void onDataChanged(@Nonnull T target, long value);
	
}
