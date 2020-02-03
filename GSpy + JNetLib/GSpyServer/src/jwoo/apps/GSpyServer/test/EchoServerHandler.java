package jwoo.apps.GSpyServer.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoServerHandler extends ChannelInboundHandlerAdapter
{
	
	@Override
	public void channelRead(ChannelHandlerContext context, Object message)
	{
		ByteBuf buf = (ByteBuf) message;

		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm:ss]");

		StringBuilder builder = new StringBuilder(formatter.format(now));
		builder.append(">> ").append(buf.toString(CharsetUtil.UTF_8));
		System.out.println(builder.toString());
		
		context.write(message);
		context.flush();
	}

	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
		cause.printStackTrace();
		context.close();
	}
	
}
