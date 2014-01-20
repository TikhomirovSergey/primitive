package org.primitive.webdriverencapsulations.eventlisteners;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

public interface IUnhandledWindowEventListener {
	public void onUnhandledWindowHandleHasBeenFound(WebDriver switcher);
	public void onUnhandledAlert(Alert alert);	
}
