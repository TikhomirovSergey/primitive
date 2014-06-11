package org.primitive.model.abstraction;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.proxy.DefaultInterceptor;

/**
 * 
 * A default interceptor for any {@link ModelObject}
 *
 */
public class ModelObjectInterceptor extends DefaultInterceptor {	
	@Override
	public Object intercept(Object modelObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		try {
			return super.intercept(modelObj, method, args, proxy);
		} catch (Exception e) {
			return ((ModelObject) modelObj).exceptionHandler.handleException(modelObj, method, proxy, args,
					e);
		}
	}

}
