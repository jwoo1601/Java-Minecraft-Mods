package mint.seobaragi.region;

import javax.annotation.Nonnull;

import mint.seobaragi.vecmath.Vec3i;

public class CuboidComponent {

	private Vec3i first = null;
	private Vec3i last = null;
	
	public CuboidComponent() {}
	
	public CuboidComponent(Vec3i first) {
		this.first = first;
	}
	
	public CuboidComponent(Vec3i first, Vec3i last) {
		this.first = first;
		this.last = last;
	}
	
	@Override
	public String toString() {
		return "first: " + (first == null? "null" : first.toString()) + " last: " + (last == null? "null" : last.toString());
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Vec3i co, ce;
		
		if (first == null)
			co = null;
		else
			co = (Vec3i) first.clone();
		
		if (last == null)
			ce = null;
		else
			ce = (Vec3i) last.clone();
		
		return new CuboidComponent(co, ce);
	}
	
	public Vec3i getMinPos() {
		if (first == null || last == null)
			return null;
		
		int x = first.xCoord < last.xCoord ? first.xCoord : last.xCoord;
		int y = first.yCoord < last.yCoord ? first.yCoord : last.yCoord;
		int z = first.zCoord < last.zCoord ? first.zCoord : last.zCoord;
		
		return Vec3i.createVector(x, y, z);
	}
	
	public Vec3i getMaxPos() {
		if (first == null || last == null)
			return null;
		
		int x = first.xCoord >= last.xCoord ? first.xCoord : last.xCoord;
		int y = first.yCoord >= last.yCoord ? first.yCoord : last.yCoord;
		int z = first.zCoord >= last.zCoord ? first.zCoord : last.zCoord;
		
		return Vec3i.createVector(x, y, z);
	}

	public CuboidComponent setFirst(Vec3i pos) {
		first = pos;		
		return this;
	}

	public Vec3i getFirst() {
		return first;
	}

	public CuboidComponent setLast(Vec3i pos) {
		last = pos;		
		return this;
	}

	public Vec3i getLast() {
		return last;
	}
	
	public int getXLength() {
		return Math.abs(first.xCoord - last.xCoord);
	}
	
	public int getYLength() {
		return Math.abs(first.yCoord - last.yCoord);
	}
	
	public int getZLength() {
		return Math.abs(first.zCoord - last.zCoord);
	}
	
	public int getVolume() {
		return getXLength() * getYLength() * getZLength();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof CuboidComponent) {
			CuboidComponent target = (CuboidComponent) o;
			return this.first.equals(target.first) && this.last.equals(target.last);
		}
		
		return false;
	}

	public boolean rangeEquals(@Nonnull CuboidComponent target) {
		if (target == null)
			throw new NullPointerException("CuboidComponent target");
		
		return first != null && last != null ? getMinPos().equals(target.getMinPos()) && getMaxPos().equals(target.getMaxPos()) : false;
	}
	
	public static boolean isPoisitionInside(@Nonnull CuboidComponent component, @Nonnull Vec3i position) {
		if (component == null)
			throw new NullPointerException("CuboidComponent component");
		
		return position.xCoord >= component.getMinPos().xCoord &&
			   position.yCoord >= component.getMinPos().yCoord &&
			   position.zCoord >= component.getMinPos().zCoord &&
			   position.xCoord <= component.getMaxPos().xCoord &&
			   position.yCoord <= component.getMaxPos().yCoord &&
			   position.zCoord <= component.getMaxPos().zCoord;
	}
	
}
