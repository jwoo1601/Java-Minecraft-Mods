package jwk.minecraft.garden.currency;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.util.INBTConvertable;
import jwk.minecraft.garden.util.INBTSerializable;

public interface ICurrency {
	
	ICurrencyManager getManager();
	
	String getDisplayName();
	
	String getRepresentingSign();
	
}
