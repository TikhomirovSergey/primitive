package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.ISendsMetastateKeyEvent;

public abstract class MetastateKeyEventSender extends WebdriverInterfaceImplementor implements
		ISendsMetastateKeyEvent {
	public MetastateKeyEventSender(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
