package jwk.minecraft.garden.client.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.google.common.collect.Queues;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.team.TeamDataClient;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class SkinLoader implements Runnable {

	public final Queue<TaskSkinLoad> scheduledTasks = Queues.newConcurrentLinkedQueue();
	
	private final int MAX_TRY_COUNT = 50;	
	private final long ExecutionPeriod = 10000L;
	
	private long elapsedTime = 0L;
	private long lastSystemTime = 0L;
	
	public volatile boolean shouldRun = false;
	
	@Override
	public void run() {
		
		while(true) {
			
			if (!shouldRun)
				continue;
			
			long currentSystemTime = System.currentTimeMillis();
			elapsedTime += (currentSystemTime - lastSystemTime);
			lastSystemTime = currentSystemTime;
			
			if (elapsedTime >= ExecutionPeriod)
				elapsedTime = 0L;
			else
				continue;
			
			if (scheduledTasks.isEmpty())
				continue;
			
			TaskSkinLoad target = scheduledTasks.poll();
			
			if (target.isTerminated())
				continue;
			
			MinecraftSessionService service = Minecraft.getMinecraft().func_152347_ac();
			GameProfile profile = service.fillProfileProperties(target.getProfile(), true);
			Map<Type, MinecraftProfileTexture> map = service.getTextures(profile, true);
			
			if (map == null || map.isEmpty()) { handleError(profile, target); continue; }
			
			MinecraftProfileTexture mc_texture = map.get(Type.SKIN);
			
			if (mc_texture == null) { handleError(profile, target); continue; }
			
			String hash = mc_texture.getHash();
			File imgFile = extractPath(hash);
			
			if (!imgFile.exists()) { handleError(profile, target); continue; }
			
			try { loadSkinImage(imgFile, profile, target); }
			catch (IOException e) { handleError(profile, target); continue;  }
		}
	}
	
	private void handleError(GameProfile profile, TaskSkinLoad task) {
		task.onSkinLoadFailed(profile);
		
		if (task.getCount() >= MAX_TRY_COUNT)
			task.terminate();
		
		else {
			task.updateCount();
			scheduledTasks.offer(task);
		}
		
		ProjectGarden.logger.info("Skin Load Failed: Name=" + profile.getName() + " Count=" + task.getCount());
	}
	
	private void handleSuccess(GameProfile profile, PlayerSkinCache cache, TaskSkinLoad task) {
		task.onSkinLoadSucceeded(profile, cache);
		task.updateCount();
		task.terminate();
		
		ProjectGarden.logger.info("Skin Load Succeeded: Name=" + profile.getName());
	}
	
	private File extractPath(String hash) {
		return new File(".\\assets\\skins\\" + hash.substring(0, 2) + "\\" + hash);
	}
	
	private void loadSkinImage(File imageFile, GameProfile profile, TaskSkinLoad task) throws IOException {
		BufferedImage image = ImageIO.read(imageFile);
		int width = image.getWidth(), height = image.getHeight();		
        int[] pixels = new int[width * height];
        
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        for(int y = 0; y < height; y++){
        	
            for(int x = 0; x < width; x++){
                int pixel = pixels[y * width + x];
                
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip();
        
        handleSuccess(profile, new PlayerSkinCache(buffer, width, height), task);
	}

}
