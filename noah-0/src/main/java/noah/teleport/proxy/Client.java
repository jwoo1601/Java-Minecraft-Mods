package noah.teleport.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import noah.teleport.block.BlockTeleport;
import noah.teleport.block.TeleportData;
import noah.teleport.gui.GuiTeleport;
import noah.teleport.place.Place;
import noah.teleport.place.PlaceRegistry;
import noah.teleport.util.Common;

public class Client extends CommonProxy {
	
	public static Item BlockTeleportItem;
	
	@Override
	public void fmlLifeCycleEvent(FMLInitializationEvent e) {
		super.fmlLifeCycleEvent(e);
		
		BlockTeleportItem = GameRegistry.findItem(Common.MOD_ID, BlockTeleport.REG_NAME);
		ModelResourceLocation BlockTeleportItemLoc = new ModelResourceLocation(Common.MOD_ID + ":" + BlockTeleport.REG_NAME, "Inventory");
		final int DEFAULT_ITEM_SUBTYPE = 0;
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(BlockTeleportItem, DEFAULT_ITEM_SUBTYPE, BlockTeleportItemLoc);
	}
	
	@Override
	public void openTeleportGui(TeleportData data) {
		Minecraft.getMinecraft().displayGuiScreen(new GuiTeleport(data));
	}
}
