package jwk.minecraft.garden.client.util;

import static jwk.minecraft.garden.flower.Flowers.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.vecmath.Color4f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.flower.FlowerProperty;
import net.minecraft.block.Block;

@SideOnly(Side.CLIENT)
public class FlowerClientUtil {

	public static final RenderableObject OBJ_DANDELION = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 0, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_POPPY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 0, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_BLUE_ORCHID = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 0, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_ALLIUM = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 0, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_HOUSTONIA = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 129, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_RED_TULIP = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 129, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_ORANGE_TULIP = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 129, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_WHITE_TULIP = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 129, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_PINK_TULIP = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 257, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_OXEYE_DAISY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 257, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_DOUBLE_LILAC = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 257, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_DOUBLE_SUNFLOWER = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 257, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_DOUBLE_ROSE_BUSH = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 385, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_DOUBLE_PEONY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 385, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_BLACK_ROSE = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 385, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_GRAY_PANSY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 385, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_BROWN_PANSY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 513, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_BLUE_BELL = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 513, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_IRIS = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 513, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_GARDENIA = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 513, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_BLOWBALL = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 641, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_THISTLE = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 641, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_ENZIAN = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 641, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_ORCHID = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 641, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_FOXGLOVES = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 769, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_LILY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 257, 769, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_PEONY = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 513, 769, new Color4f(1.f, 1.f, 1.f, 1.f));
	public static final RenderableObject OBJ_RED_ROSE = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 769, 769, new Color4f(1.f, 1.f, 1.f, 1.f));
	
	public static final RenderableObject OBJ_CHRYSANTHEME = new RenderableObject(FlowerInfo.FLOWER_LANGUAGE_TEXTURE, 256, 128, 2, 1, 0, 897, new Color4f(1.f, 1.f, 1.f, 1.f));
	
//	public static FontManager fontManager;
//	
//	public static int FONT_MISAENG_INDEX = 0;
//	public static int FONT_MISAENG_BOLD_INDEX = 0;
	
}
