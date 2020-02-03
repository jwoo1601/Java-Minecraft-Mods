package jwoo.apps.GSpyClient;

import jwoo.apps.GSpyClient.test.TestClient;

public class Program
{
	public static void main(String[] args)
	{
		TestClient clientApp = new TestClient();
		clientApp.setServerAddress("localhost");
		clientApp.setConnectingPort(14991);
		clientApp.Execute(args);
	}
}
