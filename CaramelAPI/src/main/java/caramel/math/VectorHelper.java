package caramel.math;

public class VectorHelper {
	
	public static final int ERR_INVALID_TYPE = 0xA1;

	public static final boolean equals(Vector3 first, Vector3 second) {
		Vector3d rf = null, rs = null;
		
		if (first instanceof Vector3f)
			rf = convert((Vector3f) first);
		else if (first instanceof Vector3d)
			rf = convert((Vector3f) first);
		
		if (rf == null)
			return false;
		
		if (second instanceof Vector3f)
			rs = convert((Vector3f) second);
		else if (second instanceof Vector3d)
			rs = convert((Vector3f) second);
		
		if (rs == null)
			return false;
		
		return rf.equals(rs);
	}
	
	public static final boolean equals(Vector3f first, Vector3f second) {
		return first.equals(second);
	}
	
	public static final boolean equals(Vector3d first, Vector3d second) {
		return first.equals(second);
	}
	
	public static final int compareLength(Vector3 first, Vector3 second) {
		Vector3d rf = null, rs = null;
		
		if (first instanceof Vector3f)
			rf = convert((Vector3f) first);
		else if (first instanceof Vector3d)
			rf = convert((Vector3f) first);
		
		if (rf == null)
			return ERR_INVALID_TYPE;
		
		if (second instanceof Vector3f)
			rs = convert((Vector3f) second);
		else if (second instanceof Vector3d)
			rs = convert((Vector3f) second);
		
		if (rs == null)
			return ERR_INVALID_TYPE;
		
		return rf.length() < rs.length() ? -1 : rf.length() == rs.length()? 0 : 1;
	}
	
	public static final int compareLength(Vector3f first, Vector3f second) {
		return first.length() < second.length() ? -1 : first.length() == second.length()? 0 : 1;
	}
	
	public static final int compareLength(Vector3d first, Vector3d second) {
		return first.length() < second.length() ? -1 : first.length() == second.length()? 0 : 1;
	}
	
	public static final Vector3f add(Vector3f first, Vector3f second) {
		return (Vector3f) first.add(second);
	}
	
	public static final Vector3d add(Vector3d first, Vector3d second) {
		return (Vector3d) first.add(second);
	}
	
	public static final Vector3f subtract(Vector3f first, Vector3f second) {
		return (Vector3f) first.subtract(second);
	}
	
	public static final Vector3d subtract(Vector3d first, Vector3d second) {
		return (Vector3d) first.subtract(second);
	}
	
	public static final float dot(Vector3f first, Vector3f second) {
		return first.dot(second);
	}
	
	public static final double dot(Vector3d first, Vector3d second) {
		return first.dot(second);
	}
	
	public static final Vector3f cross(Vector3f first, Vector3f second) {
		return (Vector3f) first.cross(second);
	}
	
	public static final Vector3d cross(Vector3d first, Vector3d second) {
		return (Vector3d) first.cross(second);
	}
	
	public static final Vector3d toVec3d(Vector3<Double> target) {
		return target instanceof Vector3d? (Vector3d) target : null;
	}
	
	public static final Vector3f toVec3f(Vector3<Float> target) {
		return target instanceof Vector3f? (Vector3f) target : null;
	}
	
	public static final Vector3f convert(Vector3d vec) {
		return new Vector3f(vec.x.floatValue(), vec.y.floatValue(), vec.z.floatValue());
	}
	
	public static final Vector3d convert(Vector3f vec) {
		return new Vector3d(vec.x.doubleValue(), vec.y.doubleValue(), vec.z.doubleValue());
	}
	
}
