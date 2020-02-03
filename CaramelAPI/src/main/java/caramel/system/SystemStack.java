package caramel.system;

import java.util.Deque;
import java.util.LinkedList;

public class SystemStack {

	private final Deque<SystemObject> stack = new LinkedList<SystemObject>();
	private int currentPosition = 0;
	
	public final <T extends Object> SystemObject<T> push(Object object) {
		SystemObject sysobj = new SystemObject(currentPosition++, object);
		stack.addFirst(sysobj);
		
		return sysobj;
	}
	
	public final SystemObject pop() {
		SystemObject object = stack.pollFirst();
		--currentPosition;
		
		return new SystemObject(-1, object.getObject());
	}
	
	public final SystemObject retrieve() {
		return stack.peekFirst();
	}
	
	public final void clear() {
		stack.clear();
	}
	
	public final int size() {
		return stack.size();
	}
	
	public final boolean isEmpty() {
		return stack.isEmpty();
	}
	
}
