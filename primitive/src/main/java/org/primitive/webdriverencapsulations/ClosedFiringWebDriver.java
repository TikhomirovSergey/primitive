/**
 * 
 */
package org.primitive.webdriverencapsulations;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Beta;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.internal.EventFiringKeyboard;
import org.openqa.selenium.support.events.internal.EventFiringMouse;
import org.openqa.selenium.support.events.internal.EventFiringTouch;
import org.primitive.webdriverencapsulations.eventlisteners.IExtendedWebDriverEventListener;

/**
 * @author s.tihomirov For some functions of EventFiringWebDriver
 */
public class ClosedFiringWebDriver extends EventFiringWebDriver
		implements HasCapabilities, MobileDriver, Rotatable, FindsByIosUIAutomation,
		FindsByAndroidUIAutomator, FindsByAccessibilityId
		{

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedEventFiringTouch implements TouchScreen {
		private EventFiringTouch touch;
		private ClosedFiringWebDriver driver;

		public ExtendedEventFiringTouch(TouchScreen touch,
				ClosedFiringWebDriver driver) {
			this.driver = driver;
			touch = new EventFiringTouch(this.driver.driverForExtension,
					this.driver.extendedDispatcher);
		}

		private static ExtendedEventFiringTouch newInstance(TouchScreen touch,
				ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringTouch) getProxifiedInnerObject(driver,
					ExtendedEventFiringTouch.class, new Class[] {
							TouchScreen.class,
							ClosedFiringWebDriver.class }, new Object[] {
							touch, driver });
		}

		@Override
		public void doubleTap(Coordinates arg0) {
			touch.doubleTap(arg0);
		}

		@Override
		public void down(int arg0, int arg1) {
			touch.down(arg0, arg1);

		}

		@Override
		public void flick(int arg0, int arg1) {
			touch.flick(arg0, arg1);
		}

		@Override
		public void flick(Coordinates arg0, int arg1, int arg2, int arg3) {
			touch.flick(arg0, arg1, arg2, arg3);

		}

		@Override
		public void longPress(Coordinates arg0) {
			touch.longPress(arg0);
		}

		@Override
		public void move(int arg0, int arg1) {
			touch.move(arg0, arg1);

		}

		@Override
		public void scroll(int arg0, int arg1) {
			touch.scroll(arg0, arg1);

		}

		@Override
		public void scroll(Coordinates arg0, int arg1, int arg2) {
			touch.scroll(arg0, arg1, arg2);

		}

		@Override
		public void singleTap(Coordinates arg0) {
			touch.singleTap(arg0);
		}

		@Override
		public void up(int arg0, int arg1) {
			touch.up(arg0, arg1);
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedEventFiringKeyboard implements Keyboard {
		private EventFiringKeyboard keyBoard;
		private ClosedFiringWebDriver driver;

		protected ExtendedEventFiringKeyboard(Keyboard keyboard,
				ClosedFiringWebDriver driver) {
			this.driver = driver;
			this.keyBoard = new EventFiringKeyboard(
					this.driver.driverForExtension,
					this.driver.extendedDispatcher);
		}

		private static ExtendedEventFiringKeyboard newInstance(
				Keyboard keyboard, ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringKeyboard) getProxifiedInnerObject(
					driver, ExtendedEventFiringKeyboard.class,
					new Class[] { Keyboard.class,
							ClosedFiringWebDriver.class }, new Object[] {
							keyboard, driver });
		}

		@Override
		public void sendKeys(CharSequence... arg0) {
			keyBoard.sendKeys(arg0);
		}

		@Override
		public void pressKey(CharSequence arg0) {
			keyBoard.pressKey(arg0);
		}

		@Override
		public void releaseKey(CharSequence arg0) {
			keyBoard.pressKey(arg0);
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedEventFiringMouse implements Mouse {
		private EventFiringMouse mouse;
		private ClosedFiringWebDriver driver;

		/**
		 * 
		 */
		protected ExtendedEventFiringMouse(Mouse mouse,
				ClosedFiringWebDriver driver) {
			this.driver = driver;
			this.mouse = new EventFiringMouse(this.driver.driverForExtension,
					this.driver.extendedDispatcher);
		}

		private static ExtendedEventFiringMouse newInstance(Mouse mouse,
				ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringMouse) getProxifiedInnerObject(driver,
					ExtendedEventFiringMouse.class, new Class[] { Mouse.class,
							ClosedFiringWebDriver.class }, new Object[] {
							mouse, driver });
		}

		@Override
		public void click(Coordinates arg0) {
			mouse.click(arg0);
		}

		@Override
		public void contextClick(Coordinates arg0) {
			mouse.contextClick(arg0);

		}

		@Override
		public void doubleClick(Coordinates arg0) {
			mouse.doubleClick(arg0);
		}

		@Override
		public void mouseDown(Coordinates arg0) {
			mouse.mouseDown(arg0);
		}

		@Override
		public void mouseMove(Coordinates arg0) {
			mouse.mouseMove(arg0);

		}

		@Override
		public void mouseMove(Coordinates arg0, long arg1, long arg2) {
			mouse.mouseMove(arg0, arg1, arg2);

		}

		@Override
		public void mouseUp(Coordinates arg0) {
			mouse.mouseUp(arg0);
		}

	}

	/**
	 * @author s.tihomirov It is useful for multithread working
	 */
	private static class WebDriverInterceptor implements MethodInterceptor {
		private ClosedFiringWebDriver driver;

		private void setDriver(ClosedFiringWebDriver driver) {
			this.driver = driver;
		}

		@Override
		public synchronized Object intercept(Object proxy, Method method,
				Object[] args, MethodProxy methodProxy) throws Throwable {
			try {
				return methodProxy.invokeSuper(proxy, args);
			} catch (Exception e) {
				driver.extendedDispatcher.onException(e, driver);
				throw e;
			}
		}

	}

	static class EventFiringLogs implements Logs {
		private Logs browserLogs;

		protected EventFiringLogs(Logs browserLogs) {
			this.browserLogs = browserLogs;
		}

		private static EventFiringLogs newInstance(
				ClosedFiringWebDriver driver, Logs browserLogs) {
			return (EventFiringLogs) getProxifiedInnerObject(driver,
					EventFiringLogs.class, new Class[] { Logs.class },
					new Object[] { browserLogs });
		}

		@Override
		public LogEntries get(String arg0) {
			return browserLogs.get(arg0);
		}

		@Override
		public Set<String> getAvailableLogTypes() {
			return browserLogs.getAvailableLogTypes();
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class EventFiringIme implements ImeHandler {
		private ImeHandler ime;

		protected EventFiringIme(ImeHandler ime) {
			this.ime = ime;
		}

		private static EventFiringIme newInstance(
				ClosedFiringWebDriver driver, ImeHandler ime) {
			return (EventFiringIme) getProxifiedInnerObject(driver,
					EventFiringIme.class, new Class[] { ImeHandler.class },
					new Object[] { ime });
		}

		@Override
		public void activateEngine(String arg0) {
			ime.activateEngine(arg0);
		}

		@Override
		public void deactivate() {
			ime.deactivate();
		}

		@Override
		public String getActiveEngine() {
			return ime.getActiveEngine();
		}

		@Override
		public List<String> getAvailableEngines() {
			return ime.getAvailableEngines();
		}

		@Override
		public boolean isActivated() {
			return ime.isActivated();
		}

	}

	static class ExtendedEventFiringTimeouts implements Timeouts {
		private Timeouts timeouts;
		private ClosedFiringWebDriver driver;

		protected ExtendedEventFiringTimeouts(Timeouts timeouts,
				ClosedFiringWebDriver driver) {
			this.timeouts = timeouts;
			this.driver = driver;
		}

		private static ExtendedEventFiringTimeouts newInstance(
				Timeouts timeouts, ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringTimeouts) getProxifiedInnerObject(
					driver, ExtendedEventFiringTimeouts.class,
					new Class[] { Timeouts.class,
							ClosedFiringWebDriver.class }, new Object[] {
							timeouts, driver });
		}

		@Override
		public Timeouts implicitlyWait(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			timeouts.implicitlyWait(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			return timeouts;
		}

		@Override
		public Timeouts pageLoadTimeout(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			timeouts.pageLoadTimeout(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			return timeouts;
		}

		@Override
		public Timeouts setScriptTimeout(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			timeouts.setScriptTimeout(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.driverForExtension, timeouts, arg0, arg1);
			return timeouts;
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedFiringNavigation implements Navigation {

		private Navigation naviagation;

		protected ExtendedFiringNavigation(Navigation naviagation,
				ClosedFiringWebDriver driver) {
			this.naviagation = naviagation;
		}

		private static ExtendedFiringNavigation newInstance(
				Navigation naviagation, ClosedFiringWebDriver driver) {
			return (ExtendedFiringNavigation) getProxifiedInnerObject(driver,
					ExtendedFiringNavigation.class, new Class[] {
							Navigation.class,
							ClosedFiringWebDriver.class }, new Object[] {
							naviagation, driver });
		}

		@Override
		public void back() {
			naviagation.back();
		}

		@Override
		public void forward() {
			naviagation.forward();
		}

		@Override
		public void refresh() {
			naviagation.refresh();
		}

		@Override
		public void to(String arg0) {
			naviagation.to(arg0);

		}

		@Override
		public void to(URL arg0) {
			naviagation.to(arg0);
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class EventFiringWindow implements Window {
		private Window window;

		public EventFiringWindow(Window window,
				ClosedFiringWebDriver driver) {
			this.window = window;
		}

		private static EventFiringWindow newInstance(Window window,
				ClosedFiringWebDriver driver) {
			return (EventFiringWindow) getProxifiedInnerObject(driver,
					EventFiringWindow.class, new Class[] { Window.class,
							ClosedFiringWebDriver.class }, new Object[] {
							window, driver });
		}

		@Override
		public Point getPosition() {
			return window.getPosition();
		}

		@Override
		public Dimension getSize() {
			return window.getSize();
		}

		@Override
		public void maximize() {
			window.maximize();
		}

		@Override
		public void setPosition(Point arg0) {
			window.setPosition(arg0);
		}

		@Override
		public void setSize(Dimension arg0) {
			window.setSize(arg0);
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedEventFiringOptions implements Options {
		private Options option;
		private ClosedFiringWebDriver driver;

		protected ExtendedEventFiringOptions(Options option,
				ClosedFiringWebDriver driver) {
			this.option = option;
			this.driver = driver;
		}

		private static ExtendedEventFiringOptions newInstance(Options option,
				ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringOptions) getProxifiedInnerObject(driver,
					ExtendedEventFiringOptions.class,
					new Class[] { Options.class,
							ClosedFiringWebDriver.class }, new Object[] {
							option, driver });
		}

		@Override
		public void addCookie(Cookie cookie) {
			option.addCookie(cookie);
		}

		@Override
		public void deleteAllCookies() {
			option.deleteAllCookies();
		}

		@Override
		public void deleteCookie(Cookie cookie) {
			option.deleteCookie(cookie);
		}

		@Override
		public void deleteCookieNamed(String cookieName) {
			option.deleteCookieNamed(cookieName);
		}

		@Override
		public Cookie getCookieNamed(String cookieName) {
			return option.getCookieNamed(cookieName);
		}

		@Override
		public Set<Cookie> getCookies() {
			return option.getCookies();
		}

		@Override
		public ImeHandler ime() {
			ImeHandler ime = option.ime();
			return EventFiringIme.newInstance(driver, ime);
		}

		@Override
		@Beta
		public Logs logs() {
			return EventFiringLogs.newInstance(driver, option.logs());
		}

		@Override
		public Timeouts timeouts() {
			return ExtendedEventFiringTimeouts.newInstance(option.timeouts(),
					driver);
		}

		@Override
		@Beta
		public Window window() {
			return EventFiringWindow.newInstance(option.window(), driver);
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class ExtendedEventFiringTargetLocator implements
			TargetLocator {
		private TargetLocator targetLocator;
		private ClosedFiringWebDriver driver;

		protected ExtendedEventFiringTargetLocator(TargetLocator targetLocator,
				ClosedFiringWebDriver driver) {
			this.targetLocator = targetLocator;
			this.driver = driver;
		}

		private static ExtendedEventFiringTargetLocator newInstance(
				TargetLocator targetLocator, ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringTargetLocator) getProxifiedInnerObject(
					driver, ExtendedEventFiringTargetLocator.class,
					new Class[] { TargetLocator.class,
							ClosedFiringWebDriver.class }, new Object[] {
							targetLocator, driver });
		}

		@Override
		public WebElement activeElement() {
			return ExtendedEventFiringWebElement.newInstance(
					targetLocator.activeElement(), driver);
		}

		@Override
		public Alert alert() {
			return EventFiringAlert.newInstance(targetLocator.alert(), driver);
		}

		@Override
		public WebDriver defaultContent() {
			targetLocator.defaultContent();
			return driver;
		}

		@Override
		public WebDriver frame(int arg0) {
			targetLocator.frame(arg0);
			return driver;
		}

		@Override
		public WebDriver frame(String arg0) {
			targetLocator.frame(arg0);
			return driver;
		}

		@Override
		public WebDriver frame(WebElement arg0) {
			targetLocator.frame(arg0);
			return driver;
		}

		@Override
		public WebDriver window(String arg0) {
			targetLocator.window(arg0);
			return driver;
		}

		@Override
		public WebDriver parentFrame() {
			targetLocator.parentFrame();
			return driver;
		}

	}

	/**
	 * @author s.tihomirov For some functions of EventFiringWebEvement
	 */
	static class ExtendedEventFiringWebElement implements WebElement,
			Locatable, WrapsElement {
		private final WebElement element;
		private WebElement wrapped;
		private ClosedFiringWebDriver extendedDriver;

		protected ExtendedEventFiringWebElement(final WebElement element,
				ClosedFiringWebDriver driver) {
			wrapped = element;
			this.extendedDriver = driver;
			
			this.element = (WebElement) Proxy.newProxyInstance(
					IExtendedWebDriverEventListener.class.getClassLoader(),
					element.getClass().getInterfaces(),
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							try {
								return method.invoke(element, args);
							} catch (InvocationTargetException e) {
								extendedDriver.extendedDispatcher.onException(
										e.getTargetException(),
										extendedDriver.driverForExtension);
								throw e.getTargetException();
							}
						}
					});
		}

		private static ExtendedEventFiringWebElement newInstance(
				WebElement element, ClosedFiringWebDriver driver) {
			return (ExtendedEventFiringWebElement) getProxifiedInnerObject(
					driver, ExtendedEventFiringWebElement.class, new Class[] {
							WebElement.class,
							ClosedFiringWebDriver.class }, new Object[] {
							element, driver });
		}

		@Override
		public void clear() {
			element.clear();
		}

		@Override
		public void click() {
			element.click();
		}

		@Override
		public WebElement findElement(By by) {
			return newInstance(element.findElement(by), extendedDriver);
		}

		@Override
		public List<WebElement> findElements(By by) {
			List<WebElement> temp = element.findElements(by);
			List<WebElement> result = new ArrayList<WebElement>(temp.size());
			for (WebElement element : temp) {
				result.add(newInstance(element, extendedDriver));
			}
			return result;
		}

		@Override
		public String getAttribute(String arg0) {
			return element.getAttribute(arg0);
		}

		@Override
		public String getCssValue(String arg0) {
			return element.getCssValue(arg0);
		}

		@Override
		public Point getLocation() {
			return element.getLocation();
		}

		@Override
		public Dimension getSize() {
			return element.getSize();
		}

		@Override
		public String getTagName() {
			return element.getTagName();
		}

		@Override
		public String getText() {
			return element.getText();
		}

		@Override
		public boolean isDisplayed() {
			return element.isDisplayed();
		}

		@Override
		public boolean isEnabled() {
			return element.isEnabled();
		}

		@Override
		public boolean isSelected() {
			return element.isSelected();
		}

		@Override
		public void sendKeys(CharSequence... arg0) {
			element.sendKeys(arg0);
		}

		@Override
		public void submit() {
			extendedDriver.extendedDispatcher.beforeSubmit(
					extendedDriver.driverForExtension, wrapped);
			element.submit();
			extendedDriver.extendedDispatcher.afterSubmit(
					extendedDriver.driverForExtension, wrapped);
		}

		@Override
		public Coordinates getCoordinates() {
			return ((Locatable) element).getCoordinates();

		}

		public WebElement getWrappedElement() {
			return wrapped;
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class EventFiringAlert implements Alert {
		private Alert alert;
		private ClosedFiringWebDriver driver;

		protected EventFiringAlert(Alert alert,
				ClosedFiringWebDriver driver) {
			this.alert = alert;
			this.driver = driver;
		}

		private static EventFiringAlert newInstance(Alert alert,
				ClosedFiringWebDriver driver) {
			return (EventFiringAlert) getProxifiedInnerObject(driver,
					EventFiringAlert.class, new Class[] { Alert.class,
							ClosedFiringWebDriver.class }, new Object[] {
							alert, driver });
		}

		@Override
		public void accept() {
			driver.extendedDispatcher.beforeAlertAccept(
					driver.driverForExtension, alert);
			alert.accept();
			driver.extendedDispatcher.afterAlertAccept(
					driver.driverForExtension, alert);
		}

		@Override
		@Beta
		public void authenticateUsing(Credentials arg0) {
			alert.authenticateUsing(arg0);
		}

		@Override
		public void dismiss() {
			driver.extendedDispatcher.beforeAlertDismiss(
					driver.driverForExtension, alert);
			alert.dismiss();
			driver.extendedDispatcher.afterAlertDismiss(
					driver.driverForExtension, alert);
		}

		@Override
		public String getText() {
			return alert.getText();
		}

		@Override
		public void sendKeys(String arg0) {
			driver.extendedDispatcher.beforeAlertSendKeys(
					driver.driverForExtension, alert, arg0);
			alert.sendKeys(arg0);
			driver.extendedDispatcher.afterAlertSendKeys(
					driver.driverForExtension, alert, arg0);
		}

	}

	private final List<IExtendedWebDriverEventListener> extendedEventListeners = new ArrayList<IExtendedWebDriverEventListener>();

	protected final WebDriver driverForExtension;
	private WebDriverInterceptor interceptor;

	private final IExtendedWebDriverEventListener extendedDispatcher = (IExtendedWebDriverEventListener) Proxy
			.newProxyInstance(
					IExtendedWebDriverEventListener.class.getClassLoader(),
					new Class[] { IExtendedWebDriverEventListener.class },
					new InvocationHandler() {
						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							for (IExtendedWebDriverEventListener eventListener : extendedEventListeners) {
								method.invoke(eventListener, args);
							}
							return null;
						}
					});

	public static ClosedFiringWebDriver newInstance(WebDriver driver) {
		WebDriverInterceptor webDriverCallBack = new WebDriverInterceptor();
		Enhancer enhancerForWebDriver = new Enhancer();
		enhancerForWebDriver.setCallback(webDriverCallBack);
		enhancerForWebDriver.setSuperclass(ClosedFiringWebDriver.class);
		ClosedFiringWebDriver proxy = (ClosedFiringWebDriver) enhancerForWebDriver
				.create(new Class[] { WebDriver.class },
						new Object[] { driver });
		webDriverCallBack.setDriver(proxy);
		proxy.interceptor = webDriverCallBack;
		return proxy;
	}

	// creates proxy objects of nested classes
	private static Object getProxifiedInnerObject(
			ClosedFiringWebDriver driver, Class<?> clazz,
			Class<?>[] paramClasses, Object[] paramValues) {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(driver.interceptor);
		enhancer.setSuperclass(clazz);
		return enhancer.create(paramClasses, paramValues);
	}

	protected ClosedFiringWebDriver(WebDriver driver) {
		super(driver);
		driverForExtension = driver;
	}

	public void register(IExtendedWebDriverEventListener eventListener) {
		super.register(eventListener);
		extendedEventListeners.add(eventListener);
	}

	public void unregister(IExtendedWebDriverEventListener eventListener) {
		super.unregister(eventListener);
		extendedEventListeners.remove(eventListener);
	}

	public Set<String> getWindowHandles() {
		return super.getWindowHandles();
	}

	public String getWindowHandle() {
		return super.getWindowHandle();
	}

	public String getCurrentUrl() {
		return super.getCurrentUrl();
	}

	public String getTitle() {
		return super.getTitle();
	}

	public String getPageSource() {
		return super.getPageSource();
	}

	public Keyboard getKeyboard() {
		return ExtendedEventFiringKeyboard.newInstance(super.getKeyboard(),
				this);
	}

	public Mouse getMouse() {
		return ExtendedEventFiringMouse.newInstance(super.getMouse(), this);
	}

	@Override
	public TouchScreen getTouch() {
		return ExtendedEventFiringTouch.newInstance(super.getTouch(), this);
	}

	// סמגלוסעטלמסעü
	public Capabilities getCapabilities() {
		return ((HasCapabilities) driverForExtension).getCapabilities();
	}

	public WebDriver.TargetLocator switchTo() {
		WebDriver.TargetLocator target = super.switchTo();
		return ExtendedEventFiringTargetLocator.newInstance(target, this);
	}

	public void close() {
		super.close();
	}

	public List<WebElement> findElements(By by) {
		List<WebElement> temp = super.findElements(by);
		List<WebElement> result = new ArrayList<WebElement>(temp.size());
		for (WebElement element : temp) {
			result.add(ExtendedEventFiringWebElement.newInstance(element, this));
		}
		return result;
	}

	public WebElement findElement(By by) {
		WebElement temp = super.findElement(by);
		return ExtendedEventFiringWebElement.newInstance(temp, this);
	}

	public Options manage() {
		Options option = super.manage();
		return ExtendedEventFiringOptions.newInstance(option, this);
	}

	public Navigation navigate() {
		return ExtendedFiringNavigation.newInstance(super.navigate(), this);
	}

	public <X> X getScreenshotAs(OutputType<X> target) {
		X result = null;
		try {
			result = ((TakesScreenshot) driverForExtension).getScreenshotAs(target);
		} catch (ClassCastException e) {
			WebDriver augmentedDriver = new Augmenter().augment(driverForExtension);
			result = ((TakesScreenshot) augmentedDriver).getScreenshotAs(target);
		}
		return result;
	}
	
	/**
	 * It seals wrapped driver forever
	 */
	@Deprecated
	@Override
	public WebDriver getWrappedDriver(){
		//I exclude wrapsDriver from this and super class
		Class<?>[] implemented = ClosedFiringWebDriver.class.getInterfaces();
		Class<?>[] implementedBySuper = EventFiringWebDriver.class
				.getInterfaces();
		
		Class<?>[] editedInterfaceArray = new Class<?>[(implemented.length + implementedBySuper.length)-1];
		
		int index = 0;
		for (int i=0; i<implemented.length; i++){
			if (!implemented[i].equals(WrapsDriver.class)){
				editedInterfaceArray[index] = implemented[i];	
				index++;
			}
		}
		
		for (int i=0; i<implementedBySuper.length; i++){
			if (!implementedBySuper[i].equals(WrapsDriver.class)){
				editedInterfaceArray[index] = implementedBySuper[i];	
				index++;
			}
		}

		
		// and create proxy object
		final ClosedFiringWebDriver proxyfied = this;
		WebDriver sealedDriver = (WebDriver) Proxy.newProxyInstance(
				WebDriver.class.getClassLoader(), editedInterfaceArray,
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						try {
							return method.invoke(proxyfied, args);
						} catch (Exception e) {
							throw e;
						}
					}
				});

		return sealedDriver;		
	}

	@Override
	public WebDriver context(String name) {
		return ((MobileDriver) driverForExtension).context(name);
	}

	@Override
	public Set<String> getContextHandles() {
		return ((MobileDriver) driverForExtension).getContextHandles();
	}

	@Override
	public String getContext() {
		return ((MobileDriver) driverForExtension).getContext();
	}

	@Override
	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return ((MobileDriver) driverForExtension).execute(driverCommand,
				parameters);
	}

	@Override
	public TouchAction performTouchAction(TouchAction touchAction) {
		return ((MobileDriver) driverForExtension)
				.performTouchAction(touchAction);
	}

	@Override
	public void performMultiTouchAction(MultiTouchAction multiAction) {
		((MobileDriver) driverForExtension)
				.performMultiTouchAction(multiAction);
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		((Rotatable) driverForExtension).rotate(orientation);		
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ((Rotatable) driverForExtension).getOrientation();
	}

	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return ((FindsByIosUIAutomation) driverForExtension)
				.findElementByIosUIAutomation(using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return ((FindsByIosUIAutomation) driverForExtension)
				.findElementsByIosUIAutomation(using);
	}

	@Override
	public WebElement findElementByAndroidUIAutomator(String using) {
		return ((FindsByAndroidUIAutomator) driverForExtension)
				.findElementByAndroidUIAutomator(using);
	}

	@Override
	public List<WebElement> findElementsByAndroidUIAutomator(String using) {
		return ((FindsByAndroidUIAutomator) driverForExtension)
				.findElementsByAndroidUIAutomator(using);
	}

	@Override
	public WebElement findElementByAccessibilityId(String using) {
		return ((FindsByAccessibilityId) driverForExtension)
				.findElementByAccessibilityId(using);
	}

	@Override
	public List<WebElement> findElementsByAccessibilityId(String using) {
		return ((FindsByAccessibilityId) driverForExtension)
				.findElementsByAccessibilityId(using);
	}
	
}