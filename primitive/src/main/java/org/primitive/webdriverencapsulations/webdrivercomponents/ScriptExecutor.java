package org.primitive.webdriverencapsulations.webdrivercomponents;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public final class ScriptExecutor extends WebdriverComponent implements
		JavascriptExecutor {

	public ScriptExecutor(WebDriver driver) {
		super(driver);
	}

	public Object executeAsyncScript(String script, Object...  args)
	{
		return(((JavascriptExecutor) driver).executeAsyncScript(script, args));
	}

	public Object executeScript(String script, Object... args)
	{
		return(((JavascriptExecutor) driver).executeScript(script, args));
	}

}
