package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import jw.minecraft.utility.catchable.ErrorBase;

public class ErrorCallback implements ICallback<ErrorBase> {
	
	private final Object instance;
	
	private final Method target;
	
	public ErrorCallback(@Nonnull Object instance, @Nonnull Method target) {
		if (instance == null)
			throw new NullPointerException("Object instance");
		else if (target == null)
			throw new NullPointerException("Method target");
		
		Callback c = target.getDeclaredAnnotation(Callback.class);
		if (c == null || c.type() != CallbackType.ERROR)
			throw new IllegalArgumentException("Method target doesn't have @Callback(type = CallbackType.ERROR) annotation!");
		
		this.instance = instance;
		this.target = target;
		this.target.setAccessible(true);
	}

	@Override
	public void invoke(@Nonnull ErrorBase error) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (error == null)
			throw new NullPointerException("ErrorBase error");
		
		target.invoke(instance, error);
	}
	
}
