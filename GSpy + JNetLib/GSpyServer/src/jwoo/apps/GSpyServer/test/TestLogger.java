package jwoo.apps.GSpyServer.test;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestLogger
{
	public static void init(PrintStream outputStream, PrintStream errorOutputStream, boolean bEnableFileOutput, String logFilePath)
	{
		bInitialized = true;
		
		if (outputStream == null)
		{
			stdout = System.out;
		}
		
		else
		{
			stdout = outputStream;
		}
		
		if (errorOutputStream == null)
		{
			stderr = System.err;
		}
		
		else
		{
			stderr = errorOutputStream;
		}
		
		bFileOutput = bEnableFileOutput;
		outputFilePath = logFilePath;
		
		if (outputFilePath == null)
		{
			bFileOutput = false;
		}
		
		else
		{
			Path path = Paths.get(outputFilePath);
			
			try
			{
				if (Files.exists(path))
				{
					BasicFileAttributes view = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
					FileTime creationTime = view.creationTime();
					SimpleDateFormat formatter = new SimpleDateFormat(" -yyyy.MM.dd-HH.mm.ss");

					StringBuilder builder = new StringBuilder();
					String logFileName = path.getFileName().toString();
					String newLogFileName = null;
					
					int lastDotIndex = logFileName.lastIndexOf('.');
					if (lastDotIndex == -1)
					{
						newLogFileName = builder.append(logFileName)
												.append(formatter.format(new Date(creationTime.toMillis()))).toString();
					}
					
					else
					{
						newLogFileName = builder.append(logFileName.substring(0, lastDotIndex))
												.append(formatter.format(new Date(creationTime.toMillis())))
												.append(logFileName.substring(lastDotIndex)).toString();
					}

					Path newPath = path.getParent().resolve(newLogFileName);
					Files.move(path, newPath);
				}
				
				Files.createDirectories(path.getParent());
				Files.createFile(path);
				
				FileTime currentFileTime = FileTime.fromMillis(System.currentTimeMillis());				
				BasicFileAttributeView attr = Files.getFileAttributeView(path, BasicFileAttributeView.class);
				attr.setTimes(currentFileTime, currentFileTime, currentFileTime);
			}
			
			catch (Exception e)
			{
				bFileOutput = false;
				outputFilePath = null;
				
				e.printStackTrace(stderr);
			}
			
			if (bFileOutput)
			{
				try
				{
					outputFileWriter = new PrintWriter(outputFilePath);
				}
				
				catch (Exception e)
				{
					e.printStackTrace(stderr);
				}
			}
		}
	}
	
	public static void cleanup()
	{
		stdout = null;
		bFileOutput = false;

		if (outputFilePath != null)
		{
			try
			{
				Files.setLastModifiedTime(Paths.get(outputFilePath), FileTime.fromMillis(System.currentTimeMillis()));
			}

			catch (Exception e)
			{
				e.printStackTrace(stderr);
			}
			
			outputFilePath = null;			
		}
		
		stderr = null;
		
		if (outputFileWriter != null)
		{
			outputFileWriter.close();
			outputFileWriter = null;
		}		

		bInitialized = false;
	}
	
	public static void Log(String message, Object...args)
	{
		printLine(stdout, formatLogMessage(message, args));
	}
	
	public static void Error(String message, Object...args)
	{
		printLine(stderr, formatErrorMessage(message, args));
	}
	
	private static void printLine(PrintStream outputStream, String message)
	{
		if (outputStream != null)
		{
			outputStream.println(message);
			
			if (bFileOutput && outputFileWriter != null)
			{
				outputFileWriter.println(message);
				outputFileWriter.flush();
			}
		}
	}
	
	private static String formatLogMessage(String message, Object...args)
	{
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm:ss:SSS]");
		String dateFormatString = formatter.format(now);
		
		return String.format("%s %s", dateFormatString, String.format(message, args));
	}
	
	private static String formatErrorMessage(String message, Object...args)
	{
		return String.format("[Error] %s", formatLogMessage(message, args));
	}
	
	private static boolean bInitialized;
	
	public static boolean isInitialized()
	{
		return bInitialized;
	}
	
	private static PrintStream stdout;
	
	public static PrintStream getOutputStream()
	{
		return stdout;
	}
	
	private static PrintStream stderr;
	
	public static PrintStream getErrorOutputStream()
	{
		return stderr;
	}
	
	private static boolean bFileOutput;
	
	public static boolean IsFileOutputEnabled()
	{
		return bFileOutput;
	}
	
	public static void setFileOutputEnabled(boolean bEnable)
	{
		bFileOutput = bEnable;
	}
	
	private static String outputFilePath;
	
	public static String getLogFilePath()
	{
		return outputFilePath;
	}
	
	private static PrintWriter outputFileWriter;
}
