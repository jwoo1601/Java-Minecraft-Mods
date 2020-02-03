package jwk.minecraft.garden.client.event;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.gui.GuiMap;
import net.minecraft.client.Minecraft;

@SideOnly(Side.CLIENT)
public class InputEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onKeyDown(InputEvent.KeyInputEvent e) {
		
		if (Minecraft.getMinecraft().currentScreen == null && Keyboard.isCreated() && Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_M)
			Minecraft.getMinecraft().displayGuiScreen(new GuiMap());
	}
	
}
