package caramel.region;

import java.util.Set;

import caramel.attribute.nbt.NBTAttribute;
import caramel.region.selector.CuboidRegionSelector;

public class CuboidRegion implements IRegion<CuboidRegionSelector> {
	
	private final String Identifier;
	private final CuboidRegionSelector Selector;
	
	public CuboidRegion(String id) {
		Identifier = id;
		Selector = new CuboidRegionSelector();
	}
	
	@Override
	public String getIdentifier() {
		return Identifier;
	}

	@Override
	public CuboidRegionSelector getSelector() {
		return Selector;
	}

	@Override
	public Set<NBTAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRegion<CuboidRegionSelector> getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IRegion<CuboidRegionSelector>> getChildSet() {
		// TODO Auto-generated method stub
		return null;
	}

}
