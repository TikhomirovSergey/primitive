package org.primitive.webdriverencapsulations;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.primitive.interfaces.IDestroyable;
import org.primitive.webdriverencapsulations.eventlisteners.IUnhandledWindowEventListener;


public final class UnhandledWindowChecker extends Thread implements IDestroyable {
	/**
	 * @author s.tihomirov
	 *
	 */
	private enum EActionsOnUnhandledAlert {
		DISMISS, ACCEPT;

		private void handle(Alert alert) {
			switch (this) {
			case ACCEPT:
				alert.accept();
				break;
			default:
				alert.dismiss();
			}
		}
	}
	
	// All listeners that were logged in
	private final List<IUnhandledWindowEventListener> unhandledWindowEventListeners = new ArrayList<IUnhandledWindowEventListener>();
	private final InvocationHandler unhandledInvocationHandler = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (IUnhandledWindowEventListener eventListener : unhandledWindowEventListeners) {
				method.invoke(eventListener, args);
			}
			return null;
		}
	};
	// It listens to not handled window events
	private final IUnhandledWindowEventListener unhandledWindowProxy = (IUnhandledWindowEventListener) Proxy
			.newProxyInstance(IUnhandledWindowEventListener.class.getClassLoader(),
					new Class[] {IUnhandledWindowEventListener.class },
					unhandledInvocationHandler);	

	private WindowSwitcher switcher = null;
	private final static HashMap<WindowSwitcher, UnhandledWindowChecker> checkers = new HashMap<>();
	
	private UnhandledWindowChecker(WindowSwitcher switcher)	{
		this.switcher = switcher;
		InnerSPIServises servises = InnerSPIServises.getBy(this.switcher.getWebDriverEncapsulation());
		addListeners(servises.getServices(IUnhandledWindowEventListener.class));
	}
	
	public static UnhandledWindowChecker getChecker(WindowSwitcher switcher) {
		UnhandledWindowChecker checker = checkers.get(switcher);
		if (checker != null) {
			return (checker);
		}
		checker = new UnhandledWindowChecker(switcher);
		checkers.put(switcher, checker);
		return (checker);
	}
	
	public void destroy() {
		switcher = null;
		checkers.remove(this);
	}
	
	private boolean attemptToCloseWindow(List<String> windowList, int index)
			throws UnclosedWindowException, UnhandledAlertException {
		try {
			switcher.switchTo(windowList.get(index));
			unhandledWindowProxy.whenUnhandledWindowIsFound(switcher.getWrappedDriver());
			switcher.close(windowList.get(index).toString());
			return true;
		} catch (UnclosedWindowException e) {
			throw e;
		} catch (UnhandledAlertException e) {
			throw e;
		} catch (NoSuchWindowException e) {
			unhandledWindowProxy.whenUnhandledWindowIsAlreadyClosed(switcher.getWrappedDriver());
			return true;
		}
	}
	
	private void attemptToHandleAlert(EActionsOnUnhandledAlert whatToDo) {
		try {
			Alert alert = switcher.getAlert();
			unhandledWindowProxy.whenUnhandledAlertIsFound(alert);
			whatToDo.handle(alert);
		} catch (NoAlertPresentException e1) {
			unhandledWindowProxy.whenNoAlertThere(switcher.getWrappedDriver());
		}
	}
	
	private boolean isWindowClosed(int winIndex, List<String> handleList,
			EActionsOnUnhandledAlert whatToDo) {
		try {
			return attemptToCloseWindow(handleList, winIndex);
		} catch (UnclosedWindowException | UnhandledAlertException e) {
			unhandledWindowProxy.whenUnhandledWindowIsNotClosed(switcher.getWrappedDriver());
			attemptToHandleAlert(whatToDo);
			return false;
		}
	}
	/**kills windows and alerts that weren't handled**/ 
	public synchronized void killUnexpectedWindows()
			throws UnhandledAlertException, UnclosedWindowException {
		List<String> windowList = null;
		windowList = getUnexpectedWindows();

		int i = windowList.size() - 1;
		while (i >= 0) {
			boolean closed = isWindowClosed(i, windowList, EActionsOnUnhandledAlert.DISMISS);
			if (!closed) {
				closed = isWindowClosed(i, windowList, EActionsOnUnhandledAlert.ACCEPT);
			}

			if (!closed) {
				try {
					attemptToCloseWindow(windowList, i);
				} catch (UnclosedWindowException | UnhandledAlertException e) {
					throw e;
				}
			}
			i = i - 1;
		}
	}
	
	//getting of browser window handles that probably unexpected
	private List<String> getUnexpectedWindows() {
		List<String> handles = new ArrayList<String>(switcher.getWindowHandles());
		List<String> unexpectedList = new ArrayList<String>();
		// If there is only one browser window we ignore it as it will be
		// handled soon
		if (handles.size() <= 1) // or it is already handled
		{
			return (unexpectedList); // returns empty list of window handles
		}
		// If there is more than one browser window
		for (String handle : handles) {
			if (SingleWindow.isInitiated(handle, switcher) == null) { 
				// it trying to filter windows that are unhandled
				unexpectedList.add(handle);
			}
		}
		return (unexpectedList);
	}
	
	/**Adds a list of listeners **/
	public void addListeners(List<IUnhandledWindowEventListener> listeners){
		unhandledWindowEventListeners.addAll(listeners);
	}
	
	/**Removes all listeners of defined list**/
	public void removeListeners(List<IUnhandledWindowEventListener> listeners){
		unhandledWindowEventListeners.retainAll(listeners);
	}
	
	/**Removes all listeners*/
	public void removeAllListeners() {
		unhandledWindowEventListeners.clear();
	}
}
