package jwk.minecraft.garden.client.font;

import java.util.LinkedHashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TextureMap<K, V> extends LinkedHashMap<K, V> {
	
	private final int maxSize;

	public TextureMap(int maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > maxSize;
	}
	
}
