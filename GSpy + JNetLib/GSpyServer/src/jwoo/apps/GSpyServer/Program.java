package jwoo.apps.GSpyServer;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.zip.CRC32;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import jwoo.apps.GSpyServer.packets.PacketVersion;
import jwoo.apps.GSpyServer.test.TestApp;

public class Program
{
	public static void main(String[] args)
	{
		//TestApp testApp = new TestApp();
		//testApp.Execute(args);
		
		/* int value = 0b10110110100101110010010110111011;
		byte[] values = new byte[] { 0x32, 0x5A, 0x4C, 0x1F };
	
		long crc_start = System.currentTimeMillis();
		
		CRC32 crc = new CRC32();
		crc.update(values);
		long crc_checksum = crc.getValue();
		
		long crc_end = System.currentTimeMillis();
		
		System.out.printf("CRC32 Elapsed Time: %dms", crc_end - crc_start);
		

		try
		{
			long md5_start = System.currentTimeMillis();
			
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] hash = md5.digest(values);
			
			long md5_end = System.currentTimeMillis();			
			
			System.out.printf("MD5 Elapsed Time: %dms", md5_end - md5_start);
			
			long sha_start = System.currentTimeMillis();
			
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hash2 = sha.digest(values);
			
			long sha_end = System.currentTimeMillis();			
			
			System.out.printf("SHA-1 Elapsed Time: %dms\n", sha_end - sha_start);
		}
		
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		
		long l = Integer.MAX_VALUE, l2 = 0b1111111111111111111111111111111100000000000000000000000000000000L, l3 = 0b0000000000000000000000000000000011111111111111111111111111111111L;
		long l4 = 0b1111111111111111111111111111111011111111111111111111111111111101L;
		int i2 = (int) (l4 & 0xFFFFFFFFL);
		
		byte[] bytes = new byte[] { 25, 10, 25, 3 };
		
		for (byte b : bytes)
		{
			System.out.printf("%s ", Integer.toBinaryString(b));
		}
		
		System.out.println();
		
		int sb = ((int)bytes[0]) << 25 | ((int)bytes[1]) << 17 | ((int)bytes[2]) << 9 | ((int)bytes[3]);
		
		System.out.println(Integer.toBinaryString(sb));
		
		int i = (int) l;
		
		Consumer<Integer> TempDecoder = (n) -> 
		{
			int[] extInts = new int[] { 0xFF000000, 0x00FF0000, 0x0000FF00, 0x000000FF };
			byte result = 0;
			
			switch (n)
			{
			case 1:
				result = (byte) Integer.rotateLeft(sb & extInts[n-1], 7);
				break;
			case 2:
				result = (byte) Integer.rotateLeft(sb & extInts[n-1], 15);
				break;
			case 3:
				result = (byte) ((sb & extInts[n-1]) >>> 9);
				break;
			case 4:
				result = (byte) (sb & extInts[n-1]);
				break;
				
				default:
					break;
			}
			
			System.out.println(Integer.toBinaryString(extInts[n-1]) + " / " + result + " | " + Integer.toBinaryString(result) + " | 0x" + Integer.toHexString(result));
		};

		for (int k = 1; k < 5; k++)
		{
			TempDecoder.accept(k);
		}
		
		long start = System.nanoTime();
		byte t = (byte) Integer.rotateLeft(sb & 0xFF000000, 7);
		long end = System.nanoTime();
		
		System.out.println(t);
		
		long diff1 = end - start;
		
		start = System.nanoTime();
		t = (byte) ((sb & 0x00FF0000) >>> 17);
		end = System.nanoTime();
		
		long diff2 = end - start;
		
		System.out.println(t + " & 1: " + diff1 + " 2: " + diff2);
		
		// 00000000000000000000000000000000
		long C1 = 0b10000000000000000000000000000001L, C2 = 0b0000000000000000000000000000000010000000000000000000000000000001L;
		
		System.out.println(Long.toBinaryString(Long.MAX_VALUE) + " " + Long.toBinaryString(C1 << 32 | C1) + " " +  Integer.toBinaryString((int)(C1 << 32 | C2) >>> 32)); */
		
		/* byte b1 = (byte) Integer.rotateLeft(sb & 0xFF000000, 7);
		System.out.println(Integer.toBinaryString(0xFF000000) + " / " + b1 + " | " + Integer.toBinaryString(b1) + " | 0x" + Integer.toHexString(b1)); */
		
		//System.out.printf("\n%d\n%s\n%d\n", Long.BYTES, Integer.toBinaryString(sb), l3);
		
//		System.out.printf("CRC32 Checksum: value=%s, checksum=%s", Integer.toBinaryString(value), Long.toBinaryString(crc_checksum));
	
		try {
			byte[] buf = ObjectUtils.convertToBytes(new PacketVersion((byte)18, (byte)1, (byte)16, (byte)1));
			
			System.out.printf("length: %d\ndata:", buf.length);
			
			for (byte b : buf)
			{
				System.out.print(b + " ");
			}
			
			System.out.println();

			System.out.println(Charset.forName("UTF-8").decode(ByteBuffer.wrap(buf)).toString());
			PacketVersion version = ObjectUtils.convertFromBytes(buf);
			
			System.out.println(version);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class A implements Serializable
	{
		private static final long serialVersionUID = 5102419072560417461L;
		
		public A(int a, int b)
		{
			this.a = a;
			this.b = b;
		}
		
		@Override
		public String toString()
		{
			return String.format("a=%d, b=%d", a, b);
		}
		
		private int a;
		private int b;
	}

}
