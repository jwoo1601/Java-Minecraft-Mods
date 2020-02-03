package jwk.minecraft.garden.team;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jwk.minecraft.garden.currency.CurrencyYD;
import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.currency.ICurrencyManager;
import jwk.minecraft.garden.currency.team.TeamCurrencyManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;

public class SimpleTeam implements ITeam {

	private String name;
	private ITeamManager manager;
	private ICurrencyManager currencyManager;
	
	public SimpleTeam() { this("tmp"); }
	
	public SimpleTeam(@Nonnull String name) {
		this.name = checkNotNull(name);
		this.manager = new TeamManager(this);
	}

	@Override
	public String getTeamName() { return name; }

	@Override
	public ITeamManager getManager() { return manager; }

	@Override
	public NBTBase toNBT() {
		return new NBTTagString(name);
	}

	@Override
	public void fromNBT(NBTBase nbtbase) {
		this.name = ((NBTTagString) nbtbase).func_150285_a_();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof ITeam) {
			ITeam team = (ITeam) obj;
			
			return name.equals(team.getTeamName());
		}
		
		return false;
	}

}
