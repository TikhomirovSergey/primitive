package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.ISendsKeyEvent;

public abstract class KeyEventSender extends WebdriverInterfaceImplementor implements
		ISendsKeyEvent {
	public KeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
