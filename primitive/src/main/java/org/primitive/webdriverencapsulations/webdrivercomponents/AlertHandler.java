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
		try
		{
			alert = driver.switchTo().alert();
		}
		catch (NoAlertPresentException e)
		{
			destroy();
			throw e;
		}	
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
			destroy();
			throw new NoAlertPresentException(e.getMessage(),e);
		}
		catch (NoAlertPresentException e)
		{
			destroy();
			throw e;
		}
	}

	public void accept() throws NoAlertPresentException
	{
		try 
		{
			alert.accept();
	    } 
		finally
		{
			destroy();
		}
	}

	public void authenticateUsing(Credentials credentials) throws NoAlertPresentException
	{
		try 
		{
			alert.authenticateUsing(credentials);
	    } 
		catch (NoAlertPresentException e) 
	    {
			destroy();
	        throw e;
	    }
	}

	public void dismiss() throws NoAlertPresentException
	{
		try 
		{
			alert.dismiss();
	    } 
		finally
		{
			destroy();
		}
	}

	public String getText() throws NoAlertPresentException
	{
		try 
		{
			return(alert.getText());
	    } 
		catch (NoAlertPresentException e) 
	    {
			destroy();
	        throw e;
	    }
	}

	public void sendKeys(String text) throws NoAlertPresentException
	{
		try 
		{
			alert.sendKeys(text);
	    } 
		catch (NoAlertPresentException e) 
	    {
			destroy();
	        throw e;
	    }
	}

}
