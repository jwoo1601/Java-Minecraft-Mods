package jwk.minecraft.garden.client;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.util.ITexture;
import jwk.minecraft.garden.client.util.SimpleSkinTexture;
import jwk.minecraft.garden.client.util.SkinLoader;
import jwk.minecraft.garden.client.util.TaskSkinLoad;
import jwk.minecraft.garden.util.NBTUserProfile;

@SideOnly(Side.CLIENT)
public class TaskManager {
	
	private static TaskManager instance = null;
	
	public static TaskManager instance() {
		
		if (instance == null) {
			
			synchronized(TaskManager.class) {
				instance = new TaskManager();
			}
		}
		
		return instance;
	}
	
	//////////////////////////////////////////////////////////////////////////

	private TaskManager() { }
	
	private SkinLoader skinLoaderObj = new SkinLoader();
	private Thread threadSkinLoad = new Thread(skinLoaderObj, "Skin Loader");
	
	public void startSkinLoader() {
		
		if (threadSkinLoad.isAlive())
			return;
		
		skinLoaderObj.shouldRun = true;
		threadSkinLoad.setDaemon(true);
		threadSkinLoad.start();
	}
	
	public void pauseSkinLoader() {
		
		if (threadSkinLoad.isAlive() && skinLoaderObj.shouldRun) {
			skinLoaderObj.shouldRun = false;
			System.out.println("Skin Loader Paused");
		}
	}
	
	public void resumeSkinLoader() {
		
		if (threadSkinLoad.isAlive() && !skinLoaderObj.shouldRun) {
			skinLoaderObj.shouldRun = true;
			System.out.println("Skin Loader Restarted");
		}
	}
	
	public void addTaskSkinLoad(TaskSkinLoad task) {
		skinLoaderObj.scheduledTasks.offer(task);
	}
	
	public boolean isSkinLoaderStarted() { return threadSkinLoad.isAlive(); }
	
	public boolean isSkinLoaderRunning() { return threadSkinLoad.isAlive() && skinLoaderObj.shouldRun; }
	
	public int getScheduledSize() { return skinLoaderObj.scheduledTasks.size(); }
	
	private Map<UUID, SimpleSkinTexture> skinCacheMap = new ConcurrentHashMap<UUID, SimpleSkinTexture> ();
	
	public boolean hasCache(UUID id) {
		return skinCacheMap.containsKey(id);
	}

	public SimpleSkinTexture putCache(SimpleSkinTexture texture) {
		return skinCacheMap.put(texture.getProfile().getId(), texture);
	}
	
	public SimpleSkinTexture getCache(UUID id) {
		return skinCacheMap.get(id);
	}
	
	public SimpleSkinTexture removeCache(UUID id) {
		return skinCacheMap.remove(id);
	}
	
}
