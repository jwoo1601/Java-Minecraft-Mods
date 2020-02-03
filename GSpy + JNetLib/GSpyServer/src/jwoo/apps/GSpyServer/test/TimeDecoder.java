package jwoo.apps.GSpyServer.test;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TimeDecoder extends ByteToMessageDecoder
{

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception
	{
		if (in.readableBytes() >= 4)
		{
			out.add(in.readBytes(4));
		}
	}

}
