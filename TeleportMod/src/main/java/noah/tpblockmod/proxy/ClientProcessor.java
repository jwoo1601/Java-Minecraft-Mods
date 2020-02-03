package noah.tpblockmod.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import noah.tpblockmod.ModInfo;
import noah.tpblockmod.MouseTestHandler;
import noah.tpblockmod.block.BlockTeleport;

public class ClientProcessor extends CProxy {
	
	@Override
	public void Loaded(FMLInitializationEvent e) {
		Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
		Item tpBlockItem = GameRegistry.findItem(ModInfo.MOD_ID, BlockTeleport.BLOCK_UNL_NAME);
		ModelResourceLocation tpBlockItemLoc = new ModelResourceLocation(ModInfo.MOD_ID + ":" + BlockTeleport.BLOCK_UNL_NAME, "Inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(tpBlockItem, DEFAULT_ITEM_SUBTYPE, tpBlockItemLoc);
		
		MouseTestHandler h = new MouseTestHandler();
	}
}
