package mint.seobaragi.proxy;

import api.player.render.RenderPlayerAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import library.Reference;
import mint.seobaragi.SeonengItems;
import mint.seobaragi.entity.EntityDust;
import mint.seobaragi.event.GuiEventHandler;
import mint.seobaragi.packet.PacketPlayerStat;
import mint.seobaragi.packet.SeonengPacket;
import mint.seobaragi.renderer.RenderPlayerSkin;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;

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
	
	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}
