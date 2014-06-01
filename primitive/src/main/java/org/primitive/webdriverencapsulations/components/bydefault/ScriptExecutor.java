package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class ScriptExecutor extends WebdriverInterfaceImplementor implements
		JavascriptExecutor {

	public ScriptExecutor(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
