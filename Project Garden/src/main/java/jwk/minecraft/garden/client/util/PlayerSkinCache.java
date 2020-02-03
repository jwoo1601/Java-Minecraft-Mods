package jwk.minecraft.garden.client.util;

import java.nio.ByteBuffer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerSkinCache {

	private ByteBuffer buffer;
	
	private int width;
	private int height;
	
	public PlayerSkinCache(ByteBuffer buf, int width, int height) {
		this.buffer = buf;
		this.width = width;
		this.height = height;
	}
	
	public String getFormat() { return "PNG"; }
	
	public ByteBuffer getBuffer() { return buffer; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
}
