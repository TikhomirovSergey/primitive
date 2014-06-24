package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IComplexFind;

public abstract class ComplexFinder extends WebdriverInterfaceImplementor
		implements IComplexFind {
	public ComplexFinder(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
