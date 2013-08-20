package org.primitive.interfaces;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.primitive.exceptions.BehaviorException;
/**
 * @author s.tihomirov
 *
 */
public interface ITestObjectExceptionHandler 
{
	public Object handleException(Object object, Method originalMethod, MethodProxy methodProxy, Object[] args, Throwable t) throws BehaviorException, Throwable;
}
