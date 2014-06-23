package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IPinch;

public abstract class Pinch extends WebdriverInterfaceImplementor implements IPinch {
	public Pinch(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
