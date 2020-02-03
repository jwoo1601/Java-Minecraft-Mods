package jw.minecraft.utility.transformers;

import java.lang.annotation.Annotation;

public interface ITransformer<T, V> {
	
	V transform(T t);
	
}
