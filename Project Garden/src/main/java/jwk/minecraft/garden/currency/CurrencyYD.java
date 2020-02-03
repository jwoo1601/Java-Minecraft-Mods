package jwk.minecraft.garden.currency;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map.Entry;
import java.util.UUID;

import javax.vecmath.Color4f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.ModInfo;
import jwk.minecraft.garden.client.renderer.RenderableObject;
import jwk.minecraft.garden.client.renderer.TextureInfo;
import jwk.minecraft.garden.currency.player.PlayerCurrencyManager;
import jwk.minecraft.garden.currency.team.TeamCurrencyManager;
import jwk.minecraft.garden.exception.EntryAlreadyExistException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants.NBT;

public class CurrencyYD implements ICurrency {

	private ICurrencyManager manager;
	
	public static final CurrencyYD INSTANCE = new CurrencyYD();
	
	private CurrencyYD() {
		manager = new TeamCurrencyManager(this, 100);
	}

	@Override
	public ICurrencyManager getManager() {
		return manager;
	}	

	@Override
	public String getDisplayName() { return "CurrencyYD"; }

	@Override
	public String getRepresentingSign() { return "Y"; }

}
