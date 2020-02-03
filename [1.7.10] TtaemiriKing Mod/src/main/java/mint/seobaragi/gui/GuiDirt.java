package mint.seobaragi.gui;

import static library.Location.SEONENG_GUI;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;

import javax.vecmath.Color4f;

import org.lwjgl.opengl.GL11;

import mint.seobaragi.properties.PropertyPlayerStat;
import mint.seobaragi.renderer.RenderNumber;
import mint.seobaragi.renderer.RenderableObject;
import mint.seobaragi.renderer.StandardRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.management.PlayerProfileCache;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDirt extends Gui
{
	int a = 35;
	
	private static final RenderableObject OBJECT = new RenderableObject(0, 0, 256, 256, SEONENG_GUI);
	private static final StandardRenderer RENDERER = new StandardRenderer(Tessellator.instance, OBJECT);
	
	public GuiDirt() {}
	
	public void doRender(ScaledResolution resolution)
	{		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		PropertyPlayerStat stat = (PropertyPlayerStat) player.getExtendedProperties(ID);

		int x = resolution.getScaledWidth() -32 -a;
		int y = resolution.getScaledHeight() /3;
		
//		차례대로 화면상 x좌표, y좌표, 그릴 사각형 가로사이즈, 세로, 그릴 텍스쳐의 시작 x좌표 (좌측상단) y좌표, 그릴 텍스쳐의 끝 x좌표 (우측하단) y좌표, 그릴색깔
//		RENDERER.render2DWithCustomScaledAndSized(x, y - 50, 32, 32, 0, 0, 32, 32, new Color4f(1.f, 1.f, 1.f, .5f));
//		위에꺼가 물 배경
//		RENDERER.render2DWithCustomScaledAndSized(x, y - 50, 32, 32, 0, 33, 31, 64, new Color4f(1.f, 1.f, 1.f, .7f));
//		위에꺼가 물 파란색 안
//		RENDERER.render2DWithCustomScaledAndSized(x, y + 50, 32, 32, 32, 0, 64, 32, new Color4f(1.f, 1.f, 1.f, .5f));
//		위에꺼가 더러움 배경
//		RENDERER.render2DWithCustomScaledAndSized(x, y + 50, 32, 32, 33, 33, 64, 64, new Color4f(1.f, 1.f, 1.f, .7f));
//		위에꺼가 검은색 안
		
		
		
		//색상 설정 : 1.0 = 일반값 = 255값
		Minecraft.getMinecraft().getTextureManager().bindTexture(SEONENG_GUI);
		
		//x y u v wigth height
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.5f); // 맨끝부분이 알파값 즉 투명도
		this.drawTexturedModalRect(x, y - 50, 0, 0, 64, 64);	//그림 그릴좌표 설정 - WaterLevel
		this.drawTexturedModalRect(x, y + 50, 64, 0, 64, 64);	//그림 그릴좌표 설정 - DirtLevel
		GL11.glColor4f(1.0F, 0.5F, 0.0F, 0.5F);
		RenderNumber.drawNumber(stat.waterLevel, (int) (x + 17.7f), (int) (y - 23.5f - 5), 0.8f);
		GL11.glColor4f(0.7F, 0.0F, 1.0F, 0.5F);
		RenderNumber.drawNumber(stat.dirtLevel, (int) (x + 17.7f), (int) (y + 23.5f + 49), 0.8f);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
