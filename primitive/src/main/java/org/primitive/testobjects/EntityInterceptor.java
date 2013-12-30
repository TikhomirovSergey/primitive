package org.primitive.testobjects;

import java.lang.reflect.Method;

import org.primitive.testobjects.TestObject.Interceptor;

import net.sf.cglib.proxy.MethodProxy;

public final class EntityInterceptor extends Interceptor{
	public EntityInterceptor()
	{
		super();
	}
	
	@Override
	public Object intercept(Object entity, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		try
		{
			return methodProxy.invokeSuper(entity, args);
		}
		catch (Exception e)
		{
			return handleException((Entity) entity, method, methodProxy, args, e);
		}
	}
}
