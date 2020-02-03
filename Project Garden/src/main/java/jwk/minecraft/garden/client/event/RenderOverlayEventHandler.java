package jwk.minecraft.garden.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public class RenderOverlayEventHandler {

	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre e) {

		switch (e.type) {
		
		case JUMPBAR:
			e.setCanceled(true);
			break;
		
		case HEALTHMOUNT:
			e.setCanceled(true);
			break;
			
		case HEALTH:
			e.setCanceled(true);
			break;
			
		case CHAT:
			ProjectGarden.proxy.getTeamClient().render(e.resolution);
			ProjectGarden.proxy.getCurrencyClient().render(e.resolution);
			break;
			
		case FOOD:
			e.setCanceled(true);
			break;
			
		case ARMOR:
			e.setCanceled(true);
			break;
			
		case EXPERIENCE:
			e.setCanceled(true);
			break;
			
		default:
			break;
		}	
	}
	
//	@SubscribeEvent
//	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post e) {
//		
//		switch (e.type) {
//		
//		case HEALTH:
//
//			break;
//			
//		default:
//			break;
//		}	
//	}
	
}
