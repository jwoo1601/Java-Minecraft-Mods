package jw.minecraft.utility.permissions;

/**
 * This Permission equals OP in Vanilla Minecraft
 * @author jwoo
 *
 */
public class PermissionOP implements IPermission {

	@Override
	public String getId() {
		return "op";
	}

	@Override
	public String getName() {
		return "OP";
	}

}
