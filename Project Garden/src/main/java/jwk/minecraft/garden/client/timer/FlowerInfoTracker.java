package jwk.minecraft.garden.client.timer;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.client.flower.FlowerInfo;
import jwk.minecraft.garden.timer.TickTimer;

@SideOnly(Side.CLIENT)
public class FlowerInfoTracker extends TickTimer {

	private FlowerInfo target;
	
	public FlowerInfoTracker(@Nonnull FlowerInfo target) {
		super(300L);
		
		this.target = checkNotNull(target);
	}

	@Override
	protected void update() {
		ProjectGarden.proxy.removeDisplayFlowerInfo(target);
		target = null;
		ProjectGarden.proxy.getSidedTickListenerList().removeListener(this);
	}

}
