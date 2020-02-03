package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import jw.minecraft.utility.catchable.Catchable;;

public class UndefinedCallback implements ICallback {
	
	private final Object instance;
	
	private final Method target;
	
	public UndefinedCallback(@Nonnull Object instance, @Nonnull Method target) {
		if (instance == null)
			throw new NullPointerException("Object instance");
		else if (target == null)
			throw new NullPointerException("Method target");
		
		Callback c = target.getDeclaredAnnotation(Callback.class);
		if (c == null || c.type() != CallbackType.UNDEFINED)
			throw new IllegalArgumentException("Method target doesn't have @Callback(type = CallbackType.UNDEFINED) annotation!");
		
		this.instance = instance;
		this.target = target;
		this.target.setAccessible(true);
	}

	@Override
	public void invoke(@Nonnull Catchable c) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (c == null)
			throw new NullPointerException("WarningBase warn");
		
		target.invoke(instance, c);
	}
	
}
