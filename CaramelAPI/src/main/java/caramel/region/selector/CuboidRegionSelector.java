package caramel.region.selector;

import caramel.math.BlockPos;
import caramel.math.Vector3f;
import caramel.nbt.NBTSerializable;
import caramel.nbt.NBTSerializable.NBTTag;
import caramel.nbt.NBTTagType;

@NBTSerializable
public class CuboidRegionSelector implements IRegionSelector<Vector3f> {
	
	@NBTTag(type = NBTTagType.TAG_COMPOUND, key = "First")
	protected BlockPos first;
	@NBTTag(type = NBTTagType.TAG_COMPOUND, key = "Last")
	protected BlockPos last;

	@Override
	public void select(BlockPos... values) {
		
		if (values == null)
			throw new NullPointerException();
		else if (values.length != 2)
			throw new IllegalArgumentException("The length of values must equal to 2");
		
		first = values[0];
		last = values[1];
	}

	@Override
	public void deselect() {
		first = null;
		last = null;
	}

	@Override
	public boolean isPositionInside(Vector3f pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
