package jw.minecraft.utility.event;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nonnull;

import cpw.mods.fml.common.eventhandler.EventBus;
import jw.minecraft.utility.LogHelper;
import jw.minecraft.utility.permissions.Permissions;
import net.minecraftforge.common.MinecraftForge;

public class EventHandlerRegistry {
	
	private  Set<Class<? extends EventHandler>> set = new HashSet<Class<? extends EventHandler>>();
	
	public boolean register(@Nonnull Class<? extends EventHandler> handler) {
		if (handler == null)
			throw new NullPointerException("Class<? extends EventHandler> handler");
		
		return set.add(handler);
	}
	
	public boolean unregister(@Nonnull Class<? extends EventHandler> handler) {
		if (handler == null)
			throw new NullPointerException("Class<? extends EventHandler> handler");
		
		return set.remove(handler);
	}
	
	public void post() {
		if (!set.isEmpty()) {
			Iterator<Class<? extends EventHandler>> iter = set.iterator();
		
			while (iter.hasNext()) {
				try {
					EventHandler handler = iter.next().newInstance();
					handler.getCurrentBus().register(handler);
					LogHelper.info("Registered EventHandler <class=" + handler.getClass() + ", bus=" + handler.getCurrentBus() + ">");
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
				}
			}
			
			set.clear();
		}
	}
	
	public boolean isEmpty() {
		return set.isEmpty();
	}
	
	public boolean contains(@Nonnull Class<? extends EventHandler> handler) {
		return set.contains(handler);
	}
	
	public void clear() {
		set.clear();
	}
	
	
	
	private EventHandlerRegistry() {}
	
	private volatile static EventHandlerRegistry instance = null;
	
	public static EventHandlerRegistry getInstance() {
		if (instance == null) {
			synchronized(EventHandlerRegistry.class) {
				instance = new EventHandlerRegistry();
			}
		}
		
		return instance;
	}
}
