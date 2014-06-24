package org.primitive.webdriverencapsulations.interfaces;

import org.openqa.selenium.WebElement;

public interface IScrollsTo {
	public WebElement scrollTo(String text);
	public WebElement scrollToExact(String text);
}
