package org.primitive.webdriverencapsulations.components.bydefault;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.proxy.DefaultInterceptor;

class ComponentInterceptor extends DefaultInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		return method.invoke(((WebdriverInterfaceImplementor) obj).delegate, args);
	}

}
