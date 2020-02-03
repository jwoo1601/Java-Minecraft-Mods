package caramel.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

public class ReflectionUtil {

	public static final List<Field> getAllAnnotatedFields(Class<?> clazz, Class<? extends Annotation> annotation) {
		List<Field> currentFields = getAllFieldsUpTo(clazz);
		List<Field> result = Lists.newArrayList();
		
		for (Field field : currentFields) {
			
			if (field.isAnnotationPresent(annotation))
				result.add(field);
		}
		
		return result;
	}
	
	public static final List<Field> getAllFieldsUpTo(Class<?> clazz) {		
		List<Field> currentFields = Lists.newArrayList(clazz.getDeclaredFields());
		Class<?> parentClass = clazz.getSuperclass();
		
		if (parentClass != null) {
			List<Field> parentFields = getAllFieldsUpTo(parentClass);
			
			if (!parentFields.isEmpty())
				currentFields.addAll(parentFields);	
			}
		
		return currentFields;
	}
	
	public static final Field getFieldUpTo(Class<?> clazz, String fieldName) {
		List<Field> fields = getAllFieldsUpTo(clazz);
		
		Field target = null;
		if (fields != null) {
			
			for (Field field : fields) {
				
				if (field.getName().equals(fieldName)) {
					target = field;
					break;
				}
			}
		}
		
		return target;
	}
	
}
