package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jw.minecraft.utility.catchable.Catchable;
import jw.minecraft.utility.catchable.ErrorBase;

public class DefaultCallback implements ICallback {
	
	private final Object instance;
	
	private final Method target;
	
	public DefaultCallback(@Nonnull Object instance, @Nonnull Method target) {
		if (instance == null)
			throw new NullPointerException("Object instance");
		else if (target == null)
			throw new NullPointerException("Method target");
		
		Callback c = target.getDeclaredAnnotation(Callback.class);
		if (c == null || c.type() != CallbackType.DEFAULT)
			throw new IllegalArgumentException("Method target doesn't have @Callback(type = CallbackType.DEFAULT) annotation!");
		
		this.instance = instance;
		this.target = target;
		this.target.setAccessible(true);
	}

	/**
	 * Just Assgin null
	 * @param c will be ignored though it is not null
	 */
	@Override
	public void invoke(@Nullable Catchable c) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		target.invoke(instance);
	}
	
}
