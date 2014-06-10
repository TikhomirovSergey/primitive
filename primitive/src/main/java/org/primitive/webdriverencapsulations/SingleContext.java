package org.primitive.webdriverencapsulations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchContextException;
import org.primitive.webdriverencapsulations.eventlisteners.IContextListener;
public final class SingleContext extends Handle {

	private final List<IContextListener> contextEventListeners = new ArrayList<IContextListener>();
	
	private final InvocationHandler contextListenerInvocationHandler = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (IContextListener eventListener : contextEventListeners) {
				method.invoke(eventListener, args);
			}
			return null;
		}
	};

	/**
	* It listens to context events and invokes listener methods
	*/
	private final IContextListener contextListenerProxy = (IContextListener) Proxy
			.newProxyInstance(IContextListener.class.getClassLoader(),
					new Class[] { IContextListener.class },
					 contextListenerInvocationHandler);
	
	SingleContext(String context, ContextManager manager){
		super(context, manager);
		contextEventListeners.addAll(InnerSPIServises.getBy(driverEncapsulation)
				.getServices(IContextListener.class));
		contextListenerProxy.whenNewHandleIsAppeared(this);
	}
	
	/** Static constructor ¹1 - initialization of new context that will appear.
	 */
	public SingleContext(ContextManager manager) throws NoSuchContextException {
		this(manager.switchToNew(), manager);
	}

	/** constructor ¹1.1
	 */
	public SingleContext(ContextManager manager, long secondsTimeOut) throws NoSuchContextException {
		this(manager.switchToNew(secondsTimeOut), manager);
	}

	/**constructor ¹2 - initialization of new context that will appear.
	* We use its name
	*/
	public SingleContext(ContextManager manager, String name) throws NoSuchContextException  {
		this( manager.switchToNew(name), manager);
	}

	/**constructor ¹2.1
	 */
	public SingleContext(ContextManager manager, String name, long secondsTimeOut) throws NoSuchContextException  {
		this(manager.switchToNew(secondsTimeOut, name), manager);
	}

	@Override
	public synchronized void switchToMe() {
		contextListenerProxy.beforeIsSwitchedOn(this);
		nativeManager.switchTo(handle);
		contextListenerProxy.whenIsSwitchedOn(this);
	}

	public void addListener(IContextListener listener) {
		contextEventListeners.add(listener);
	}

	public void removeAllListeners() {
		contextEventListeners.clear();
	}

	public void removeListener(IContextListener listener) {
		contextEventListeners.remove(listener);
	}

	/** Static constructor ¹3 - initialization of new contextt by its index.
	*/
	public static SingleContext initContextByIndex(ContextManager manager,
			int index) throws NoSuchContextException {
		String handle = manager.getHandleByInex(index);
		SingleContext initedContext = (SingleContext) isInitiated(handle, manager);
		if (initedContext != null) {
			return (initedContext);
		}
		return (new SingleContext(handle, manager));
	}

}
