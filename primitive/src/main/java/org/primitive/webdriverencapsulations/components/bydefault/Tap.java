package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.ITap;

public abstract class Tap extends WebdriverInterfaceImplementor implements ITap {
	public Tap(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
