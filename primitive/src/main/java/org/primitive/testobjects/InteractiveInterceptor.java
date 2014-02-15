package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public final class InteractiveInterceptor implements MethodInterceptor {

	public InteractiveInterceptor() {
		super();
	}

	@Override
	public synchronized Object intercept(Object page, Method method,
			Object[] args, MethodProxy methodProxy) throws Throwable {
		if (method.isAnnotationPresent(FunctionalPart.InteractiveMethod.class)) { 
			// if there are actions with a page 
			((FunctionalPart) page).switchToMe();
		}
		try {
			return methodProxy.invokeSuper(page, args);
		} catch (Exception e) {
			return ((FunctionalPart) page).exceptionHandler.handleException(page, method, methodProxy,
					args, e);
		}
	}
}
