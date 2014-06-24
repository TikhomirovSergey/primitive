package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IGetsNamedTextField;

public abstract class NamedTextFieldGetter extends
		WebdriverInterfaceImplementor implements IGetsNamedTextField {
	public NamedTextFieldGetter(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
