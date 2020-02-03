package caramel.metamethod;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;

import javax.annotation.Nonnull;

import caramel.math.Tuple2;
import caramel.math.Tuple3;
import caramel.metamethod.exception.MethodProcessException;
import caramel.metamethod.exception.NoSuchFieldException;
import caramel.reflection.ReflectionUtil;
import caramel.system.SystemStack;

public class MetaMemberOf implements IMetaMethod<Field> {

	public static final String NAME = "memberof";
	
	@Override
	public Field call(@Nonnull SystemStack stack) {
		checkNotNull(stack);
		
		if (stack.isEmpty())
			throw new EmptyStackException();
		else if (stack.size() < 3)
			throw new MethodProcessException("The Size of stack must be greater than or equal to 3");
		
		String parMemberName = null, parObjectName = null;
		
		try {
			parMemberName = (String) stack.pop().getObject();
			parObjectName = (String) stack.pop().getObject();
		}
		
		catch (Exception e) { throw new MethodProcessException(e); }
		
		Object self = stack.pop().getObject();

		Field field = ReflectionUtil.getFieldUpTo(self.getClass(), parObjectName);
		
		if (field == null)
			throw new NoSuchFieldException(parObjectName);
		
		Field memberField = ReflectionUtil.getFieldUpTo(field.getType(), parMemberName);
		
		if (memberField == null)
			throw new NoSuchFieldException(parObjectName + "." + parMemberName);
		
		return memberField;
	}
	
}
