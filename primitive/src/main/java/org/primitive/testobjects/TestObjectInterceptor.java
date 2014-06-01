package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.proxy.DefaultInterceptor;

/**
 * 
 * A default interceptor for any {@link TestObject}
 *
 */
public class TestObjectInterceptor extends DefaultInterceptor {	
	@Override
	public Object intercept(Object testObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try {
			return super.intercept(testObj, method, args, proxy);
		} catch (Exception e) {
			return ((TestObject) testObj).exceptionHandler.handleException(testObj, method, proxy, args,
					e);
		}
	}

}
