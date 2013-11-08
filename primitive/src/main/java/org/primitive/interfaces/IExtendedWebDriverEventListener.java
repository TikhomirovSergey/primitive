package org.primitive.interfaces;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * @author s.tihomirov
 * For some functions that are not declared in WebDriverEventListener
 */
public interface IExtendedWebDriverEventListener extends WebDriverEventListener 
{
	//
	public void beforeWindowClose(WebDriver driver);
	//
	public void afterWindowClose(WebDriver driver);
	//
	public void beforeSubmit(WebDriver driver, WebElement element);
	//
	public void afterSubmit(WebDriver driver, WebElement element);
	
	public void beforeAlertDismiss(WebDriver driver, Alert alert);
	public void afterAlertDismiss(WebDriver driver, Alert alert);	
	
	public void beforeAlertAccept(WebDriver driver, Alert alert);
	public void afterAlertAccept(WebDriver driver, Alert alert);
	
	public void beforeAlertSendKeys(WebDriver driver, Alert alert, String keys);
	public void afterAlertSendKeys(WebDriver driver, Alert alert, String keys);	
		
	public void beforeWindowSetSize(WebDriver driver, Window window, Dimension size);
	public void afterWindowSetSize(WebDriver driver, Window window, Dimension size);	
	
	public void beforeWindowSetPosition(WebDriver driver, Window window, Point position);
	public void afterWindowSetPosition(WebDriver driver, Window window, Point position);	
	
	public void beforeWindowMaximize(WebDriver driver, Window window);
	public void afterWindowMaximize(WebDriver driver, Window window);		
	
	public void beforeWindowRefresh(WebDriver driver, Navigation navigate);
	public void afterWindowRefresh(WebDriver driver, Navigation navigate);
	
	public void beforeWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts, long timeOut, TimeUnit timeUnit);
	public void afterWebDriverSetTimeOut(WebDriver driver, Timeouts timeouts, long timeOut, TimeUnit timeUnit);		
}
