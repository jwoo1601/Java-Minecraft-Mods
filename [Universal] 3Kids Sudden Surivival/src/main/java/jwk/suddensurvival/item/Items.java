package jwk.suddensurvival.item;

import cpw.mods.fml.common.registry.GameRegistry;
import jwk.suddensurvival.block.Blocks;
import net.minecraft.item.Item;

public class Items {
	
	public static final Item3Kids _3KIDS = new Item3Kids();

	public static void registerItems() {
		GameRegistry.registerItem(_3KIDS, _3KIDS.NAME);
	}
	
}
