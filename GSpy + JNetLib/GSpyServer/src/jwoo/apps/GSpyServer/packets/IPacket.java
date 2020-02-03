package jwoo.apps.GSpyServer.packets;

import java.io.IOException;
import java.io.Serializable;

import io.netty.buffer.ByteBuf;

public interface IPacket<T extends Serializable>
{	
	public void serialize(ByteBuf in) throws IOException;
	public void deserialize(ByteBuf in) throws IOException;
	
	public PacketHeader getHeader();
	public T getContent();
	
	public byte[] toByteArray() throws IOException;
	public void fromByteArray(byte[] in) throws IOException, ClassNotFoundException;
}
