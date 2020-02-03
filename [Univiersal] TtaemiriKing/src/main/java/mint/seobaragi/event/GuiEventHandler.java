package mint.seobaragi.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import mint.seobaragi.gui.GuiDirt;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiEventHandler
{
	static final GuiDirt GuiClass = new GuiDirt();
	
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRenderOverlay(RenderGameOverlayEvent event)
	{
		if(event.type == ElementType.ALL)
		{
			GuiClass.doRender(event.resolution);
		}
	}
}
