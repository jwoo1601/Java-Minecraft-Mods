package jwoo.apps.GSpyServer.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jwoo.apps.GSpyServer.ServerApp;

public class TestServer extends ServerApp
{
	public TestServer()
	{
		super(true);
	}
	
	public TestServer(int serverPort)
	{
		setServerPort(serverPort);
	}

	@Override
	public int Execute(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try
		{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					 .channel(NioServerSocketChannel.class)
					 .childHandler(new ChannelInitializer<SocketChannel>()
							 {
						 		@Override
						 		public void initChannel(SocketChannel channel) throws Exception
						 		{
						 			channel.pipeline().addLast(serverInboundHandler);
						 		}
							 })
					 .option(ChannelOption.SO_BACKLOG, 256)
					 .childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture future = bootstrap.bind(serverPort).sync();
			future.addListener(f -> {
				if (f instanceof ChannelFuture)
				{
					ChannelFuture cf = (ChannelFuture) f;
					if (cf.isSuccess())
					{
						TestLogger.Log("Connected to a new client: Addr=%s", cf.channel().localAddress().toString());
					}
				}
			});//channel().closeFuture().sync();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			reportException(e);
			
			return RESULT_EXCEPTION_CAUGHT;
		}
		
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
		return RESULT_SUCCESS;
	}
	
	private int serverPort = 14991;
	
	public int getServerPort()
	{
		return serverPort;
	}
	
	public void setServerPort(int port)
	{
		serverPort = port;
	}
	
	private ChannelInboundHandler serverInboundHandler;
	
	public ChannelInboundHandler getServerInboundHandler()
	{
		return serverInboundHandler;
	}
	
	public void setServerInboundHandler(ChannelInboundHandler serverInboundHandler)
	{
		if (serverInboundHandler != null)
		{
			this.serverInboundHandler = serverInboundHandler;
		}
	}
	
	private ChannelOutboundHandler serverOutboundHandler;
	
	public ChannelOutboundHandler getServerOutboundHandler()
	{
		return serverOutboundHandler;
	}
	
	public void setServerOutboundHandler(ChannelOutboundHandler serverOutboundHandler)
	{
		if (serverOutboundHandler != null)
		{
			this.serverOutboundHandler = serverOutboundHandler;
		}
	}
}
