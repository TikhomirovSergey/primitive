package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IScrollsTo;

public abstract class ScrollerTo extends WebdriverInterfaceImplementor
		implements IScrollsTo {

	public ScrollerTo(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}

}
