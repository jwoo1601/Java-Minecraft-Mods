package jwk.minecraft.garden.client.team;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import jwk.minecraft.garden.team.ITeam;
import jwk.minecraft.garden.team.ITeamManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;

@SideOnly(Side.CLIENT)
public class FakeTeam implements ITeam {
	
	private String name;
	
	public FakeTeam(@Nonnull String teamName) {
		this.name = checkNotNull(teamName);
	}

	@Override
	public NBTBase toNBT() {
		return new NBTTagString(name);
	}

	@Override
	public void fromNBT(NBTBase nbtbase) {
		this.name = ((NBTTagString) nbtbase).func_150285_a_();
	}

	@Override
	public String getTeamName() { return name; }

	@Override
	public ITeamManager getManager() { return null; }

}
