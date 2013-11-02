package org.primitive.webdriverencapsulations;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation.Awaiting;

/**
 * @author s.tihomirov
 * It checks element visibility
 */
final class ElementVisibility implements IConfigurable {

	private Awaiting awaiting;
	private WebDriver driver;
	
	private final long defaultTimeOut = 20; //We will wait visibility of a web element for 20 seconds
	//if there is no settings		
	private long timeOut;
	
	ElementVisibility(Awaiting awaiting, WebDriver driver)
	{
		this.awaiting = awaiting;
		this.driver   = driver;
	}
	
	public synchronized void resetAccordingTo(Configuration config)
	{
		Long time = config.getWebElementVisibility().getVisibilityTimeOutSec();
		if (time==null)
		{
			timeOut = defaultTimeOut;
		}
		else
		{
			timeOut = time;
		}
	}
	
	//Here the algorithm of visibility waiting
	private ExpectedCondition<Boolean> getVisibilityOf(final WebElement elementToBeVisible)
	{	
		return new ExpectedCondition<Boolean>()
		{
			public Boolean apply(final WebDriver from)
			{
				try
				{   //the element should be displayed on the page
					Boolean result = null;
					if (elementToBeVisible.isDisplayed())
					{
						result = true;
					} //if it is transparent
					else
					{
						String opacity = elementToBeVisible.getCssValue("opacity");
						if (String.valueOf("0").equals(opacity))
						{
							result = true;
						}
					}
					return result;
				}
				catch (StaleElementReferenceException e)
				{
					return null;
				}
				
			}
		};
	}
	
	void throwIllegalVisibility(WebElement element)
	{
		try
		{
			awaiting.awaitCondition(timeOut, 
					getVisibilityOf(element));
		}
		catch (TimeoutException e)
		{
			Log.warning("Element is not visible!");
		  	Photographer.takeAPictureOfAWarning(driver, "Page with the invisible element!");
		  	throw new ElementNotVisibleException("Element is not visible!");
		}
	}

}
