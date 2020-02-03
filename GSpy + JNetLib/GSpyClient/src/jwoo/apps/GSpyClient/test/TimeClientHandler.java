package jwoo.apps.GSpyClient.test;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext context, Object message)
	{
		ByteBuf buf = (ByteBuf) message;
		
		try
		{
			long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
			System.out.println("[From Server]>> " + new Date(currentTimeMillis));
			context.close();
		}
		
		finally
		{
			buf.release();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
	{
		cause.printStackTrace();
		context.close();
	}
}
