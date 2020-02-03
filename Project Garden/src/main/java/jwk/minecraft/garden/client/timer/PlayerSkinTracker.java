package jwk.minecraft.garden.client.timer;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ProjectGarden;
import jwk.minecraft.garden.timer.TickTimer;
import net.minecraft.client.entity.AbstractClientPlayer;

@SideOnly(Side.CLIENT)
public class PlayerSkinTracker extends TickTimer {

	private AbstractClientPlayer player;
	
	public PlayerSkinTracker(@Nonnull AbstractClientPlayer player) {
		super(10L);
		
		this.player = checkNotNull(player);
	}

	@Override
	protected void update() {
		ProjectGarden.logger.info("update Tracker elapsed=" + this.elapsedTicks() + " : name=" + player.getCommandSenderName());
		
		if (!player.getLocationSkin().equals(AbstractClientPlayer.locationStevePng)) {
			
			ProjectGarden.logger.info("update Tracker -success: name=" + player.getCommandSenderName());
			
			ProjectGarden.proxy.getClientTickListenerList().removeListener(this);
		}
	}

}
