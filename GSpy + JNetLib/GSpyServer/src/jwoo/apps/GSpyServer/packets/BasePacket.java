package jwoo.apps.GSpyServer.packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import io.netty.buffer.ByteBuf;
import jwoo.apps.GSpyServer.ObjectUtils;
import jwoo.apps.GSpyServer.packets.verification.PacketVerificationService;

public class BasePacket<T extends Serializable> implements IPacket<T>
{
	@Override
	public void serialize(ByteBuf in) throws IOException
	{
		in.writeBytes(toByteArray());
	}
	
	@Override
	public void deserialize(ByteBuf in) throws IOException
	{
	}
	
	public void serializeHeader(ByteBuf buf) throws IOException
	{
		if (header == null)
		{
			throw new PacketSerializationException("Header of current packet must not be null!");
		}
		
		try
		{
			buf.writeBytes(ObjectUtils.convertToBytes(content));
		}
		
		catch (IOException e)
		{
			throw new PacketSerializationException(e);
		}
	}
	
	public void serializeContent(ByteBuf buf) throws PacketSerializationException
	{
		try
		{
			buf.writeBytes(ObjectUtils.convertToBytes(content));
		}
		
		catch (IOException e)
		{
			throw new PacketSerializationException(e);
		}
	}
	
	public void deserializeHeader(ByteBuf buf) throws PacketSerializationException
	{
		if (buf.readableBytes() < PacketHeader.HEADER_LENGTH)
		{
			throw new PacketSerializationException("buf.readableBytes() must be greater than or equal to PacketHeader.HEADER_LENGTH!");
		}
		
		// buf.readBytes((int)PacketHeader.HEADER_LENGTH)

	}
	
	public void deserializeContent(byte[] rawData) throws IOException
	{
		
	}
	
	@Override
	public PacketHeader getHeader()
	{
		return header;
	}
	
	@Override
	public T getContent()
	{
		return content;
	}
	
	private PacketHeader header;
	private T content;
	
	Object verificationData;

	@Override
	public byte[] toByteArray() throws IOException
	{
		if (header == null || content == null)
		{
			return null;
		}
		
		try (ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
			 ObjectOutputStream outObj = new ObjectOutputStream(outBytes))
		{
			outObj.writeObject(header);
			outObj.writeObject(content);
			outObj.flush();
			
			return outBytes.toByteArray();
		}
		
		catch (IOException e)
		{
			throw e;
		}
	}

	@Override
	public void fromByteArray(byte[] in) throws IOException, ClassNotFoundException
	{
		try (ObjectInputStream inObj = new ObjectInputStream(new ByteArrayInputStream(in)))
		{
			this.header = (PacketHeader) inObj.readObject();
			this.content = (T) inObj.readObject();
		}
		
		catch (IOException e)
		{
			throw e;
		}
	}
}
