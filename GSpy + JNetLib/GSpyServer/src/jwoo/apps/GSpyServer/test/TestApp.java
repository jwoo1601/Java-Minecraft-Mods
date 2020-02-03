package jwoo.apps.GSpyServer.test;

import java.nio.file.Paths;
import java.util.Date;
import io.netty.channel.ChannelInboundHandler;
import jwoo.apps.GSpyServer.ServerApp;

public class TestApp extends ServerApp
{

	@Override
	public int Execute(String[] args) {
		String executionPath = System.getProperty("user.dir");
		if (executionPath == null)
		{
			TestLogger.init(System.out, System.err, false, null);
		}
		
		else
		{
			TestLogger.init(System.out, System.err, true, Paths.get(executionPath, "log", "server.log").toString());
		}
		
		TestServer testServerApp = new TestServer();
		ChannelInboundHandler serverInboundHandler;
		
		TestLogger.Log("TestServerApp has started on %s", new Date(System.currentTimeMillis()));
		TestLogger.Log("Initializing...");
		
		/* DiscardServer Test */
		// erverInboundHandler = new DiscardServerHandler();
		/* ~DiscardServer Test */
		
		/* EchoServer Test */
		// serverInboundHandler = new EchoServerHandler();
		/* ~EchoServer Test */
		
		/* TimeServer Test */
		serverInboundHandler = new TimeServerHandler();
		/* ~TimeServer Test */
		
		testServerApp.setServerInboundHandler(serverInboundHandler);
		TestLogger.Log("ServerHandlerType=%s", serverInboundHandler.getClass().getName());
		
		TestLogger.Log("Executing ServerApp...");
		int result = testServerApp.Execute(args);
		
		TestLogger.Log("Finalizing...");
		TestLogger.cleanup();
		
		return result;
	}

}
