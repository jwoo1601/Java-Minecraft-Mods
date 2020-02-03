package jw.minecraft.utility.math;

import jw.minecraft.utility.nbt.INBTSerializable;

public interface IComponent<T> extends INBTSerializable, Cloneable {
	
	T setOrigin(Vec3i pos);
	
	Vec3i getOrigin();
	
	T setEnd(Vec3i pos);
	
	Vec3i getEnd();
	
	int getVolume();
	
	boolean rangeEquals(T target);
	
	Object clone() throws CloneNotSupportedException;
}
