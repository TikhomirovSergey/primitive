package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * A default interceptor for any {@link TestObject}
 *
 */
public class DefaultInterceptor implements MethodInterceptor {

	public DefaultInterceptor(){
		super();
	}
	
	@Override
	public Object intercept(Object testObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try {
			return proxy.invokeSuper(testObj, args);
		} catch (Exception e) {
			return ((TestObject) testObj).exceptionHandler.handleException(testObj, method, proxy, args,
					e);
		}
	}

}
