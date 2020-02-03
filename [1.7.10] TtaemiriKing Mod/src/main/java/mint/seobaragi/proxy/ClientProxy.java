package mint.seobaragi.proxy;

import api.player.render.RenderPlayerAPI;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.common.MinecraftForge;
import library.Reference;
import mint.seobaragi.SeonengItems;
import mint.seobaragi.entity.EntityDust;
import mint.seobaragi.event.GuiEventHandler;
import mint.seobaragi.renderer.RenderPlayerSkin;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		RenderPlayerAPI.register(Reference.MODID, RenderPlayerSkin.class);
		RenderingRegistry.registerEntityRenderingHandler(EntityDust.class, new RenderSnowball(SeonengItems.throwableDust));
	}
	
	@Override
	public void registerGuiEventHandler()
	{
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
	}
}
