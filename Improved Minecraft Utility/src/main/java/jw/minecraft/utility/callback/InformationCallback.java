package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import jw.minecraft.utility.catchable.InformationBase;

public class InformationCallback implements ICallback<InformationBase> {
	
	private final Object instance;
	
	private final Method target;
	
	public InformationCallback(@Nonnull Object instance, @Nonnull Method target) {
		if (instance == null)
			throw new NullPointerException("Object instance");
		else if (target == null)
			throw new NullPointerException("Method target");
		
		Callback c = target.getDeclaredAnnotation(Callback.class);
		if (c == null || c.type() != CallbackType.INFORMATION)
			throw new IllegalArgumentException("Method target doesn't have @Callback(type = CallbackType.INFORMATION) annotation!");
		
		this.instance = instance;
		this.target = target;
		this.target.setAccessible(true);
	}

	@Override
	public void invoke(@Nonnull InformationBase info) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (info == null)
			throw new NullPointerException("InformationBase info");
		
		target.invoke(instance, info);
	}
	
}
