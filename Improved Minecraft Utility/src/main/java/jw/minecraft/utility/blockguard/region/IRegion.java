package jw.minecraft.utility.blockguard.region;

import jw.minecraft.utility.math.IComponent;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.nbt.INBTSerializable;

public interface IRegion<T extends IComponent> extends INBTSerializable, Cloneable {
	
	String getId();
	
	T getComponent();
	
	void setComponent(T component);
	
	boolean isPoisitionInside(Vec3i position);
	
	Object clone() throws CloneNotSupportedException;
}
