package caramel.attribute;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

public class ListAttribute<T> extends AbstractAttribute<List<T>> {

	public ListAttribute(@Nonnull String key, @Nonnull List<T> list) {
		super(key, list);
	}
	
	public void append(T element) {
		value.add(element);
	}
	
	public T removeAt(int index) {
		return value.remove(index);
	}
	
	public void set(int index, T element) {
		value.set(index, element);
	}
	
	public T get(int index) {
		return value.get(index);
	}
	
	public boolean isEmpty() {
		return value.isEmpty();
	}
	
	public int getSize() {
		return value.size();
	}
	
	public Iterator<T> getIterator() {
		return value.iterator();
	}
	
	public void clear() {
		value.clear();
	}

}
