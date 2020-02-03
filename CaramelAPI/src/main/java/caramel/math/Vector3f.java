package caramel.math;

import net.minecraft.util.MathHelper;

public class Vector3f extends Vector3<Float> {

	public static final Vector3f zero() {
		return new Vector3f(0.f, 0.f, 0.f);
	}
	
	public Vector3f(float x, float y, float z) {
		super(x == -0.f ? 0.f : x, y == -0.f ? 0.f : y, z == -0.f ? 0.f : z);
	}

	@Override
	public Vector3<Float> add(Vector3<Float> vec) {
		return new Vector3f(x + vec.x, y + vec.y, z + vec.z);
	}

	@Override
	public Vector3<Float> subtract(Vector3<Float> vec) {
		return new Vector3f(vec.x - x, vec.y - y, vec.z - z);
	}

	@Override
	public Vector3<Float> normalize() {
		float length = MathHelper.sqrt_float(x * x + y * y + z * z);
		
		return length < 1.0E-4D ? zero() : new Vector3f(x / length, y / length, z / length);
	}

	@Override
	public Float dot(Vector3<Float> vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	@Override
	public Vector3<Float> cross(Vector3<Float> vec) {
		return new Vector3f(y * vec.z - z * vec.y, z * vec.x - x * vec.z, x * vec.y - y * vec.x);
	}

	@Override
	public Float distanceTo(Vector3<Float> vec) {
		float subX = vec.x - x;
		float subY = vec.y - y;
		float subZ = vec.z - z;
		
		return MathHelper.sqrt_float(subX * subX + subY * subY + subZ * subZ);
	}

	@Override
	public Float length() {
        return MathHelper.sqrt_float(x * x + y * y + z * z);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj != null && obj instanceof Vector3f) {
			Vector3f vec = (Vector3f) obj;
			
			return x == vec.x && y == vec.y && z == vec.z;
		}
		
		return false;
	}

	@Override
	public Vector3<Float> mul(Float scalar) {
		return new Vector3f(x * scalar, y * scalar, z * scalar);
	}

}
