package jw.minecraft.utility.callback;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

	/**
	 * If type is {@link CallbackType.DEFAULT},
	 * Caller won't send any parameters. (SO DO NOT USE THEM)
	 * If not, Callback Methods must receive {@link jw.minecraft.utility.catchable.Catchable Catchable}
	 * (or derived class of that)
	 * For example :
	 * <pre>{@code
	 * public void onCallback(Catchable c) {
	 * // Something to do when get called
	 * }
	 * }
	 *</pre>
	 * @author jwoo
	 *
	 */
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Callback {
		
		CallbackType type() default CallbackType.DEFAULT;
		
	}
