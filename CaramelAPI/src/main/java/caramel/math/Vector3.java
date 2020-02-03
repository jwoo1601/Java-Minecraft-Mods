package caramel.math;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public abstract class Vector3<T> {
	
	protected T x;
	protected T y;
	protected T z;
	
	protected Vector3(T x, T y, T z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public abstract Vector3<T> add(Vector3<T> vec);
	
	public abstract Vector3<T> subtract(Vector3<T> vec);
	
	public abstract Vector3<T> normalize();
	
	public abstract T dot(Vector3<T> vec);
	
	public abstract Vector3<T> cross(Vector3<T> vec);
	
	public abstract T distanceTo(Vector3<T> vec);
	
	public abstract T length();
	
	public abstract Vector3<T> mul(T scalar);
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
