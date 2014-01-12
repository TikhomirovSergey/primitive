package org.primitive.webdriverencapsulations.interfaces;

import org.openqa.selenium.WebDriver.Window;

/**
 * @author s.tihomirov
 *
 */
public interface IExtendedWindow extends Window, ITakesPictureOfItSelf{
	public String getWindowHandle();
	
	public String getCurrentUrl();
	
	public String getTitle();
}
