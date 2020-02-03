package jwoo.apps.GSpyServer.packets;

import java.io.IOException;

public class PacketSerializationException extends IOException
{
	private static final long serialVersionUID = -2015171314221484641L;
	
	public PacketSerializationException()
	{
		super();
	}
	
	public PacketSerializationException(String message)
	{
		super(message);
	}
	
	public PacketSerializationException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public PacketSerializationException(Throwable cause)
	{
		super(cause);
	}	
}
