/**
 * 
 */
package org.primitive.webdriverencapsulations;

import io.appium.java_client.FindsByAccessibilityId;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.FindsByIosUIAutomation;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.primitive.webdriverencapsulations.eventlisteners.IExtendedWebDriverEventListener;
import org.primitive.webdriverencapsulations.interfaces.IUnpacksRemoteWebElement;

/**
 * @author s.tihomirov For some functions of EventFiringWebDriver
 */
public class ClosedFiringWebDriver extends EventFiringWebDriver
		implements HasCapabilities, MobileDriver, Rotatable, FindsByIosUIAutomation,
		FindsByAndroidUIAutomator, FindsByAccessibilityId
		{

	static class DefaultTimeouts implements Timeouts {
		private Timeouts timeouts;
		private ClosedFiringWebDriver driver;

		private DefaultTimeouts(Timeouts timeouts,
				ClosedFiringWebDriver driver) {
			this.timeouts = timeouts;
			this.driver = driver;
		}

		@Override
		public Timeouts implicitlyWait(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			timeouts.implicitlyWait(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			return timeouts;
		}

		@Override
		public Timeouts pageLoadTimeout(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			timeouts.pageLoadTimeout(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			return timeouts;
		}

		@Override
		public Timeouts setScriptTimeout(long arg0, TimeUnit arg1) {
			driver.extendedDispatcher.beforeWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			timeouts.setScriptTimeout(arg0, arg1);
			driver.extendedDispatcher.afterWebDriverSetTimeOut(
					driver.originalDriver, timeouts, arg0, arg1);
			return timeouts;
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class DefaultOptions implements Options {
		private Options option;
		private ClosedFiringWebDriver driver;

		private DefaultOptions(Options option,
				ClosedFiringWebDriver driver) {
			this.option = option;
			this.driver = driver;
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
			return option.ime();
		}

		@Override
		@Beta
		public Logs logs() {
			return option.logs();
		}

		@Override
		public Timeouts timeouts() {
			return new DefaultTimeouts(option.timeouts(),
					driver);
		}

		@Override
		@Beta
		public Window window() {
			return option.window();
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class DefaultTargetLocator implements
			TargetLocator {
		private TargetLocator targetLocator;
		private ClosedFiringWebDriver driver;

		private DefaultTargetLocator(TargetLocator targetLocator,
				ClosedFiringWebDriver driver) {
			this.targetLocator = targetLocator;
			this.driver = driver;
		}

		@Override
		public WebElement activeElement() {
			return new DefaultWebElement(
					targetLocator.activeElement(), driver);
		}

		@Override
		public Alert alert() {
			return new DefaultAlert(targetLocator.alert(), driver);
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
	static class DefaultWebElement implements WebElement,
			Locatable, WrapsElement, FindsByIosUIAutomation,
			FindsByAndroidUIAutomator, FindsByAccessibilityId, IUnpacksRemoteWebElement {
		private final WebElement element;
		private WebElement wrapped;
		private ClosedFiringWebDriver extendedDriver;

		private DefaultWebElement(final WebElement element,
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
										extendedDriver.originalDriver);
								throw e.getTargetException();
							}
						}
					});
		}

		private RemoteWebElement unpackOriginalElement(){
			WebElement original = wrapped;
			while (original instanceof WrapsElement) {
				original = ((WrapsElement) original).getWrappedElement();				
			}
			return (RemoteWebElement) original;
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
			return new DefaultWebElement(element.findElement(by), extendedDriver);
		}

		@Override
		public List<WebElement> findElements(By by) {
			List<WebElement> temp = element.findElements(by);
			List<WebElement> result = new ArrayList<WebElement>(temp.size());
			for (WebElement element : temp) {
				result.add(new DefaultWebElement(element, extendedDriver));
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
					extendedDriver.originalDriver, wrapped);
			element.submit();
			extendedDriver.extendedDispatcher.afterSubmit(
					extendedDriver.originalDriver, wrapped);
		}

		@Override
		public Coordinates getCoordinates() {
			return ((Locatable) element).getCoordinates();

		}

		public WebElement getWrappedElement() {
			return wrapped;
		}

		@Override
		public WebElement findElementByAccessibilityId(String arg0) {
			return new DefaultWebElement(
					new MobileElement(unpackOriginalElement(),
							(MobileDriver) extendedDriver
									.unpackOriginalDriver())
							.findElementByAccessibilityId(arg0),
					extendedDriver);
		}

		@Override
		public List<WebElement> findElementsByAccessibilityId(String arg0) {
			List<WebElement> found = new MobileElement(unpackOriginalElement(),
					(MobileDriver) extendedDriver.unpackOriginalDriver())
					.findElementsByAccessibilityId(arg0);
			List<WebElement> result = new ArrayList<WebElement>();
			for (WebElement e : found) {
				result.add(new DefaultWebElement(e, extendedDriver));
			}
			return result;
		}

		@Override
		public WebElement findElementByAndroidUIAutomator(String arg0) {
			return new DefaultWebElement(
					new MobileElement(unpackOriginalElement(),
							(MobileDriver) extendedDriver
									.unpackOriginalDriver())
							.findElementByAndroidUIAutomator(arg0),
					extendedDriver);
		}

		@Override
		public List<WebElement> findElementsByAndroidUIAutomator(String arg0) {
			List<WebElement> found = new MobileElement(unpackOriginalElement(),
					(MobileDriver) extendedDriver.unpackOriginalDriver())
					.findElementsByAndroidUIAutomator(arg0);
			List<WebElement> result = new ArrayList<WebElement>();
			for (WebElement e : found) {
				result.add(new DefaultWebElement(e, extendedDriver));
			}
			return result;
		}

		@Override
		public WebElement findElementByIosUIAutomation(String arg0) {
			return new DefaultWebElement(
					new MobileElement(unpackOriginalElement(),
							(MobileDriver) extendedDriver
									.unpackOriginalDriver())
							.findElementByIosUIAutomation(arg0),
					extendedDriver);
		}

		@Override
		public List<WebElement> findElementsByIosUIAutomation(String arg0) {
			List<WebElement> found = new MobileElement(unpackOriginalElement(),
					(MobileDriver) extendedDriver.unpackOriginalDriver())
					.findElementsByIosUIAutomation(arg0);
			List<WebElement> result = new ArrayList<WebElement>();
			for (WebElement e : found) {
				result.add(new DefaultWebElement(e, extendedDriver));
			}
			return result;
		}

		@Override
		public RemoteWebElement unpackRemoteWebElement() {
			return unpackOriginalElement();
		}

	}

	/**
	 * @author s.tihomirov
	 * 
	 */
	static class DefaultAlert implements Alert {
		private Alert alert;
		private ClosedFiringWebDriver driver;

		private DefaultAlert(Alert alert,
				ClosedFiringWebDriver driver) {
			this.alert = alert;
			this.driver = driver;
		}

		@Override
		public void accept() {
			driver.extendedDispatcher.beforeAlertAccept(
					driver.originalDriver, alert);
			alert.accept();
			driver.extendedDispatcher.afterAlertAccept(
					driver.originalDriver, alert);
		}

		@Override
		@Beta
		public void authenticateUsing(Credentials arg0) {
			alert.authenticateUsing(arg0);
		}

		@Override
		public void dismiss() {
			driver.extendedDispatcher.beforeAlertDismiss(
					driver.originalDriver, alert);
			alert.dismiss();
			driver.extendedDispatcher.afterAlertDismiss(
					driver.originalDriver, alert);
		}

		@Override
		public String getText() {
			return alert.getText();
		}

		@Override
		public void sendKeys(String arg0) {
			driver.extendedDispatcher.beforeAlertSendKeys(
					driver.originalDriver, alert, arg0);
			alert.sendKeys(arg0);
			driver.extendedDispatcher.afterAlertSendKeys(
					driver.originalDriver, alert, arg0);
		}

	}

	private final List<IExtendedWebDriverEventListener> extendedEventListeners = new ArrayList<IExtendedWebDriverEventListener>();

	private final WebDriver originalDriver;
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

	ClosedFiringWebDriver(WebDriver driver) {
		super(driver);
		originalDriver = driver;
	}

	public void register(IExtendedWebDriverEventListener eventListener) {
		super.register(eventListener);
		extendedEventListeners.add(eventListener);
	}

	public void unregister(IExtendedWebDriverEventListener eventListener) {
		super.unregister(eventListener);
		extendedEventListeners.remove(eventListener);
	}

	public Capabilities getCapabilities() {
		return ((HasCapabilities) originalDriver).getCapabilities();
	}

	public WebDriver.TargetLocator switchTo() {
		WebDriver.TargetLocator target = super.switchTo();
		return new DefaultTargetLocator(target, this);
	}

	public List<WebElement> findElements(By by) {
		List<WebElement> temp = super.findElements(by);
		List<WebElement> result = new ArrayList<WebElement>(temp.size());
		for (WebElement element : temp) {
			result.add(new DefaultWebElement(element, this));
		}
		return result;
	}

	public WebElement findElement(By by) {
		WebElement temp = super.findElement(by);
		return new DefaultWebElement(temp, this);
	}

	public Options manage() {
		Options option = super.manage();
		return new DefaultOptions(option, this);
	}

	public <X> X getScreenshotAs(OutputType<X> target) {
		X result = null;
		try {
			result = ((TakesScreenshot) originalDriver).getScreenshotAs(target);
		} catch (ClassCastException e) {
			WebDriver augmentedDriver = new Augmenter().augment(originalDriver);
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
		((MobileDriver) originalDriver).context(name);
		return this;
	}

	@Override
	public Set<String> getContextHandles() {
		return ((MobileDriver) originalDriver).getContextHandles();
	}

	@Override
	public String getContext() {
		return ((MobileDriver) originalDriver).getContext();
	}

	@Override
	public Response execute(String driverCommand, Map<String, ?> parameters) {
		return ((MobileDriver) originalDriver).execute(driverCommand,
				parameters);
	}

	@Override
	public TouchAction performTouchAction(TouchAction touchAction) {
		return ((MobileDriver) originalDriver)
				.performTouchAction(touchAction);
	}

	@Override
	public void performMultiTouchAction(MultiTouchAction multiAction) {
		((MobileDriver) originalDriver)
				.performMultiTouchAction(multiAction);
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		((Rotatable) originalDriver).rotate(orientation);		
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ((Rotatable) originalDriver).getOrientation();
	}

	@Override
	public WebElement findElementByIosUIAutomation(String using) {
		return ((FindsByIosUIAutomation) originalDriver)
				.findElementByIosUIAutomation(using);
	}

	@Override
	public List<WebElement> findElementsByIosUIAutomation(String using) {
		return ((FindsByIosUIAutomation) originalDriver)
				.findElementsByIosUIAutomation(using);
	}

	@Override
	public WebElement findElementByAndroidUIAutomator(String using) {
		return ((FindsByAndroidUIAutomator) originalDriver)
				.findElementByAndroidUIAutomator(using);
	}

	@Override
	public List<WebElement> findElementsByAndroidUIAutomator(String using) {
		return ((FindsByAndroidUIAutomator) originalDriver)
				.findElementsByAndroidUIAutomator(using);
	}

	@Override
	public WebElement findElementByAccessibilityId(String using) {
		return ((FindsByAccessibilityId) originalDriver)
				.findElementByAccessibilityId(using);
	}

	@Override
	public List<WebElement> findElementsByAccessibilityId(String using) {
		return ((FindsByAccessibilityId) originalDriver)
				.findElementsByAccessibilityId(using);
	}
	
	private WebDriver unpackOriginalDriver(){
		WebDriver original = originalDriver;
		while (original instanceof WrapsDriver) {
			original = ((WrapsDriver) original).getWrappedDriver();				
		}
		return original;
	}
	
}