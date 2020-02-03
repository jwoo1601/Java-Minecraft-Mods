package mint.seobaragi.region;

import javax.annotation.Nonnull;

import mint.seobaragi.vecmath.Vec3i;

public class CuboidRegion {
	
	private String id;
	private CuboidComponent component;
	
	public CuboidRegion() {
		this("temp");
	}
	
	public CuboidRegion(@Nonnull String id) {
		if (id == null)
			throw new NullPointerException("String id");
		
		this.id = id;
		this.component = new CuboidComponent();
	}	
	
	public CuboidRegion(@Nonnull String id, @Nonnull CuboidComponent component) {
		if (id == null)
			throw new NullPointerException("String id");
		if (component == null)
			throw new NullPointerException("CuboidComponent component");
		
		this.id = id;
		this.component = component;
	}	
	
	public Object clone() throws CloneNotSupportedException {
		return new CuboidRegion(id, (CuboidComponent) component.clone());
	}

	public String getId() {
		return id;
	}

	public CuboidComponent getComponent() {
		return component;
	}

	public void setComponent(@Nonnull CuboidComponent component) {
		if (component == null)
			throw new NullPointerException("CuboidComponent component");
		
		this.component = component;
	}

	public boolean isPoisitionInside(Vec3i position) {
		return position.xCoord >= component.getMinPos().xCoord &&
			   position.yCoord >= component.getMinPos().yCoord &&
			   position.zCoord >= component.getMinPos().zCoord &&
			   position.xCoord <= component.getMaxPos().xCoord &&
			   position.yCoord <= component.getMaxPos().yCoord &&
			   position.zCoord <= component.getMaxPos().zCoord;
	}
	
	@Override
	public boolean equals(Object target) {
		if (target instanceof CuboidRegion) {
			CuboidRegion pr = (CuboidRegion) target;
			
			return id.equals(pr.id) && component.equals(pr.component);
		}
		
		return false;
	}
	
}
