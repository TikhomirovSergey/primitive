package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IShakes;

public abstract class Shaker extends WebdriverInterfaceImplementor implements
		IShakes {
	public Shaker(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
