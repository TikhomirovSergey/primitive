package org.primitive.testobjects.interfaces;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;
/**
 * @author s.tihomirov
 *
 */
public interface ITestObjectExceptionHandler 
{
	public Object handleException(Object object, Method originalMethod, MethodProxy methodProxy, Object[] args, Throwable t) throws Throwable;
}
