package org.primitive.interfaces;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Point;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
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
	public void beforeClose(WebDriver driver);
	//
	public void afterClose(WebDriver driver);
	//
	public void beforeSubmit(WebDriver driver, WebElement element);
	//
	public void afterSubmit(WebDriver driver, WebElement element);
	
	public void beforeDismiss(WebDriver driver, Alert alert);
	public void afterDismiss(WebDriver driver, Alert alert);	
	
	public void beforeAccept(WebDriver driver, Alert alert);
	public void afterAccept(WebDriver driver, Alert alert);
	
	public void beforeSendKeys(WebDriver driver, Alert alert, String keys);
	public void afterSendKeys(WebDriver driver, Alert alert, String keys);	
		
	public void beforeSetSize(WebDriver driver, Window window, Dimension size);
	public void afterSetSize(WebDriver driver, Window window, Dimension size);	
	
	public void beforeSetPosition(WebDriver driver, Window window, Point position);
	public void afterSetPosition(WebDriver driver, Window window, Point position);	
	
	public void beforeMaximize(WebDriver driver, Window window);
	public void afterMaximize(WebDriver driver, Window window);		
	
	public void beforeRefresh(WebDriver driver, Navigation navigate);
	public void afterRefresh(WebDriver driver, Navigation navigate);
	
	public void beforeSetTimeOut(WebDriver driver, Timeouts timeouts, long timeOut, TimeUnit timeUnit);
	public void afterSetTimeOut(WebDriver driver, Timeouts timeouts, long timeOut, TimeUnit timeUnit);	
	
	public <X> X beforeTakingScringShot(WebDriver driver, OutputType<X> target);
	public <X> X afterTakingScringShot(WebDriver driver, OutputType<X> target);		
}
