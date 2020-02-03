package caramel.system;

public class SystemObject<T extends Object> {
	
	public static final SystemObject NULL = new SystemObject(null);

	private int address = -1;
	private T object;
	
	public SystemObject(T obj) {
		this (-1, obj);
	}
	
	public SystemObject(int addr, T obj) {
		this.address = addr;
		this.object = obj;
	}
	
	public int getAddress() { return address; }
	
	public T getObject() { return object; }
	
}
