package jwoo.apps.GSpyServer.test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter
{	
	@Override
	public void channelActive(final ChannelHandlerContext context)
	{
		final ByteBuf time = context.alloc().buffer(4);
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		
		final ChannelFuture future = context.writeAndFlush(time);
		future.addListener(f -> {
			assert future == f;
			context.close();
		});
	}
	
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
		cause.printStackTrace();
		context.close();
	}
}
