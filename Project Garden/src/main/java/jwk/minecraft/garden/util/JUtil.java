package jwk.minecraft.garden.util;

import java.awt.Color;
import java.util.UUID;

import javax.vecmath.Color4f;

import io.netty.buffer.ByteBuf;

public class JUtil {

	public static void writeUUID(ByteBuf buf, UUID uuid) {
		buf.writeLong(uuid.getLeastSignificantBits());
		buf.writeLong(uuid.getMostSignificantBits());
	}
	
	public static UUID readUUID(ByteBuf buf) {
		long lbits = buf.readLong();
		long mbits = buf.readLong();
		
		return new UUID(mbits, lbits);
	}
	
	public static void writeString(ByteBuf buf, String str) {
		char[] array = str.toCharArray();
		
		buf.writeInt(array.length);
		
		for (char c : array)
			buf.writeChar(c);
	}
	
	public static String readString(ByteBuf buf) {
		int size = buf.readInt();
		
		char[] array = new char[size];
		
		for (int i=0; i < size; i++)
			array[i] = buf.readChar();
		
		return String.valueOf(array);
	}
	
	public static Color convertToAWTColor(Color4f color) {
		return new Color(color.x, color.y, color.z, color.w);
	}
	
	public static Color4f convertToColor4f(Color color) {
		return new Color4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}
	
}
