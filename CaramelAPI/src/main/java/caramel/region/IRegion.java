package caramel.region;

import java.util.Set;

import caramel.attribute.nbt.NBTAttribute;
import caramel.region.selector.IRegionSelector;

public interface IRegion<T extends IRegionSelector> {
	
	String getIdentifier();
	
	T getSelector();
	
	Set<NBTAttribute> getAttributes();
	
	IRegion<T> getParent();
	
	Set<IRegion<T>> getChildSet();
	
}
