package org.primitive.webdriverencapsulations.webdrivercomponents;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.ui.ExpectedConditions;

public final class AlertHandler extends WebdriverComponent implements Alert {

	private Alert alert;
	
	public AlertHandler(WebDriver driver) {
		super(driver);
		alert = driver.switchTo().alert();
	}

	public AlertHandler(WebDriver driver, long secTimeOut) throws NoAlertPresentException
	{
		super(driver);
		try
		{
			alert = new Awaiting(driver).awaitCondition(secTimeOut, ExpectedConditions.alertIsPresent());
		}
		catch (TimeoutException e)
		{
			throw new NoAlertPresentException(e.getMessage(),e);
		}
	}

	public void accept() throws NoAlertPresentException
	{
		alert.accept();
	}

	public void authenticateUsing(Credentials credentials) throws NoAlertPresentException
	{
		alert.authenticateUsing(credentials);
	}

	public void dismiss() throws NoAlertPresentException
	{
		alert.dismiss();
	}

	public String getText() throws NoAlertPresentException
	{
		return(alert.getText());
	}

	public void sendKeys(String text) throws NoAlertPresentException
	{
		alert.sendKeys(text);
	}

}
