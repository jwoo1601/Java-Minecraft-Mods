package jwk.minecraft.garden.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.renderer.SimpleRenderer2D;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.network.PacketDelivery;
import jwk.minecraft.garden.network.PacketDelivery.Action;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import jwk.minecraft.garden.warp.ContainerWarp;
import jwk.minecraft.garden.warp.InventoryWarp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiWarp extends GuiContainer {
	
    private static final ResourceLocation VANILLA_CHEST_LOCATION = new ResourceLocation("textures/gui/container/generic_54.png");
    
    private static final ResourceLocation PJGARDEN_LOGO = new ResourceLocation(ModInfo.ID, "textures/misc/logo.png");
    
    private SimpleRenderer2D renderer = new SimpleRenderer2D();
    
    private static final int LOGO_WIDTH = 642;
    private static final int LOGO_HEIGHT = 500;
    
    private static final int LOGO_R_WIDTH = 160;
    private static final int LOGO_R_HEIGHT = 125;
    
    private static final int OFFSET = 15;

    private InventoryWarp inventoryWarp;
    
    private int upperHeight = 17;
    private int lowerHeight = 25;

    public GuiWarp(InventoryWarp invWarp)
    {
        super(new ContainerWarp(invWarp));
        inventoryWarp = invWarp;
        this.allowUserInput = false;
        this.ySize = upperHeight + lowerHeight;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	
    	if (inventoryWarp.hasCustomInventoryName())
    		fontRendererObj.drawString(inventoryWarp.getInventoryName(), 8, 76, 4210752); // 6
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int lx = (this.width - LOGO_R_WIDTH + 10) / 2;
        int ly = (this.height - this.ySize - OFFSET - LOGO_R_HEIGHT) / 2;
        
        mc.getTextureManager().bindTexture(PJGARDEN_LOGO);
        renderer.render(lx, ly, LOGO_WIDTH, LOGO_HEIGHT, 0, 0, LOGO_WIDTH, LOGO_HEIGHT, LOGO_R_WIDTH, LOGO_R_HEIGHT);

        int k = (this.width - this.xSize) / 2;
        int l = ly + LOGO_R_HEIGHT + OFFSET;
        
        mc.getTextureManager().bindTexture(VANILLA_CHEST_LOCATION);
        drawTexturedModalRect(k, l, 0, 0, xSize, upperHeight);
        drawTexturedModalRect(k, l + upperHeight, 0, 197, xSize, lowerHeight);
    }

}
