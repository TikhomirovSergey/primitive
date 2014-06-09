package org.primitive.webdriverencapsulations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.bydefault.NavigationTool;
import org.primitive.webdriverencapsulations.components.bydefault.WindowTool;
import org.primitive.webdriverencapsulations.eventlisteners.IWindowListener;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;

/**
 * @author s.tihomirov It is performs actions on a single window
 */
public final class SingleWindow extends Handle implements Navigation, IExtendedWindow {
	private final WindowTool windowTool;
	private final NavigationTool navigationTool;
	private final List<IWindowListener> windowEventListeners = new ArrayList<IWindowListener>();
	private final InvocationHandler windowListenerInvocationHandler = new InvocationHandler() {
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			for (IWindowListener eventListener : windowEventListeners) {
				method.invoke(eventListener, args);
			}
			return null;
		}
	};
	/**
	* It listens to window events and invokes listener methods
	*/
	private final IWindowListener windowListenerProxy = (IWindowListener) Proxy
			.newProxyInstance(IWindowListener.class.getClassLoader(),
					new Class[] { IWindowListener.class },
					windowListenerInvocationHandler);

	static SingleWindow isInitiated(String handle, WindowManager manager) {
		return (SingleWindow) manager.getHandleReceptionist().isInstantiated(handle);
	}

	private SingleWindow(WindowManager windowManager, String handle) {
		super(windowManager, handle);
		this.windowTool = ComponentFactory.getComponent(WindowTool.class,
				driverEncapsulation.getWrappedDriver());
		this.navigationTool = ComponentFactory.getComponent(NavigationTool.class,
				driverEncapsulation.getWrappedDriver());;
		windowEventListeners.addAll(InnerSPIServises.getBy(driverEncapsulation)
				.getServices(IWindowListener.class));
		windowListenerProxy.whenNewWindewIsAppeared(this);
	}

	/** Static constructor ¹1 - initialization of new window that will appear.
	 */
	public static SingleWindow initNewWindow(WindowManager manager)
			throws NoSuchWindowException {
		return (new SingleWindow(manager, manager.switchToNew()));
	}

	/** Static constructor ¹1.1
	 */
	public static SingleWindow initNewWindow(WindowManager manager,
			long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(manager,
				manager.switchToNew(secondsTimeOut)));
	}

	/** Static constructor ¹2 - initialization of new window that will appear.
	* We use either title of a window or piece of its title. Fragment is
	* a regular expression
	*/
	public static SingleWindow initNewWindow(WindowManager manager,
			String title) throws NoSuchWindowException {
		return (new SingleWindow(manager, manager.switchToNew(title)));
	}

	/** Static constructor ¹2.1
	 */
	public static SingleWindow initNewWindow(WindowManager manager,
			String title, long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(manager, manager.switchToNew(
				secondsTimeOut, title)));
	}

	/** Static constructor ¹3 - initialization of new window that will appear.
	* We use possible URLs of a loaded page. They can be defined as RegExp
	*/
	public static SingleWindow initNewWindow(WindowManager manager, List<String> urls)
			throws NoSuchWindowException {
		return (new SingleWindow(manager, manager.switchToNew(urls)));
	}

	/** Static constructor ¹3.1
	 */
	public static SingleWindow initNewWindow(WindowManager manager, List<String> urls,
			long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(manager, manager.switchToNew(
				secondsTimeOut, urls)));
	}

	/** Static constructor ¹4 - initialization of new window object by its index.
	* It is known that window is present
	*/
	public static SingleWindow initWindowByIndex(WindowManager manager,
			int index) throws NoSuchWindowException {
		String handle = manager.getHandleByInex(index);
		SingleWindow InitedWindow = isInitiated(handle, manager);
		if (InitedWindow != null) {
			return (InitedWindow);
		}
		return (new SingleWindow(manager, handle));
	}

	private void requestToMe() {
		windowListenerProxy.beforeWindowIsSwitchedOn(this);
		nativeManager.switchTo(handle);
		windowListenerProxy.whenWindowIsSwitchedOn(this);
	}

	@Override
	public void destroy() {
		super.destroy();
		removeAllListeners();
	}

	public synchronized void close() throws UnclosedWindowException,
			NoSuchWindowException, UnhandledAlertException,
			UnreachableBrowserException {
		try {
			windowListenerProxy.beforeWindowIsClosed(this);
			((WindowManager) nativeManager).close(handle);
			windowListenerProxy.whenWindowIsClosed(this);
			destroy();
		} catch (UnhandledAlertException | UnclosedWindowException e) {
			throw e;
		} catch (NoSuchWindowException e) {
			destroy();
			throw e;
		}
	}

	@Override
	public synchronized void switchToMe() throws NoSuchWindowException {
		requestToMe();
	}

	@Override
	public synchronized String getCurrentUrl() throws NoSuchWindowException {
		return ((WindowManager) nativeManager).getWindowURLbyHandle(handle);
	}

	@Override
	public synchronized String getTitle() {
		return ((WindowManager) nativeManager).getTitleByHandle(handle);
	}

	public WebDriverEncapsulation getDriverEncapsulation() {
		return (driverEncapsulation);
	}

	@Override
	public synchronized void to(String link) {
		requestToMe();
		navigationTool.to(link);
	}

	@Override
	public synchronized void forward() {
		requestToMe();
		navigationTool.forward();
	}

	@Override
	public synchronized void back() {
		requestToMe();
		navigationTool.back();
	}

	@Override
	public synchronized void refresh() {
		requestToMe();
		windowListenerProxy.beforeWindowIsRefreshed(this);
		navigationTool.refresh();
		windowListenerProxy.whenWindowIsRefreshed(this);
	}

	@Override
	public synchronized void to(URL url) {
		requestToMe();
		navigationTool.to(url);

	}

	@Override
	public synchronized Point getPosition() {
		requestToMe();
		return windowTool.getPosition();
	}

	@Override
	public synchronized Dimension getSize() {
		requestToMe();
		return windowTool.getSize();
	}

	@Override
	public synchronized void maximize() {
		requestToMe();
		windowListenerProxy.beforeWindowIsMaximized(this);
		windowTool.maximize();
		windowListenerProxy.whenWindowIsMaximized(this);
	}

	@Override
	public synchronized void setPosition(Point position) {
		requestToMe();
		windowListenerProxy.beforeWindowIsMoved(this, position);
		windowTool.setPosition(position);
		windowListenerProxy.whenWindowIsMoved(this, position);
	}

	@Override
	public synchronized void setSize(Dimension size) {
		requestToMe();
		windowListenerProxy.beforeWindowIsResized(this, size);
		windowTool.setSize(size);
		windowListenerProxy.whenWindowIsResized(this, size);
	}

	public void addListener(IWindowListener listener) {
		windowEventListeners.add(listener);
	}

	public void removeListener(IWindowListener listener) {
		windowEventListeners.remove(listener);
	}

	public void removeAllListeners() {
		windowEventListeners.clear();
	}
}