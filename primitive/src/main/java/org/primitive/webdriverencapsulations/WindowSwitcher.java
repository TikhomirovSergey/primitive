package org.primitive.webdriverencapsulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.primitive.configuration.commonhelpers.WindowsTimeOuts;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.components.bydefault.AlertHandler;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.overriden.FluentWindowConditions;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;

public final class WindowSwitcher extends AbstractSwitcher {
	private final static List<WindowSwitcher> swithcerList = Collections
			.synchronizedList(new ArrayList<WindowSwitcher>());
	private final WindowTimeOuts windowTimeOuts;
	private final FluentWindowConditions fluent;
	private final WindowReceptionist windowReceptionist = new WindowReceptionist();

	void changeActive(String handle)
			throws NoSuchWindowException, UnhandledAlertException {
		Set<String> handles = getHandles();
		if (!handles.contains(handle)) {
			throw new NoSuchWindowException("There is no window with handle "
					+ handle + "!");
		}
		try {
			driverEncapsulation.getWrappedDriver().switchTo().window(handle);
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}
	}

	/**
	 * returns WindowSwither instance that exists or creates a new instance
	 */
	public static WindowSwitcher get(WebDriverEncapsulation driver) {
		for (WindowSwitcher switcher : swithcerList) {
			if (switcher.driverEncapsulation == driver) {
				return switcher;
			}
		}

		return new WindowSwitcher(driver);
	}

	private WindowSwitcher(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		windowTimeOuts = driverEncapsulation.getWindowTimeOuts();
		fluent = new FluentWindowConditions(driverEncapsulation.getWrappedDriver());
		swithcerList.add(this);
	}

	@Override
	/**
	 * returns window handle by it's index
	 */
	public String getHandleByInex(int windowIndex)
			throws NoSuchWindowException {
		try {
			Log.debug("Attempt to get window that is specified by index "
					+ Integer.toString(windowIndex) + "...");
			WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
			long timeOut = windowTimeOuts.getTimeOut(timeOuts.getWindowCountTimeOutSec(),
					windowTimeOuts.defaultTime);
			return awaiting.awaitCondition(timeOut, 100, 
					fluent.suchWindowWithIndexIsPresent(windowIndex));
		} catch (TimeoutException e) {
			throw new NoSuchWindowException("Can't find window! Index out of bounds! Specified index is "
							+ Integer.toString(windowIndex)
							+ " is more then actual window count", e);
		}
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time
	 */
	public synchronized String switchToNew(long timeOutInSeconds)
			throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds) + " seconds.");
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared());
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration
	 */
	public String switchToNew() throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(
				timeOuts.getNewWindowTimeOutSec(),
				windowTimeOuts.defaultTimeForNewWindow);
		return switchToNew(timeOut);
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has defined title. We can specify title partially
	 * as a regular expression
	 */
	public synchronized String switchToNew(long timeOutInSeconds, String title)
			throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds)
					+ " seconds. New window should have title " + title);
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared(title));
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window with title " + title
							+ " ! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has defined title. We can
	 * specify title partially
	 * as a regular expression
	 */
	public String switchToNew(String title) throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(
				timeOuts.getNewWindowTimeOutSec(),
				windowTimeOuts.defaultTimeForNewWindow);
		return switchToNew(timeOut, title);
	}

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has page that loads by specified URLs. We can specify it as regular expression list
	 */
	public synchronized String switchToNew(long timeOutInSeconds, List<String> urls)
			throws NoSuchWindowException {
		try {
			Log.debug("Waiting a new window for "
					+ Long.toString(timeOutInSeconds)
					+ " seconds. New window should have page "
					+ " that is loaded by specified URLs. Urls are "
					+ urls.toString());
			String newHandle = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newWindowIsAppeared(urls));
			changeActive(newHandle);
			return newHandle;
		} catch (TimeoutException e) {
			throw new NoSuchWindowException(
					"There is no new window that loads by " + urls.toString()
							+ " ! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has page that loads by
	 * specified URL. We can specify it as regular expression list
	 */
	public String switchToNew(List<String> urls) throws NoSuchWindowException {
		WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(
				timeOuts.getNewWindowTimeOutSec(),
				windowTimeOuts.defaultTimeForNewWindow);
		return switchToNew(timeOut, urls);
	}

	public synchronized String getWindowURLbyHandle(String handle)
			throws NoSuchWindowException {
		changeActive(handle);
		return (driverEncapsulation.getWrappedDriver().getCurrentUrl());
	}

	public synchronized String getTitleByHandle(String handle)
			throws NoSuchWindowException {
		changeActive(handle);
		return (driverEncapsulation.getWrappedDriver().getTitle());
	}

	public synchronized void destroy() {
		swithcerList.remove(this);
		isAlive = false;

		List<IExtendedWindow> windowsToBeDestroyed = windowReceptionist.getInstantiatedWindows();
		for (IExtendedWindow window : windowsToBeDestroyed) {
			((IDestroyable) window).destroy();
		}
	}

	public synchronized void close(String handle)
			throws UnclosedWindowException, NoSuchWindowException,
			UnhandledAlertException, UnreachableBrowserException {
		WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		long timeOut = windowTimeOuts.getTimeOut(
				timeOuts.getWindowClosingTimeOutSec(),
				windowTimeOuts.defaultTime);

		try {
			changeActive(handle);
			WebDriver driver = driverEncapsulation.getWrappedDriver();
			driver.switchTo().window(handle).close();
		} catch (UnhandledAlertException | NoSuchWindowException e) {
			throw e;
		}

		try {
			awaiting.awaitCondition(timeOut, fluent.isClosed(handle));
		} catch (TimeoutException e) {
			throw new UnclosedWindowException("Window hasn't been closed!", e);
		}

		int actualWinCount = 0;
		try {
			actualWinCount = getHandles().size();
		} catch (WebDriverException e) { // if all windows are closed
			actualWinCount = 0;
		} finally {
			if (actualWinCount == 0) {
				destroy();
				driverEncapsulation.destroy();
			}
		}
	}

	public Set<String> getHandles() {
		return (driverEncapsulation.getWrappedDriver().getWindowHandles());
	}

	public synchronized Alert getAlert() throws NoAlertPresentException {
		WindowsTimeOuts timeOuts = windowTimeOuts.getTimeOuts();
		return ComponentFactory.getComponent(AlertHandler.class,
				driverEncapsulation.getWrappedDriver(),
				new Class[] {long.class}, new Object[] { windowTimeOuts
						.getTimeOut(timeOuts.getSecsForAwaitinAlertPresent(),
								windowTimeOuts.defaultTime) });

	}
	
	public synchronized Alert getAlert(long timeOut) throws NoAlertPresentException {
		return  ComponentFactory.getComponent(AlertHandler.class,
				driverEncapsulation.getWrappedDriver(),
				new Class[] {long.class}, new Object[] {timeOut});
	}

	WindowReceptionist getWindowReceptionist(){
		return windowReceptionist;
	}
}