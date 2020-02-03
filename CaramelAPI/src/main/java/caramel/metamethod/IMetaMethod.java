package caramel.metamethod;

import caramel.system.SystemStack;

public interface IMetaMethod<R> {
	
	R call(SystemStack stack);
	
}
