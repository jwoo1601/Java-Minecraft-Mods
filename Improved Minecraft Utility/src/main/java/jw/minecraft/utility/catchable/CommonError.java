package jw.minecraft.utility.catchable;

public class CommonError extends ErrorBase {

	public static final String ID = "common";
	
	public CommonError(int code, String defaultMessage, Object[] args) {
		super(ID, code, defaultMessage, args);
	}
	
	
	public static final WrongCommandError WrongCommand = new WrongCommandError();
	
	public static final NonPlayerError NonPlayer = new NonPlayerError();
	
	public static final NoPermissionError NoPermission = new NoPermissionError();
	
	
	public static class WrongCommandError extends CommonError { public WrongCommandError() { super(0, "Wrong Command!", null); }	}
	
	public static class NonPlayerError extends CommonError { public NonPlayerError() { super(1, "Non-Player Command Sender cannot execute this SubCommand!!", null); }	}
	
	public static class NoPermissionError extends CommonError { public NoPermissionError() { super(2, "Do not Have Permission to execute this Command!", null); } }
	
}
