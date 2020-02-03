package jwk.suddensurvival.proxy;

import java.util.Map;
import java.util.function.BiFunction;

import com.google.common.collect.Maps;

import cpw.mods.fml.common.event.*;
import jwk.suddensurvival.ModInfo;
import jwk.suddensurvival.block.Blocks;
import jwk.suddensurvival.item.Items;

public abstract class CommonProxy {
	Map<String, String> a = Maps.newConcurrentMap();
	
	public void onFMLLifeCycleEvent(FMLPreInitializationEvent e) {
		ModInfo.setupMetadata(e.getModMetadata());
		
		Items.registerItems();
		Blocks.registerBlocks();
		
		a.put("a", "a");
		a.put("b", "a");
		a.put("c", "a");
		a.put("d", "a");
		
		a.compute("a", new BiFunction<String, String, String>() {

			@Override
			public String apply(String t, String u) {
				return u + "d";
			}
			
		});
		a.compute("b", new BiFunction<String, String, String>() {

			@Override
			public String apply(String t, String u) {
				return u + "d";
			}
			
		});
		a.compute("c", new BiFunction<String, String, String>() {

			@Override
			public String apply(String t, String u) {
				return u + "d";
			}
			
		});
		a.compute("d", new BiFunction<String, String, String>() {

			@Override
			public String apply(String t, String u) {
				return u + "d";
			}
			
		});
	}
	
	public void onFMLLifeCycleEvent(FMLInitializationEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLPostInitializationEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerAboutToStartEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerStartingEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerStartedEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerStoppingEvent e) {
		
	}
	
	public void onFMLLifeCycleEvent(FMLServerStoppedEvent e) {
		
	}
	
}
