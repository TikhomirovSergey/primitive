package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.primitive.webdriverencapsulations.components.overriden.Awaiting;

public abstract class AlertHandler extends WebdriverInterfaceImplementor implements Alert {

	public AlertHandler(WebDriver driver, long secTimeOut)
			throws NoAlertPresentException {
		super(driver);
		try {
			delegate = new Awaiting(driver).awaitCondition(secTimeOut,
					ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {
			throw new NoAlertPresentException(e.getMessage(), e);
		}
	}

}
