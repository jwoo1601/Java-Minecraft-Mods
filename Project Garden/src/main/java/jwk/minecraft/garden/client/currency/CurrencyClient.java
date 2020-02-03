package jwk.minecraft.garden.client.currency;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.annotation.Nonnull;
import javax.vecmath.Color4f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.font.Fonts;
import jwk.minecraft.garden.client.renderer.RenderFrame;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.client.util.ColorUtil;
import jwk.minecraft.garden.client.util.FlowerClientUtil;
import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.util.IManaged;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class CurrencyClient implements IManaged {
	
	private static final Color4f BACK_RECT_COLOR = new Color4f(0.f, 0.f, 0.f, 0.4f);
	
	public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ModInfo.ID, "textures/currency/coin.png");
	public static final TextureInfo TEXTURE = new TextureInfo(TEXTURE_LOCATION, 9, 9);
	
	private RenderableObject renderObj = new RenderableObject(TEXTURE, 9, 9, 9, 9, 0, 0, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final long MAX_RAW_DISPLAY_AMOUNT = 1000000000000L;
	
	public boolean isVisible = true;

	private RenderFrame frameObj;
	private RenderableObject currencyIcon;
	private CurrencyString currencyString;
	
	private ICurrency currency;
	private int Y_Width;
	
	public CurrencyClient(ICurrency currency) {
		this.currency = currency;
		frameObj = new RenderFrame();
		currencyIcon = renderObj.clone();
		frameObj.attach("icon", currencyIcon);
		currencyString = new CurrencyString();
	}
	
	public void init() {
		Y_Width = Fonts.fontDohyeon.getWidth(currency.getRepresentingSign()) / 2;
	}
	
	public void onSetValue(long value) {
		currencyString.setValue(value);
	}
	
	public void render(@Nonnull ScaledResolution resolution) {
		if (!isVisible)
			return;
		
		float sw = resolution.getScaledWidth();
		float sh = resolution.getScaledHeight();
		
		float startX = sw / 2 + 17;
		float startY = sh - 33;
		
		currencyIcon.setVector(startX, startY - 2, 0.0F);
		
		float rectX = sw / 2 + 15;
		float rectY = startY  - 3;
		
		RenderFrame.drawRect(rectX, rectY, 0.F, 72.F, 11.F, BACK_RECT_COLOR);
		
		float width = Y_Width;		
		float YDStartX = sw / 2 + 82 - width;
		float YDStartY = startY - 2.5F;

		Fonts.fontDohyeon.drawString(currency.getRepresentingSign(), YDStartX, YDStartY, Color.ORANGE);
		
		if (currencyString.getValue() >= 10000000L) {
			int fontWidth = currencyString.getFontWidth();
			float valueStartX = YDStartX - 2 - fontWidth;
			Fonts.fontDohyeon.drawString(currencyString.getDisplayString(), valueStartX, YDStartY, Color.ORANGE);
		}
		
		else {
			String displayString = CurrencyString.toDefaultFormat(currencyString.getValue());
			float valueStartX = YDStartX - 2 - Fonts.fontDohyeon.getWidth(displayString);
			Fonts.fontDohyeon.drawString(displayString, valueStartX, YDStartY, Color.ORANGE);
		}
		
		frameObj.doRender();
		
		glEnable(GL_DEPTH_TEST);
	}	
	
	@Override
	public void onLoad() {	}

	@Override
	public void onUnload() {
		currencyString.reset();
	}

	@Override
	public void onSave() {	}
	
}
