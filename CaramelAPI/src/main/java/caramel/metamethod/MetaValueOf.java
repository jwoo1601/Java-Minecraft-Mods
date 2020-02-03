package caramel.metamethod;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;

import javax.annotation.Nonnull;

import caramel.math.Tuple2;
import caramel.metamethod.exception.MethodProcessException;
import caramel.metamethod.exception.NoSuchFieldException;
import caramel.reflection.ReflectionUtil;
import caramel.system.SystemStack;

public class MetaValueOf implements IMetaMethod<Object> {

	public static final String NAME = "valueof";
	
	@Override
	public Object call(@Nonnull SystemStack stack) {
		checkNotNull(stack);
		
		if (stack.isEmpty())
			throw new EmptyStackException();
		else if (stack.size() < 2)
			throw new MethodProcessException("The Size of stack must be greater than or equal to 2");
		
		String parMemberName = null;
		
		try {
			Object obj = stack.pop().getObject();
			
			if (obj instanceof Field)
				parMemberName = ((Field) obj).getName();
			else
				parMemberName = (String) obj;
		}
		
		catch (Exception e) { throw new MethodProcessException(e); }
		
		Object self = stack.pop().getObject();
		
		Field field = ReflectionUtil.getFieldUpTo(self.getClass(), parMemberName);
		
		if (field == null)
			throw new NoSuchFieldException(parMemberName);
		
		try {
			field.setAccessible(true);
			return field.get(self);
		}
		
		catch (Exception e) { throw new MethodProcessException(e); }
	}

}
