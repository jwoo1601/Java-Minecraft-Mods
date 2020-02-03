package mint.seobaragi.vecmath;

import net.minecraft.util.Vec3;

public class Vec3i {
	
	public static final Vec3i ZERO() {
		return new Vec3i(0, 0, 0);
	}
	
	public int xCoord = 0;
	public int yCoord = 0;
	public int zCoord = 0;
	
	protected Vec3i(int x, int y, int z) {				
		if (x == -0)
			x = 0;
		if (y == -0)
			y = 0;
		if (z == -0)
			z = 0;

		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Vec3i(xCoord, yCoord, zCoord);
	}
	
	public static Vec3i createVector(int x, int y, int z) {
		return new Vec3i(x, y, z);
	}
	
	public Vec3 toVec3() {
		return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Vec3i) {
			Vec3i tmp = (Vec3i) o;
			return tmp.xCoord == this.xCoord && tmp.yCoord == this.yCoord && tmp.zCoord == this.zCoord;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + xCoord + "," + yCoord + "," + zCoord + ")";
	}
	
}
