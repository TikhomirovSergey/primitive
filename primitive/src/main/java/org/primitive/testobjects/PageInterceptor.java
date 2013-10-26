package org.primitive.testobjects;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.testobjects.testobject.TestObject.Interceptor;

public final class PageInterceptor extends Interceptor {

	public PageInterceptor()
	{
		super();
	}
	
	@Override
	public synchronized Object intercept(Object page, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		if (method.isAnnotationPresent(Page.PageMethod.class))
		{	//if there are actions with a page
			((Page) page).switchToMe();
		}
		try
		{
			return methodProxy.invokeSuper(page, args);
		}
		catch (Exception e)
		{
			return handleException((Page) page, method, methodProxy, args, e);
		}
	}
}
