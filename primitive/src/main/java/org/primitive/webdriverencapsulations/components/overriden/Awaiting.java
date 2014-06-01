package org.primitive.webdriverencapsulations.components.overriden;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.primitive.webdriverencapsulations.components.WebdriverComponent;

public final class Awaiting extends WebdriverComponent {

	public Awaiting(WebDriver driver) {
		super(driver);
	}

	@SuppressWarnings("unchecked")
	public <T> T awaitCondition(long secTimeOut, ExpectedCondition<?> condition)
			throws TimeoutException {
		return (T) new WebDriverWait(driver, secTimeOut).until(condition);
	}

	@SuppressWarnings("unchecked")
	public <T> T awaitCondition(long secTimeOut, long sleepInMillis,
			ExpectedCondition<?> condition) throws TimeoutException {
		return (T) new WebDriverWait(driver, secTimeOut, sleepInMillis)
				.until(condition);
	}

}
