package jw.minecraft.utility.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public abstract class PermissionManager {
	
	private static Map<String, IPermission> map = new ConcurrentHashMap<String, IPermission>();
	
	public static boolean set(@Nonnull EntityPlayer player, @Nonnull IPermission permission) {
		if (player == null)
			throw new NullPointerException("EntityPlayer player");
		
		return set(player.getDisplayName(), permission);
	}
	
	public static boolean set(@Nonnull String playerName, @Nonnull IPermission permission) {
		if (playerName == null)
			throw new NullPointerException("String playerName");
		else if (permission == null)
			throw new NullPointerException("IPermission permission");
		
		if (Permissions.getInstance().equals(permission, Permissions.getInstance().DEFAULT)) {
			if (map.containsKey(playerName))
				map.remove(playerName);
			
			return true;
		}
		else {
			if (map.containsKey(playerName)) {
				IPermission p = map.get(playerName);
				
				if (!Permissions.getInstance().equals(p, permission))
					map.replace(playerName, permission);
			}
			else
				map.put(playerName, permission);
			
			return true;
		}
	}
	
	public static IPermission get(@Nonnull EntityPlayer player) {
		if (player == null)
			throw new NullPointerException("EntityPlayer player");
		
		return get(player.getDisplayName());
	}
	
	public static IPermission get(@Nonnull String playerName) {
		if (playerName == null)
			throw new NullPointerException("String playerName");
		
		if (map.containsKey(playerName))
			return map.get(playerName);
		else
			return Permissions.getInstance().DEFAULT;
	}	
		
	public static boolean hasPermission(@Nonnull IPermission permission, @Nonnull EntityPlayer player) {
		if (player == null)
			throw new NullPointerException("EntityPlayer player");
		
		return hasPermission(permission, player.getDisplayName());
	}
	
	public static boolean hasPermission(@Nonnull IPermission permission, @Nonnull String playerName) {
		if (permission == null)
			throw new NullPointerException("IPermission permission");
		else if (playerName == null)
			throw new NullPointerException("String playerName");
		
		if (Permissions.getInstance().equals(permission, Permissions.getInstance().DEFAULT))
			return true;
		else if (Permissions.getInstance().equals(permission, Permissions.getInstance().OP))
			return isOp(playerName);
		else
			return map.containsKey(playerName) && Permissions.getInstance().equals(map.get(playerName), permission);
	}
	
	public static final boolean isOp(String username) {
		String[] userlist = MinecraftServer.getServer().getConfigurationManager().func_152606_n();
		
		for (String name : userlist) {
			if (name.equals(username))
				return true;
		}
		
		return false;
	}
}
