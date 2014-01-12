package org.primitive.webdriverencapsulations.webdrivercomponents;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;

public final class DriverLogs extends WebdriverComponent implements Logs {

	public DriverLogs(WebDriver driver) {
		super(driver);
	}

	@Override
	public LogEntries get(String logType) 	{
		return driver.manage().logs().get(logType);
	}

	@Override
	public Set<String> getAvailableLogTypes() 	{
		return driver.manage().logs().getAvailableLogTypes();
	}

}
