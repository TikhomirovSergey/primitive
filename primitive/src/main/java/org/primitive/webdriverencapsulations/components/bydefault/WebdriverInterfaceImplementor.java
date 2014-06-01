package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.components.WebdriverComponent;

abstract class WebdriverInterfaceImplementor extends WebdriverComponent {
	Object delegate;
	
	public WebdriverInterfaceImplementor(WebDriver driver) {
		super(driver);
	}

}
