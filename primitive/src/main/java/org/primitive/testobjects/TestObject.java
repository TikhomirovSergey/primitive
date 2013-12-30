package org.primitive.testobjects;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.interfaces.ITestObjectExceptionHandler;
import org.primitive.logging.Log;
import org.primitive.testobjects.decomposition.IDecomposable;
import org.primitive.testobjects.exceptionhandler.TestObjectExceptionHandler;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.primitive.webdriverencapsulations.webdrivercomponents.Awaiting;
import org.primitive.webdriverencapsulations.webdrivercomponents.BrowserLogs;
import org.primitive.webdriverencapsulations.webdrivercomponents.ScriptExecutor;

public abstract class TestObject implements IDestroyable, IDecomposable
{	
	/**
	 * @author s.tihomirov
	 *	It should intercept invocation of TestObject and its inheritors methods
	 */
	public abstract static class Interceptor implements MethodInterceptor {

		public Interceptor()
		{
			super();
		}
		
		//methods that should support its working:
		//handles exceptions that has been caught
		protected Object handleException(TestObject testObject, Method originalMethod, MethodProxy methodProxy, Object[] args, Throwable t) throws Throwable
		{
			return testObject.exceptionHandler.handleException(testObject, originalMethod, methodProxy, args, t);
		}
		
		@Override
		public abstract Object intercept(Object arg0, Method arg1, Object[] arg2,
				MethodProxy arg3) throws Throwable;

	}

	protected SingleWindow nativeWindow; //browser window that object placed on
	protected WebDriverEncapsulation driverEncapsulation; //wrapped web driver for situations when it needs to be used
	
	protected Awaiting awaiting;
	protected ScriptExecutor scriptExecutor;
	protected BrowserLogs logs;
	protected final HashSet<TestObjectExceptionHandler> checkedInExceptionHandlers = 
			new HashSet<TestObjectExceptionHandler>();	
	
	//this will be invoked when some exception is caught out 
    private ITestObjectExceptionHandler exceptionHandler = (ITestObjectExceptionHandler) Proxy.newProxyInstance(
        	IExtendedWebDriverEventListener.class.getClassLoader(),
            new Class[] {ITestObjectExceptionHandler.class },
            new InvocationHandler() 
        	{
        		
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
                {
                	//it needs to know exception
                	Throwable t = (Throwable) args[4];
                    for (TestObjectExceptionHandler handler : checkedInExceptionHandlers) 
                    {
                    	//it looks for the suitable handler
                    	if (handler.isThrowableInList(t.getClass()))
                    	{
                    		try
                    		{
                    			return method.invoke(handler, args);
                    		}
                    		catch (Exception e) {
                    			continue; //it wasn't the suitable handler
    						}
                    	}
                    }
                	//if there are no suitable handlers
                	throw t;                    
                }
            }
        );

	final List<TestObject> children = Collections.synchronizedList(new ArrayList<TestObject>());
	protected TestObject(SingleWindow browserWindow) throws ConcstructTestObjectException
    {
    	try
    	{
			nativeWindow = browserWindow;
			driverEncapsulation = nativeWindow.getDriverEncapsulation();
			awaiting           = driverEncapsulation.getAwaiting();
	    	scriptExecutor = driverEncapsulation.getScriptExecutor();
	    	logs     = driverEncapsulation.getLogs();
    	}
    	catch (Exception e)
    	{
    		throw new ConcstructTestObjectException("Test object form hasn't been constructed. You can get the reason of the error " +
    			" for situation analysis", e);
    	} 	
    }
    
    @SuppressWarnings("unchecked")
	protected static <T extends IDecomposable> T getProxy(Class<T> clazz, Class<? extends Interceptor> interceptorClass, Class<?>[] paramClasses, Object[] paramValues) throws ConcstructTestObjectException
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
    
    public void destroy()
    {
    	for (TestObject child: children)
    	{
    		child.destroy();
    	}    	
    	children.clear(); 
    	try {
			this.finalize();
		} catch (Throwable e) {
			Log.warning("A problem with destroying of " + this.getClass().getSimpleName() + " instance has been found out! "+e.getMessage(),e);
		}
    }
	
	
	public void checkInExceptionHandler(TestObjectExceptionHandler exceptionHandler)
	{
		checkedInExceptionHandlers.add(exceptionHandler);
	}
	
	public void checkOutExceptionHandler(TestObjectExceptionHandler exceptionHandler)
	{
		checkedInExceptionHandlers.remove(exceptionHandler);
	}

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass);

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass, Integer frameIndex);

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass, String pathToFrame);

	void addChild(TestObject child)
	{
		children.add(child);
	}

	@Override
	public abstract <T extends IDecomposable> T getPart(Class<T> partClass, String pathToFrame, Long timeOutInSec);
}	

