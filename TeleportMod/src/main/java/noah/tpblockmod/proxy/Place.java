package noah.tpblockmod.proxy;

import net.minecraft.util.BlockPos;

public class Place {
	private String name;
	private BlockPos pos;
	
	public Place(String name, BlockPos pos) {
		this.name = name;
		this.pos = pos;
	}
	
	public String getPlaceName() {
		return this.name;
	}
	
	public BlockPos getPlacePosition() {
		return this.pos;
	}
	
	public void setPlaceName(String name) {
		this.name = name;
	}
	
	public void setPlacePosition(BlockPos position) {
		this.pos = position;
	}
	
	public void setPlacePosition(double x, double y, double z) {
		this.pos = new BlockPos(x, y, z);
	}
	
	public void setPlacePosition(int x, int y, int z) {
		this.pos = new BlockPos(x, y, z);
	}
	
	public boolean isEqualTo(BlockPos p) {		
		if (this.pos.getX() == p.getX() && this.pos.getY() == p.getY() && this.pos.getZ() == p.getZ())
			return true;
		else
			return false;
	}
	
	@Override
	public String toString() {
		return "Name="+ this.name + " Position=" + this.pos.toString();
	}
}
