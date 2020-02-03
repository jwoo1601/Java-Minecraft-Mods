package jwk.minecraft.garden.client.flower;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.util.BlockPos;
import jwk.minecraft.garden.util.IManaged;

@SideOnly(Side.CLIENT)
public class FlowerInfoCache implements IManaged {
	
	private boolean isLoaded = false;

	private final Map<BlockPos, FlowerInfo> map = new ConcurrentHashMap<BlockPos, FlowerInfo>();
	
	public FlowerInfoCache inject(@Nonnull FlowerInfo info) {
		checkNotNull(info);
		
		BlockPos pos = new BlockPos(info.RenderObj.getVector());
		map.put(pos, info);
		
		return this;			
	}
	
	public FlowerInfoCache injectAll(@Nonnull FlowerInfo[] array) {
		checkNotNull(array);
		
		if (array.length != 0) {
			
			for (FlowerInfo info : array) {
				BlockPos pos = new BlockPos(info.RenderObj.getVector());
				
				map.put(pos, info);
			}
		}
		
		return this;
	}
	
	public FlowerInfoCache replace(@Nonnull FlowerInfo info) {
		checkNotNull(info);
		
		BlockPos pos = new BlockPos(info.RenderObj.getVector());
		
		if (map.containsKey(pos))
			map.put(pos, info);
		
		return this;
	}
	
	public FlowerInfoCache remove(@Nonnull BlockPos pos) {
		checkNotNull(pos);
		
		if (map.containsKey(pos))
			map.remove(pos);
		
		return this;
	}
	
	public FlowerInfo get(@Nonnull BlockPos pos) {
		checkNotNull(pos);
		
		return map.get(pos);
	}
	
	public int size() { return map.size(); }
	
	public boolean isEmpty() { return map.isEmpty(); }

	@Override
	public void onLoad() {
		
		if (!isLoaded)
			isLoaded = true;
	}

	@Override
	public void onUnload() {
		
		if (isLoaded) {
			map.clear();
			isLoaded = false;
		}
	}

	@Override
	public void onSave() {	}
	
}
