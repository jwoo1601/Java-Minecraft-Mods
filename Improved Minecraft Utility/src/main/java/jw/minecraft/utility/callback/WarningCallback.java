package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import jw.minecraft.utility.catchable.WarningBase;

public class WarningCallback implements ICallback<WarningBase> {
	
	private final Object instance;
	
	private final Method target;
	
	public WarningCallback(@Nonnull Object instance, @Nonnull Method target) {
		if (instance == null)
			throw new NullPointerException("Object instance");
		else if (target == null)
			throw new NullPointerException("Method target");
		
		Callback c = target.getDeclaredAnnotation(Callback.class);
		if (c == null || c.type() != CallbackType.WARNING)
			throw new IllegalArgumentException("Method target doesn't have @Callback(type = CallbackType.WARNING) annotation!");
		
		this.instance = instance;
		this.target = target;
		this.target.setAccessible(true);
	}

	@Override
	public void invoke(@Nonnull WarningBase warn) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (warn == null)
			throw new NullPointerException("WarningBase warn");
		
		target.invoke(instance, warn);
	}
	
}