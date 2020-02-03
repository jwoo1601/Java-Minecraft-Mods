package jwoo.apps.GSpyServer.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext context, Object message)
	{
		ByteBuf buf = (ByteBuf) message;
		
		try
		{
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm:ss]");
			
			StringBuilder builder = new StringBuilder(formatter.format(now));
			builder.append(">> ").append(buf.toString(CharsetUtil.UTF_8));
			System.out.println(builder.toString());
		}
		
		finally
		{
			buf.release();
		} 
	}
	
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause)
	{
		cause.printStackTrace();
		context.close();
	}
}
