package jwk.minecraft.garden.team;

import jwk.minecraft.garden.currency.ICurrency;
import jwk.minecraft.garden.currency.ICurrencyManager;
import jwk.minecraft.garden.util.INBTConvertable;
import jwk.minecraft.garden.util.INBTSerializable;

public interface ITeam extends INBTConvertable {

	String getTeamName();
	
	ITeamManager getManager();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object obj);
	
}
