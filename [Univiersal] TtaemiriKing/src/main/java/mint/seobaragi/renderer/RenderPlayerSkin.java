package mint.seobaragi.renderer;

import mint.seobaragi.properties.PropertyPlayerStat;
import net.minecraft.client.entity.AbstractClientPlayer;
import library.SkinType;
import api.player.render.RenderPlayerAPI;
import api.player.render.RenderPlayerBase;
import static library.Location.SKIN1;
import static library.Location.SKIN2;
import static library.Location.SKIN3;
import static library.Location.SKIN4;
import static library.Location.SKIN5;
import static mint.seobaragi.properties.PropertyPlayerStat.ID;


public class RenderPlayerSkin extends RenderPlayerBase
{
	public SkinType currentType;
	
	public RenderPlayerSkin(RenderPlayerAPI API)
	{
		super(API);
	}
	
	@Override
	public void renderPlayer(AbstractClientPlayer ACP, double p1, double p2, double p3, float p4, float p5)
	{
		currentType = SkinType.DEFAULT;
		super.renderPlayer(ACP, p1, p2, p3, p4, p5);
		PropertyPlayerStat target = (PropertyPlayerStat) ACP.getExtendedProperties(ID);
		currentType = SkinType.getTypeFromInt(target.dirtLevel);
		super.renderPlayer(ACP, p1, p2, p3, p4, p5);		
	}
	 
	public void loadTexture(net.minecraft.util.ResourceLocation paramResourceLocation)
	{
		switch (currentType)
		{
		case DEFAULT:
			super.loadTexture(paramResourceLocation);			 
			break;
			
		case SKIN_OVERLAY_FIRST:
			super.loadTexture(SKIN1);
			break;
			 
		case SKIN_OVERLAY_SECOND:
			super.loadTexture(SKIN2);
			break;
			 
		case SKIN_OVERLAY_THIRD:
			super.loadTexture(SKIN3);
			break;
			 
		case SKIN_OVERLAY_FOURTH:
			super.loadTexture(SKIN4);
			break;
			 
		case SKIN_OVERLAY_FIFTH:
			super.loadTexture(SKIN5);
			break;
			
			default:
				super.loadTexture(paramResourceLocation);			 
				break;
		}
	}
}
