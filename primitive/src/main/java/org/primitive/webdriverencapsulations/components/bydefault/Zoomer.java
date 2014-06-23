package org.primitive.webdriverencapsulations.components.bydefault;

import org.openqa.selenium.WebDriver;
import org.primitive.webdriverencapsulations.interfaces.IZoom;

public abstract class Zoomer extends WebdriverInterfaceImplementor implements IZoom {
	public Zoomer(WebDriver driver) {
		super(driver);
		delegate = this.driver;
	}
}
