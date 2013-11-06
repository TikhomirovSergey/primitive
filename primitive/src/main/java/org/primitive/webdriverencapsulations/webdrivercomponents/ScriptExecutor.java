package org.primitive.webdriverencapsulations.webdrivercomponents;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.primitive.logging.Log;

public final class ScriptExecutor extends WebdriverComponent implements
		JavascriptExecutor {

	public ScriptExecutor(WebDriver driver) {
		super(driver);
	}

	public Object executeAsyncScript(String script, Object...  args)
	{
		try
		{
			return(((JavascriptExecutor) driver).executeAsyncScript(script, args));
		}
		catch (Exception e)
		{
			Log.warning("JavaScript: " + script + " has not been executed! Error:  " + e.getMessage(), e);
			throw e;
		}
	}

	public Object executeScript(String script, Object... args)
	{
		try
		{
			return(((JavascriptExecutor) driver).executeScript(script, args));
		}
		catch (Exception e)
		{
			Log.warning("JavaScript  " + script + " has not been executed! Error: " + e.getMessage(), e);
			throw e;
		}
	}

}
