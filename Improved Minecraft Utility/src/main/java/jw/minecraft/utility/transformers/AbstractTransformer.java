package jw.minecraft.utility.transformers;

import javax.annotation.Nonnull;

public abstract class AbstractTransformer<T, V> implements ITransformer<T, V> {
	
	@Nonnull
	protected T raw;
	
	
	protected abstract void validate();
	
}
