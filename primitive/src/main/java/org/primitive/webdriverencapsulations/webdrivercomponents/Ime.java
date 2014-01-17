package org.primitive.webdriverencapsulations.webdrivercomponents;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.ImeHandler;

public final class Ime extends WebdriverComponent implements ImeHandler {

	public Ime(WebDriver driver) {
		super(driver);
	}

	@Override
	public void activateEngine(String arg0) {
		driver.manage().ime().activateEngine(arg0);
	}

	@Override
	public void deactivate() {
		driver.manage().ime().deactivate();
	}

	@Override
	public String getActiveEngine() {
		return driver.manage().ime().getActiveEngine();
	}

	@Override
	public List<String> getAvailableEngines() {
		return driver.manage().ime().getAvailableEngines();
	}

	@Override
	public boolean isActivated() {
		return driver.manage().ime().isActivated();
	}

}
