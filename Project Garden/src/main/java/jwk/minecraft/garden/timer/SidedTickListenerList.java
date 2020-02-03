package jwk.minecraft.garden.timer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;

import cpw.mods.fml.relauncher.Side;

public class SidedTickListenerList {

	private final Queue<ITickListener> ListenerList = Queues.newConcurrentLinkedQueue();
	private final Side Side;
	
	public SidedTickListenerList(@Nonnull Side side) {
		Side = checkNotNull(side);
	}
	
	public ITickListener addListener(@Nonnull ITickListener listener) {
		
		if (ListenerList.add(listener))
			return listener;
		
		return null;
	}
	
	public ITickListener removeListener(@Nonnull ITickListener listener) {
		
		if (ListenerList.remove(listener))
			return listener;
		
		return null;
	}
	
	public boolean isScheduled(@Nonnull ITickListener listener) {
		return ListenerList.contains(listener);
	}
	
	public void updateListeners() {
		
		for (ITickListener listener : ListenerList)
			listener.onUpdate();
	}
	
	public void removeAll() {
		
		for (int i=0; i < ListenerList.size(); i++)
			ListenerList.remove(i);
	}
	
	public Iterator<ITickListener> iterator() { return ListenerList.iterator(); }
	
	public List<ITickListener> getListenerList() { return Lists.<ITickListener>newArrayList(ListenerList.iterator()); }
	
}
