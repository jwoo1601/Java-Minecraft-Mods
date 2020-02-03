package jwk.minecraft.fogmanager.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.fogmanager.animation.FogAnimationHelper;
import jwk.minecraft.fogmanager.enumerations.EnumFogQuality;
import jwk.minecraft.fogmanager.enumerations.EnumFogRenderOption;
import jwk.minecraft.fogmanager.utils.Color4f;

public class Configuration {

	private static Configuration instance = null;
	
	public static Configuration getInstance() {
		
		if (instance == null) {
			synchronized(Configuration.class) {
				instance = new Configuration();
			}
		}
		
		return instance;
	}
	
	public final FogConfig Default = new FogConfig(false, EnumFogQuality.HIGH, EnumFogRenderOption.NICEST, 0.0f, new Color4f(99, 58, 0, 255), 4.0f, 10.0f);
	
	@SideOnly(Side.CLIENT)
	private FogConfig fog = Default;
	
	@SideOnly(Side.CLIENT)
	public FogConfig fog() { return fog; }
	
	@SideOnly(Side.CLIENT)
	public synchronized FogConfig setFog(FogConfig target) {
		fog = target;
		return fog;
	}
	
	@SideOnly(Side.CLIENT)
	public final FogAnimationHelper AnimationHelper = new FogAnimationHelper();
	
	
	///////////////////////////////////////////LOCAL FIELDS///////////////////////////////////////////
	public static class FogConfig {
		
		private boolean isEnabled;
		
		private EnumFogQuality quality;
		
		private EnumFogRenderOption renderOption;
		
		private float density;
		
		private Color4f color;
		
		private float startDepth;
		
		private float distance;
		
		public FogConfig(EnumFogQuality quality, EnumFogRenderOption renderOption, float density, Color4f color, float startDepth, float distance) {
			this(false, quality, renderOption, density, color, startDepth, distance);
		}
		
		public FogConfig(boolean isEnabled, EnumFogQuality quality, EnumFogRenderOption renderOption, float density,
				         Color4f color, float startDepth, float distance) {
			
			this.isEnabled = isEnabled;
			this.quality = quality;
			this.renderOption = renderOption;
			this.density = density;
			this.color = color;
			this.startDepth = startDepth;
			this.distance = distance;
		}
		
		public FogConfig setState(boolean value) {
			isEnabled = value;
			return this;
		}
		
		public boolean getState() { return isEnabled; }
		
		public FogConfig setQuality(@Nonnull EnumFogQuality value) {
			this.quality = checkNotNull(value);
			return this;
		}
		
		public EnumFogQuality getQuality() { return quality; }
		
		public FogConfig setRenderOption(EnumFogRenderOption value) {
			this.renderOption = checkNotNull(value);
			return this;
		}
		
		public EnumFogRenderOption getRenderOption() { return renderOption; }
		
		public FogConfig setDensity(float value) {
			checkArgument(value >= 0.f && value <= 1.f);
			
			this.density = value;
			return this;
		}
		
		public float getDensity() { return density; }
		
		public FogConfig setColor(Color4f value) {
			color = checkNotNull(value);
			return this;
		}
		
		public Color4f getColor() { return color; }
		
		public FogConfig setStartDepth(float value) {
			checkArgument(value >= 0.f);
			
			startDepth = value;
			return this;
		}
		
		public float getStartDepth() { return startDepth; }
		
		public FogConfig setDistance(float value) {
			checkArgument(value >= 0.f);
			
			distance = value;
			return this;
		}
		
		public float getDistance() { return distance; }
		
	}
}
