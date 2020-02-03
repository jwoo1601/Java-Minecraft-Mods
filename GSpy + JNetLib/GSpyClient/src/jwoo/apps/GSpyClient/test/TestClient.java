package jwoo.apps.GSpyClient.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import jwoo.apps.GSpyClient.ClientApp;

public class TestClient extends ClientApp
{

	@Override
	public int Execute(String[] args) {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try
		{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workerGroup);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						public void initChannel(SocketChannel channel) throws Exception
						{
							channel.pipeline().addLast(new TimeClientHandler());
						}
					});
			
			ChannelFuture fconnect = bootstrap.connect(serverAddress, port).sync();
			fconnect.channel().closeFuture().sync();
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			reportException(e);
			
			return RESULT_EXCEPTION_CAUGHT;
		}
		
		finally
		{
			workerGroup.shutdownGracefully();
		}
		
		return RESULT_SUCCESS;
	}
	
	private String serverAddress;
	
	public String getServerAddress()
	{
		return serverAddress;
	}
	
	public void setServerAddress(String address)
	{
		serverAddress = address;
	}
	
	private int port;
	
	public int getConnectingPort()
	{
		return port;
	}
	
	public void setConnectingPort(int port)
	{
		this.port = port;
	}
}
