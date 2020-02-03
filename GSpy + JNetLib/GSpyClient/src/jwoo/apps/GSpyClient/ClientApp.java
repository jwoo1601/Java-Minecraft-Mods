package jwoo.apps.GSpyClient;

public abstract class ClientApp
{
	public static final int RESULT_SUCCESS = 0x00;
	public static final int RESULT_INVALID_APP = 0x01;
	public static final int RESULT_INVALID_ARGUMENTS = 0x02;
	public static final int RESULT_EXCEPTION_CAUGHT = 0x03;
	
	public ClientApp()
	{
		this(false);
	}
	
	public ClientApp(boolean bRunParallel)
	{
		this.bRunParallel = bRunParallel;
	}
	
	public abstract int Execute(String[] args);
	
	public void reportException(Throwable exception)
	{
		exception.printStackTrace();
	}
	
	protected boolean bRunParallel;
	
	public boolean isRunParallel()
	{
		return bRunParallel;
	}
}
