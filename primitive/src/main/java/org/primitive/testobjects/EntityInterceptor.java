package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class EntityInterceptor implements MethodInterceptor {
	public EntityInterceptor() {
		super();
	}

	@Override
	public Object intercept(Object entity, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		try {
			return methodProxy.invokeSuper(entity, args);
		} catch (Exception e) {
			return ((Entity) entity).exceptionHandler.handleException(entity, method, methodProxy, args,
					e);
		}
	}
}
