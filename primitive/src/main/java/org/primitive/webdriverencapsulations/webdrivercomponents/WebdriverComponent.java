package org.primitive.webdriverencapsulations.webdrivercomponents;

import org.openqa.selenium.WebDriver;
import org.primitive.interfaces.IDestroyable;

/**
 * @author s.tihomirov
 * For any class that executes specific actions using webdriver
 */
public abstract class WebdriverComponent implements IDestroyable{
	
	protected WebDriver driver;
	
	public WebdriverComponent(WebDriver driver)
	{
		this.driver = driver;
	}

	@Override
	public void destroy()
	{
		try 
		{
			finalize();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
		}
	}
	
}
