package jwk.minecraft.garden.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.shop.ShopData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;

@SideOnly(Side.CLIENT)
public class GuiSaleShopSetup extends GuiShopSetup {

	private static final ResourceLocation RES_SALESHOP_IMG = new ResourceLocation(ModInfo.ID, "textures/gui/sale_shop.png");
	private static final TextureInfo TEXTURE_SALESHOP = new TextureInfo(RES_SALESHOP_IMG, 2048, 2048);
	private static final RenderableObject OBJ_SALESHOP = new RenderableObject(TEXTURE_SALESHOP, 1566, 1920);
	
	private static final ResourceLocation RES_NPC_IMG = new ResourceLocation(ModInfo.ID, "textures/gui/npc.png");
	private static final TextureInfo TEXTURE_NPC = new TextureInfo(RES_NPC_IMG, 1024, 2048);
	private static final RenderableObject OBJ_NPC = new RenderableObject(TEXTURE_NPC, 1024, 2048);
	
	public GuiSaleShopSetup() { super(OBJ_NPC, OBJ_SALESHOP); slotSize = 9; }

	@Override
	protected void initSlots() {
		
    	for (int i=0; i < 9; i++) { 
    		float[] pos = getSlotPosition(i);
    		
			Slots[i] = new GuiSlot(mc, i, shopData.getSlot(i), pos[0], pos[1], slotRealWidth, slotRealHeight, slotInnerRealWidth, slotInnerRealHeight, true);
    	}
	}
    
}
