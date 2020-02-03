package jwk.minecraft.garden.util;

import java.lang.reflect.Field;

public class ReflectionHelper {

	public static <O, T> O getValue(Class<T> clazz, T self, String fieldName) {
		Field[] declared = clazz.getDeclaredFields();
		Field target = null;
		
		for (Field field : declared) {
			
			if (field.getName().equals(fieldName)) {
				target = field;
				break;
			}
		}
		
		if (target != null) {
			target.setAccessible(true);
			
			try {
				return (O) target.get(self);
			}
			
			catch (Exception e) { e.printStackTrace(); }
		}
		
		return null;
	}
	
}
