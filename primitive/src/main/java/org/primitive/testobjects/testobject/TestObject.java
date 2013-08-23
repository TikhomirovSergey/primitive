package org.primitive.testobjects.testobject;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.primitive.exceptions.BehaviorException;
import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.interfaces.ITestObjectExceptionHandler;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public abstract class TestObject implements IDestroyable
{	
	/**
	 * @author s.tihomirov
	 *	It should intercept invocation of TestObject and its inheritors methods
	 */
	protected abstract static class Interceptor implements MethodInterceptor {

		public Interceptor()
		{
			super();
		}
		
		//methods that should support its working:
		//handles exceptions that has been caught
		protected Object handleException(TestObject testObject, Method originalMethod, MethodProxy methodProxy, Object[] args, Throwable t) throws BehaviorException, Throwable
		{
			//if there is no handlers that checked in 
			if (testObject.checkedInExceptionHandlers.size() == 0)
			{
				throw t; //we throw this exception
			}
			try
			{   //if there is some handlers we try to return any result
				return testObject.exceptionHandler.handleException(testObject, originalMethod, methodProxy, args, t);
			}
			catch (BehaviorException e1)
			{
				throw e1;
			}
			catch (Exception e2) 
			{
				throw e2;
			}
		}
		
		@Override
		public abstract Object intercept(Object arg0, Method arg1, Object[] arg2,
				MethodProxy arg3) throws Throwable;

	}

	protected SingleWindow nativeWindow; //browser window that object placed on
	protected WebDriverEncapsulation driverEncapsulation; //wrapped web driver for situations when it needs to be used
	
	protected WebDriverEncapsulation.Awaiting awaiting;
	protected WebDriverEncapsulation.ScriptExecutor scriptExecutor;
	protected final HashSet<ITestObjectExceptionHandler> checkedInExceptionHandlers = 
			new HashSet<ITestObjectExceptionHandler>();	
	
	//this will be invoked when some exception is caught out 
    private ITestObjectExceptionHandler exceptionHandler = (ITestObjectExceptionHandler) Proxy.newProxyInstance(
        	IExtendedWebDriverEventListener.class.getClassLoader(),
            new Class[] {ITestObjectExceptionHandler.class },
            new InvocationHandler() 
        	{
        		
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
                {
                	
                    for (ITestObjectExceptionHandler handler : checkedInExceptionHandlers) 
                    {
                    	method.invoke(handler, args);
                    }
                    
                    return null;
                }
            }
        );
	
	protected TestObject(SingleWindow browserWindow) throws ConcstructTestObjectException
    {
    	try
    	{
			nativeWindow = browserWindow;
			driverEncapsulation = nativeWindow.getDriverEncapsulation();
			awaiting           = driverEncapsulation.getAwaiting();
	    	scriptExecutor = driverEncapsulation.getScriptExecutor();
    	}
    	catch (Exception e)
    	{
    		throw new ConcstructTestObjectException("Test object form hasn't been constructed. You can get the reason of the error " +
    			" for situation analysis", e);
    	} 	
    }
    
    @SuppressWarnings("unchecked")
	protected static <T extends TestObject> T getProxy(Class<? extends TestObject> clazz, Class<? extends Interceptor> interceptorClass, Class<?>[] paramClasses, Object[] paramValues) throws ConcstructTestObjectException
    {	//should be closed by child class method
    	Enhancer enhancer = new Enhancer();
    	Interceptor interceptor = null;
    	try
    	{
		    interceptor = interceptorClass.newInstance();
		    enhancer.setCallback(interceptor);
		    enhancer.setSuperclass(clazz);
    	}
    	catch (Exception e)
    	{
    		throw new ConcstructTestObjectException(e.getMessage(), e);
    	}
    	
	    T objectToBeTested = (T) enhancer.create(paramClasses, paramValues);
	    return  objectToBeTested;
    }
    
    public abstract void destroy();
	
	
	public void checkInExceptionHandler(ITestObjectExceptionHandler exceptionHandler)
	{
		checkedInExceptionHandlers.add(exceptionHandler);
	}
	
	public void checkOutExceptionHandler(ITestObjectExceptionHandler exceptionHandler)
	{
		checkedInExceptionHandlers.remove(exceptionHandler);
	}
}	

