package jw.minecraft.utility.permissions;

public interface IPermission {
	
	/**
	 * Gets Id for Permission (will be used to identify permissions)
	 * @return Id
	 */
	String getId();
	
	/**
	 * Gets User-Friendly Name for Permission
	 * @return User-Friendly Name
	 */
	String getName();
}
