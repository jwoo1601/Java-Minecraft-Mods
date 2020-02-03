package jw.minecraft.utility.callback;

import java.lang.reflect.InvocationTargetException;

import jw.minecraft.utility.catchable.Catchable;

public interface ICallback<C extends Catchable> {
	
	void invoke(C catchable) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
}
