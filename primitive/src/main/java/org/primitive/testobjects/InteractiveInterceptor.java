package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.testobjects.testobject.TestObject.Interceptor;

public final class InteractiveInterceptor extends Interceptor {

	public InteractiveInterceptor()
	{
		super();
	}
	
	@Override
	public synchronized Object intercept(Object page, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		if (method.isAnnotationPresent(FunctionalPart.InteractiveMethod.class))
		{	//if there are actions with a page
			((FunctionalPart) page).switchToMe();
		}
		try
		{
			return methodProxy.invokeSuper(page, args);
		}
		catch (Exception e)
		{
			return handleException((FunctionalPart) page, method, methodProxy, args, e);
		}
	}
}
