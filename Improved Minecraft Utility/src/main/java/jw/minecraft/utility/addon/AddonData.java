package jw.minecraft.utility.addon;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import jw.minecraft.utility.command.CommandData;

public class AddonData {
	
	public AddonData(@Nonnull String id, @Nonnull String name, @Nonnull String description, @Nonnull String version, @Nonnull IUnit unit, @Nonnull CommandData command) {
		checkNotNull(id, "id must not be null!");
		checkNotNull(name, "name must not be null!");
		checkNotNull(version, "version must not be null!");
		checkNotNull(description, "description must not be null!");
		checkNotNull(unit, "unit must not be null!");
		checkNotNull(command, "command must not be null!");
		
		Id = id;
		Name = name;
		Description = description;
		Version = version;
		Unit = unit;
		Command = command;
	}
	
	public final String Id;
	
	public final String Name;
	
	public final String Description;
	
	public final String Version;
	
	public final IUnit Unit;
	
	public final CommandData Command;
	
}
