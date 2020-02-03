package caramel.math;

import net.minecraft.util.MathHelper;

public class Vector3d extends Vector3<Double> {

	public static final Vector3d zero() {
		return new Vector3d(0.D, 0.D, 0.D);
	}
	
	public Vector3d(double x, double y, double z) {
		super(x == -0.D ? 0.D : x, y == -0.D ? 0.D : y, z == -0.D ? 0.D : z);
	}

	@Override
	public Vector3<Double> add(Vector3<Double> vec) {
		return new Vector3d(x + vec.x, y + vec.y, z + vec.z);
	}

	@Override
	public Vector3<Double> subtract(Vector3<Double> vec) {
		return new Vector3d(vec.x - x, vec.y - y, vec.z - z);
	}

	@Override
	public Vector3<Double> normalize() {
		double length = Math.sqrt(x * x + y * y + z * z);
		
		return length < 1.0E-4D ? zero() : new Vector3d(x / length, y / length, z / length);
	}

	@Override
	public Double dot(Vector3<Double> vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	@Override
	public Vector3<Double> cross(Vector3<Double> vec) {
		return new Vector3d(y * vec.z - z * vec.y, z * vec.x - x * vec.z, x * vec.y - y * vec.x);
	}

	@Override
	public Double distanceTo(Vector3<Double> vec) {
		double subX = vec.x - x;
		double subY = vec.y - y;
		double subZ = vec.z - z;
		
		return Math.sqrt(subX * subX + subY * subY + subZ * subZ);
	}

	@Override
	public Double length() {
        return Math.sqrt(x * x + y * y + z * z);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj != null && obj instanceof Vector3d) {
			Vector3d vec = (Vector3d) obj;
			
			return x == vec.x && y == vec.y && z == vec.z;
		}
		
		return false;
	}

	@Override
	public Vector3<Double> mul(Double scalar) {
		return new Vector3d(x * scalar, y * scalar, z * scalar);
	}

}
