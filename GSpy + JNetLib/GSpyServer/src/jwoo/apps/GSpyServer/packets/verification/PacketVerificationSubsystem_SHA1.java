package jwoo.apps.GSpyServer.packets.verification;

import java.io.Serializable;

import jwoo.apps.GSpyServer.packets.IPacket;

public class PacketVerificationSubsystem_SHA1 implements IPacketVerificationSubsystem
{

	@Override
	public boolean isValid(IPacket<? extends Serializable> packet)
	{
		return false;
	}

	@Override
	public void validate(IPacket<? extends Serializable> packet)
	{
		
	}

	@Override
	public void invalidate(IPacket<? extends Serializable> packet)
	{

	}

}
