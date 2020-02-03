package jw.minecraft.utility.blockguard.player;

import java.lang.ref.SoftReference;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import jw.minecraft.utility.math.PipedComponent;
import jw.minecraft.utility.math.Vec3i;
import jw.minecraft.utility.exception.ElementAlreadyExistException;
import jw.minecraft.utility.math.PipedComponent;

public abstract class PlayerCache {
	
	public static ConcurrentHashMap<UUID, PipedComponent> cache = new ConcurrentHashMap<UUID, PipedComponent>();
	
	public static void put(@Nonnull UUID uid, int ox, int oy, int oz, int ex, int ey, int ez) throws Exception {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid))
			return;
//			throw new ElementAlreadyExistException(uid.toString());
		else
			cache.put(uid, new PipedComponent(Vec3i.createVector(ox, oy, oz), Vec3i.createVector(ex, ey, ez)));
	}
	
	public static void put(@Nonnull UUID uid, Vec3i origin, Vec3i end) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid))
			return;
//			throw new ElementAlreadyExistException(uid.toString());
		else
			cache.put(uid, new PipedComponent(origin, end));
	}
	
	public static void put(@Nonnull UUID uid, PipedComponent target) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid))
			return;
//			throw new ElementAlreadyExistException(uid.toString());
		else {
			if (target == null)
				cache.put(uid, new PipedComponent());
			else
				cache.put(uid, target);
		}
	}
	
	public static boolean contains(@Nonnull UUID uid) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		return cache.containsKey(uid);
	}
	
	public static PipedComponent getPlayerCache(@Nonnull UUID uid) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid))
			return cache.get(uid);
		else
			return null;
//			throw new NoSuchElementException(uid.toString());
	}
	
	public static void replace(@Nonnull UUID uid, PipedComponent target) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid)) {
			if (target == null)
				cache.replace(uid, new PipedComponent());
			else
				cache.replace(uid, target);
		}
		else
			return;
//			throw new NoSuchElementException(uid.toString());
	}
	
	public static PipedComponent remove(@Nonnull UUID uid) {
		if (uid == null)
			throw new NullPointerException("uid");
		
		if (cache.containsKey(uid))
			return cache.remove(uid);
		else
			return null;
//			throw new NoSuchElementException(uid.toString());
	}
	
	public static boolean isEmpty() {
		return cache.isEmpty();
	}
	
	public static int getSize() {
		return cache.size();
	}
}
