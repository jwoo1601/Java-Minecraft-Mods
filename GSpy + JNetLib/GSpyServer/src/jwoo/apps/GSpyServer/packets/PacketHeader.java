package jwoo.apps.GSpyServer.packets;

import java.io.Serializable;

public class PacketHeader implements Serializable
{
	public static final long HEADER_LENGTH = Long.BYTES + Integer.BYTES + Integer.BYTES;
	
	public static final int NO_FLAG = 0x00;

	/**
	 * Class-specific Serial Version UID
	 */
	private static final long serialVersionUID = -3235094849692808845L;
	
	public PacketHeader()
	{
		this(-1, -1, NO_FLAG);
	}
	
	public PacketHeader(int type, long contentLength, int flags)
	{
		this.type = type;
		this.packetLength = HEADER_LENGTH + contentLength;
		this.flags = flags;
	}
	
	public int getPacketType()
	{
		return type;
	}
	
	public long getPacketLength()
	{
		return packetLength;
	}
	
	public long getContentLength()
	{
		return packetLength - HEADER_LENGTH;
	}

	public boolean hasFlag(int flag)
	{
		return (flags & flag) == 1;
	}
	
	public void enableFlag(int flag)
	{
		flags |= flag;
	}

	public void disableFlag(int flag)
	{
		flags &= ~flag;
	}
	
	private int type;
	private long packetLength;
	private int flags;	
}
