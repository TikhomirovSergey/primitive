package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IPerformsTouchActions;

public abstract class TouchActionsPerformer extends
		WebdriverInterfaceImplementor implements IPerformsTouchActions {
	public TouchActionsPerformer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
