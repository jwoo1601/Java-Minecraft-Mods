package caramel.region.selector;

import caramel.math.BlockPos;
import caramel.math.Vector3;

public interface IRegionSelector<V extends Vector3> {

	void select(BlockPos... values);
	
	void deselect();
	
	boolean isPositionInside(V pos);
	
}
