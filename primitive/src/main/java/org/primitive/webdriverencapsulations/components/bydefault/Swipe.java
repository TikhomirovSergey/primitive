package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.ISwipe;

public abstract class Swipe extends WebdriverInterfaceImplementor implements
		ISwipe {
	public Swipe(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
