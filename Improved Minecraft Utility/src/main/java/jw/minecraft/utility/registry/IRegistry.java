package jw.minecraft.utility.registry;

import java.util.Collection;

import javax.annotation.Nonnull;

public interface IRegistry<K, V> {
	
	boolean register(@Nonnull K key, @Nonnull V value);
	
	boolean unregister(@Nonnull K key);
	
	V get(@Nonnull K key);
	
	boolean contains(@Nonnull K key);
	
	Collection<V> values();
	
}