package jw.minecraft.utility.permissions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;

public class Permissions {
	
	public final PermissionDefault DEFAULT = new PermissionDefault();
	public final PermissionAdmin ADMIN = new PermissionAdmin();
	public final PermissionOP OP = new PermissionOP();
	
	private Permissions() {}
	
	public boolean equals(@Nonnull IPermission src, @Nonnull IPermission dest) {
		if (src == null)
			throw new NullPointerException("IPermission src");
		else if (dest == null)
			throw new NullPointerException("IPermission dest");
		
		return src.getId().equals(dest.getId());
	}
	
	
	private volatile static Permissions instance = null;
	
	public static Permissions getInstance() {
		if (instance == null) {
			synchronized(Permissions.class) {
				instance = new Permissions();
			}
		}
		
		return instance;
	}
}
