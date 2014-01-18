package org.primitive.webdriverencapsulations.webdrivercomponents;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * @author s.tihomirov Fluent waiting for some window conditions
 */
public final class FluentWindowConditions extends WebdriverComponent{

	public FluentWindowConditions(WebDriver driver) {
		super(driver);
	}

	// is here new browser window?
	// returns handle of a new browser window that we have been waiting for
	// specified time
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles) {
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {
			newHandles.removeAll(oldHandles);
			newHandle = (String) newHandles.toArray()[0];
			return newHandle;
		}
		return newHandle;

	}

	// is here new browser window?
	// returns handle of a new browser window that we have been waiting for
	// specified time
	// new browser window should have defined title. We can specify title in
	// this way:
	// title, title*, *title, *title*,tit*le and etc
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles, String title) {
		Pattern titlePattern = Pattern.compile(title);
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {

			newHandles.removeAll(oldHandles);
			for (String handle : newHandles) {
				from.switchTo().window(handle);
				Matcher titleMatcher = titlePattern.matcher(from.getTitle());
				if (titleMatcher.matches()) {
					newHandle = handle;
					return newHandle;
				}
			}
		}
		return newHandle;
	}

	// is here new browser window?
	// returns handle of a new browser window that we have been waiting for
	// specified time
	// new browser window should have page that loads by specified URL
	private String getNewHandle(final WebDriver from,
			final Set<String> oldHandles, URL url) {
		String newHandle = null;
		Set<String> newHandles = from.getWindowHandles();
		if (newHandles.size() > oldHandles.size()) {
			newHandles.removeAll(oldHandles);
			for (String handle : newHandles) {
				from.switchTo().window(handle);
				
				if (from.getCurrentUrl().equals(url.toString())) {
					newHandle = handle;
					return newHandle;
				}
			}
		}
		return newHandle;
	}

	// is there such window count? It it is true this method returns handle of
	// window
	private String getWindowHandleByIndex(final WebDriver from, int windowIndex) {
		Set<String> handles = from.getWindowHandles();
		if ((handles.size() - 1) >= windowIndex) {
			return new ArrayList<String>(handles).get(windowIndex);
		} else {
			return null;
		}
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<Boolean> isClosed(final String closingHandle) {
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(final WebDriver from) {
				return isClosed(from, closingHandle);
			}
		};
	}

	// is browser window closed?
	private Boolean isClosed(final WebDriver from, String handle) {
			Set<String> handles;
		try {
			handles = from.getWindowHandles();
		} catch (WebDriverException e) { // if all windows are closed
			return true;
		}

		if (!handles.contains(handle)) {
			return true;
		} else {
			return null;
		}
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> newWindowIsAppeared() {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles);
			}
		};
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> newWindowIsAppeared(final String title) {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles, title);
			}
		};
	}

	// fluent waiting of the result. See above
	public ExpectedCondition<String> newWindowIsAppeared(final URL url) {
		return new ExpectedCondition<String>() {
			Set<String> oldHandles = driver.getWindowHandles();

			public String apply(final WebDriver from) {
				return getNewHandle(from, oldHandles, url);
			}
		};
	}

	// fluent waiting for the result. See above
	public ExpectedCondition<String> suchWindowWithIndexIsPresent(final int windowIndex) {
		return new ExpectedCondition<String>() {
			public String apply(final WebDriver from) {
				return getWindowHandleByIndex(from, windowIndex);
			}
		};
	}
}
