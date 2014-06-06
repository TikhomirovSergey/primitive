package org.primitive.webdriverencapsulations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.primitive.interfaces.IDestroyable;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.bydefault.NavigationTool;
import org.primitive.webdriverencapsulations.components.bydefault.WindowTool;
import org.primitive.webdriverencapsulations.eventlisteners.IWindowListener;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;
import org.primitive.webdriverencapsulations.interfaces.ISwitchesToItself;

/**
 * @author s.tihomirov It is performs actions on a single window
 */
public final class SingleWindow implements Navigation, IExtendedWindow,
		IDestroyable, ISwitchesToItself {
	private final WindowSwitcher nativeSwitcher;
	private final String objectWindow;
	private final WebDriverEncapsulation driverEncapsulation;
	private final WindowTool windowTool;
	private final NavigationTool navigationTool;
	private final WindowReceptionist receptionist;
	private final ContextSwitcher contextSwitcher;
	

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

	static SingleWindow isInitiated(String handle, WindowSwitcher switcher) {
		return (SingleWindow) switcher.getWindowReceptionist().isInstantiated(handle);
	}

	private SingleWindow(WindowSwitcher switcher, String handle) {
		this.nativeSwitcher = switcher;
		this.driverEncapsulation = switcher.getWebDriverEncapsulation();
		this.objectWindow = handle;
		this.windowTool = ComponentFactory.getComponent(WindowTool.class,
				driverEncapsulation.getWrappedDriver());
		this.navigationTool = ComponentFactory.getComponent(NavigationTool.class,
				driverEncapsulation.getWrappedDriver());
		this.receptionist = nativeSwitcher.getWindowReceptionist();
		this.contextSwitcher = new ContextSwitcher(this);
		receptionist.addKnownWindow(this);
		windowEventListeners.addAll(InnerSPIServises.getBy(driverEncapsulation)
				.getServices(IWindowListener.class));
		windowListenerProxy.whenNewWindewIsAppeared(this);
	}

	/** Static constructor ¹1 - initialization of new window that will appear.
	 */
	public static SingleWindow initNewWindow(WindowSwitcher switcher)
			throws NoSuchWindowException {
		return (new SingleWindow(switcher, switcher.switchToNew()));
	}

	/** Static constructor ¹1.1
	 */
	public static SingleWindow initNewWindow(WindowSwitcher switcher,
			long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(switcher,
				switcher.switchToNew(secondsTimeOut)));
	}

	/** Static constructor ¹2 - initialization of new window that will appear.
	* We use either title of a window or piece of its title. Fragment is
	* a regular expression
	*/
	public static SingleWindow initNewWindow(WindowSwitcher switcher,
			String title) throws NoSuchWindowException {
		return (new SingleWindow(switcher, switcher.switchToNew(title)));
	}

	/** Static constructor ¹2.1
	 */
	public static SingleWindow initNewWindow(WindowSwitcher switcher,
			String title, long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(switcher, switcher.switchToNew(
				secondsTimeOut, title)));
	}

	/** Static constructor ¹3 - initialization of new window that will appear.
	* We use possible URLs of a loaded page. They can be defined as RegExp
	*/
	public static SingleWindow initNewWindow(WindowSwitcher switcher, List<String> urls)
			throws NoSuchWindowException {
		return (new SingleWindow(switcher, switcher.switchToNew(urls)));
	}

	/** Static constructor ¹3.1
	 */
	public static SingleWindow initNewWindow(WindowSwitcher switcher, List<String> urls,
			long secondsTimeOut) throws NoSuchWindowException {
		return (new SingleWindow(switcher, switcher.switchToNew(
				secondsTimeOut, urls)));
	}

	/** Static constructor ¹4 - initialization of new window object by its index.
	* It is known that window is present
	*/
	public static SingleWindow initWindowByIndex(WindowSwitcher switcher,
			int index) throws NoSuchWindowException {
		String handle = switcher.getHandleByInex(index);
		SingleWindow InitedWindow = isInitiated(handle, switcher);
		if (InitedWindow != null) {
			return (InitedWindow);
		}
		return (new SingleWindow(switcher, handle));
	}

	private void requestToMe() {
		windowListenerProxy.beforeWindowIsSwitchedOn(this);
		nativeSwitcher.switchTo(objectWindow);
		windowListenerProxy.whenWindowIsSwitchedOn(this);
	}

	@Override
	public void destroy() {
		receptionist.removeWindow(this);
		removeAllListeners();
	}

	public synchronized void close() throws UnclosedWindowException,
			NoSuchWindowException, UnhandledAlertException,
			UnreachableBrowserException {
		try {
			windowListenerProxy.beforeWindowIsClosed(this);
			nativeSwitcher.close(objectWindow);
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
	public synchronized String getWindowHandle() {
		return (objectWindow);
	}

	@Override
	public synchronized String getCurrentUrl() throws NoSuchWindowException {
		return (nativeSwitcher.getWindowURLbyHandle(objectWindow));
	}

	@Override
	public synchronized String getTitle() {
		return nativeSwitcher.getTitleByHandle(objectWindow);
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

	@Override
	public synchronized void takeAPictureOfAnInfo(String Comment) {
		nativeSwitcher.takeAPictureOfAnInfo(objectWindow, Comment);
	}

	@Override
	public synchronized void takeAPictureOfAFine(String Comment) {
		nativeSwitcher.takeAPictureOfAFine(objectWindow, Comment);
	}

	@Override
	public synchronized void takeAPictureOfAWarning(String Comment) {
		nativeSwitcher.takeAPictureOfAWarning(objectWindow, Comment);
	}

	@Override
	public synchronized void takeAPictureOfASevere(String Comment) {
		nativeSwitcher.takeAPictureOfASevere(objectWindow, Comment);
	}

	public synchronized boolean exists() {
		if (!nativeSwitcher.isAlive()) {
			return false;
		}
		try {
			Set<String> handles = nativeSwitcher.getHandles();
			return handles.contains(objectWindow);
		} catch (WebDriverException e) { // if there is no window
			return false;
		}
	}

	public WindowSwitcher getSwitcher() {
		return nativeSwitcher;
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
	
	public ContextAware gContextAware(){
		return contextSwitcher;
	}
}