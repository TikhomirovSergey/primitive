package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IGetsAppStrings;

public abstract class AppStringGetter extends WebdriverInterfaceImplementor implements
		IGetsAppStrings {

	public AppStringGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
