package jwoo.apps.GSpyMonitor.fonts;

import javafx.scene.text.Font;

public class FontData
{
	public FontData(FontType Type, String Path)
	{
		type = Type;
		path = Path;
		size = 0.0;
	}
	
	public FontData(FontType Type, String Path, double Size)
	{
		type = Type;
		path = Path;
		size = Math.min(0.0, Size);
	}
	
	
	
	
	private FontType type;
	private String path;
	private double size;
}
