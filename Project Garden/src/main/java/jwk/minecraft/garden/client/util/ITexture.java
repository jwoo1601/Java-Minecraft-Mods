package jwk.minecraft.garden.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ITexture {

	void bindTexture();
	
	void deleteTexture();
	
}
