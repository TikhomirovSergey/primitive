package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IHidesKeyboard;

public abstract class KeyboardHider extends WebdriverInterfaceImplementor
		implements IHidesKeyboard {
	public KeyboardHider(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
