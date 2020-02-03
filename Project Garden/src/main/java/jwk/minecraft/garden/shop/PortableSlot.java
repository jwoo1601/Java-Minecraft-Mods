package jwk.minecraft.garden.shop;

import static com.google.common.base.Preconditions.checkArgument;

import io.netty.buffer.ByteBuf;

public class PortableSlot {

	private int index;
	private long value;
	
	public PortableSlot() { }
	
	public PortableSlot(int index, long value) {
		checkArgument(index >= 0 && index < 12);
		
		this.index = index;
		this.value = value < 0? 0 : value;
	}
	
	public int getIndex() { return index; }
	
	public long getValue() { return value; }
	
	
	public void serialize(ByteBuf buf) {
		buf.writeInt(index);
		buf.writeLong(value);
	}
	
	public void deserialize(ByteBuf buf) {
		index = buf.readInt();
		value = buf.readLong();
	}
	
}
