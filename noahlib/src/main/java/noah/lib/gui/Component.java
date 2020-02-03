package noah.lib.gui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

	Priority priority() default Priority.NORMAL;
	
	public static enum Priority {
		HIGH(1),
		NORMAL(0),
		LOW(2);
		
		private final int index;
		
		Priority(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
	}
}