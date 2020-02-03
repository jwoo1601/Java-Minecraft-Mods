package jwk.minecraft.garden.client.flower;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.flower.FlowerProperty;
import jwk.minecraft.garden.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class FlowerInfo {

	public static final TextureInfo FLOWER_LANGUAGE_TEXTURE = new TextureInfo(new ResourceLocation(ModInfo.ID, "textures/flowerlanguage/flowerlanguage.png"), 1024, 1024);
	
//	public static final float DOUBLE_V_UP_OFFSET = 1.5f;
	public static final float DOUBLE_V_OFFSET = 2.5f;
	
	public static final float H_OFFSET = 0.5f;
	public static final float V_OFFSET = 1.0f;
	public static final float D_OFFSET = 0.5f;
	
	public final boolean IsVisible;
	public final boolean IsDoublePlant;	
	public final RenderableObject RenderObj;	
	public final EnumFacing Facing;
	
	public FlowerInfo(@Nonnull FlowerProperty property) {
		checkNotNull(property);
		EnumFlowerType type = EnumFlowerType.fromInteger(property.getFlowerType());
		BlockPos pos = property.getPosition();
		
		IsVisible = property.getVisible();
		IsDoublePlant = type.IsDoublePlant;
		RenderObj = type.FlowerObj.clone().setVector(pos.getX(), pos.getY(), pos.getZ());
		Facing = property.getFacing();
	}
	
	public FlowerInfo(boolean isDoublePlant, @Nonnull RenderableObject obj, @Nonnull EnumFacing facing) {
		this(true, isDoublePlant, obj, facing);
	}
	
	public FlowerInfo(boolean isVisible, boolean isDoublePlant, @Nonnull RenderableObject obj, @Nonnull EnumFacing facing) {
		IsVisible = isVisible;
		IsDoublePlant = isDoublePlant;
		RenderObj = checkNotNull(obj);
		Facing = checkNotNull(facing);
	}
	
}
