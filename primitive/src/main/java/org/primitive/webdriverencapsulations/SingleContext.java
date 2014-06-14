package org.primitive.webdriverencapsulations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.primitive.webdriverencapsulations.eventlisteners.IContextListener;
import org.primitive.webdriverencapsulations.interfaces.IHasActivity;
public final class SingleContext extends Handle implements IHasActivity{

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
	
	public void addListener(IContextListener listener) {
		contextEventListeners.add(listener);
	}

	public void removeAllListeners() {
		contextEventListeners.clear();
	}

	public void removeListener(IContextListener listener) {
		contextEventListeners.remove(listener);
	}

	@Override
	void requestToMe() {
		contextListenerProxy.beforeIsSwitchedOn(this);
		super.requestToMe();
		contextListenerProxy.whenIsSwitchedOn(this);
	}

	@Override
	public String currentActivity() {
		return ((ContextManager) nativeManager).getActivityByHandle(handle);
	}

}
