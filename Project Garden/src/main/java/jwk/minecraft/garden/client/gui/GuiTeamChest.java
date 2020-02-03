package jwk.minecraft.garden.client.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.network.PacketDelivery;
import jwk.minecraft.garden.network.PacketDelivery.Action;
import jwk.minecraft.garden.team.container.ContainerTeamChest;
import jwk.minecraft.garden.team.inventory.InventoryTeamChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiTeamChest extends GuiContainer {
	
    private static final ResourceLocation VANILLA_CHEST_LOCATION = new ResourceLocation("textures/gui/container/generic_54.png");
    
    private InventoryPlayer inventoryPlayer;
    private InventoryTeamChest inventoryTeam;
    
    private int inventoryRows;

    public GuiTeamChest(InventoryPlayer invPlayer, InventoryTeamChest invTeam)
    {
        super(new ContainerTeamChest(invPlayer, invTeam));
        inventoryPlayer = invPlayer;
        inventoryTeam = invTeam;
        this.allowUserInput = false;
        short s = 222;
        int i = s- 108;
        this.inventoryRows = invTeam.getSizeInventory() / 9;
        this.ySize = i + this.inventoryRows * 18;
    }
    
    @Override
    public void initGui() {
    	super.initGui();
    	
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        int startX = k + xSize + 10;
        int startY = l + 20;
    	this.buttonList.add(new GuiButton(0, startX, startY, 80, 20, "배송하기"));
    	this.buttonList.add(new GuiButton(1, startX, startY + 30, 60, 20, "알림 보내기"));
    }
    
    @Override
    public void actionPerformed(GuiButton target) {
    	
    	if (target.id == 0)
    		ProjectGarden.NET_HANDLER.sendToServer(new PacketDelivery(Action.DELIVER, Minecraft.getMinecraft().thePlayer.getUniqueID()));
    	else if (target.id == 1)
    		ProjectGarden.NET_HANDLER.sendToServer(new PacketDelivery(Action.NOTIFY, Minecraft.getMinecraft().thePlayer.getUniqueID()));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(inventoryTeam.hasCustomInventoryName() ? inventoryTeam.getInventoryName() : I18n.format(inventoryTeam.getInventoryName(), new Object[0]), 8, 6, 4210752);
        fontRendererObj.drawString(inventoryPlayer.hasCustomInventoryName() ? inventoryPlayer.getInventoryName() : I18n.format(inventoryPlayer.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(VANILLA_CHEST_LOCATION);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }

}
