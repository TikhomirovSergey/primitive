package org.primitive.webdriverencapsulations;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.WebElement;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IExtendedWebDriverEventListener;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.webdrivercomponents.ElementVisibility;

final class WebdriverInnerListener implements IExtendedWebDriverEventListener, IDestroyable {

	private ElementVisibility elementVisibility;
	
	void setElementVisibilityChecker(ElementVisibility elementVisibility)
	{
		this.elementVisibility = elementVisibility;
	}
	
	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		Photographer.takeAPictureOfAnInfo(arg1, arg0, "Element with value that is changed.");
	}

	@Override
	public void afterClickOn(WebElement arg0, WebDriver arg1) 
	{
		Log.message("Click on element has been successfully performed!");
	}

	@Override
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		Log.debug("Searching for web element has been finished. Locator is " + arg0.toString());
	}

	@Override
	public void afterNavigateBack(WebDriver arg0) {
		Log.message("Current URL is  " + arg0.getCurrentUrl());
		Photographer.takeAPictureOfAnInfo(arg0,"After navigation to previous url");
	}

	@Override
	public void afterNavigateForward(WebDriver arg0) 
	{
		Log.message("Current URL is  " + arg0.getCurrentUrl());
		Photographer.takeAPictureOfAnInfo(arg0,"After navigation to next url");
	}
	
	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		Log.message("Current URL is " + arg1.getCurrentUrl());
		Photographer.takeAPictureOfAnInfo(arg1,"After navigate to url " + arg0);
	}

	@Override
	public void afterScript(String arg0, WebDriver arg1) {
		Log.debug("Javascript  " + arg0 + " has been executed successfully!");
	}
	
	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		elementVisibility.throwIllegalVisibility(arg0);
		if (!arg0.isEnabled())
		{
			Log.warning("Webelement is disabled!");
			Photographer.takeAPictureOfAWarning(arg1, arg0, "Webelemet that is disabled");
			throw new InvalidElementStateException("Attempt to change value of disabled page element!");
		}
		Photographer.takeAPictureOfAnInfo(arg1, arg0, "Element with value that will be changed");
	}


	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1)
	{
		elementVisibility.throwIllegalVisibility(arg0);
		Photographer.takeAPictureOfAnInfo(arg1, arg0, "Click will be performed on this element");
	}

	@Override
	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		Log.debug("Searching for element by locator " + arg0.toString() + " has been started");
	}

	@Override
	public void beforeNavigateBack(WebDriver arg0) 
	{
		Log.message("Attempt to navigate to previous url. Current url is " + arg0.getCurrentUrl());
	}

	@Override
	public void beforeNavigateForward(WebDriver arg0) 
	{
		Log.message("Attempt to navigate to next url. Current url is " + arg0.getCurrentUrl());
	}

	@Override
	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		Log.message("Attempt to navigate to another url. Required url is " + arg0);
	}

	@Override
	public void beforeScript(String arg0, WebDriver arg1) 
	{
		Log.debug("Javascript execution has been started " + arg0);
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1)
	{
		Log.debug("An exception has been caught out.", arg0);
	}
	

	@Override
	public void beforeClose(WebDriver driver) 
	{
		Log.message("Attempt to close browser window...");
	}

	@Override
	public void afterClose(WebDriver driver) 
	{
		Log.message("Not any problem has occurred when browser window was closed...");			
	}

	@Override
	public void beforeSubmit(WebDriver driver, WebElement element) {
		elementVisibility.throwIllegalVisibility(element);
		Photographer.takeAPictureOfAnInfo(driver, element, "Element that will perform submit");			
	}

	@Override
	public void afterSubmit(WebDriver driver, WebElement element) {
		Photographer.takeAPictureOfAnInfo(driver, "After submit");
		Log.message("Submit has been performed successfully");	
	}

	@Override
	public void beforeDismiss(WebDriver driver, Alert alert) 
	{
		Log.message("Attempt to dismiss the alert...");
		
	}

	@Override
	public void afterDismiss(WebDriver driver, Alert alert) 
	{
		Log.message("Alert has been dismissed");
	}

	@Override
	public void beforeAccept(WebDriver driver, Alert alert) 
	{
		Log.message("Attempt to accept alert...");			
	}

	@Override
	public void afterAccept(WebDriver driver, org.openqa.selenium.Alert alert) 
	{
		Log.message("Alert has been accepted");		
	}

	@Override
	public void beforeSendKeys(WebDriver driver, Alert alert, String keys) 
	{
		Log.message("Attemt to send string " + keys + " to alert...");		
	}

	@Override
	public void afterSendKeys(WebDriver driver, Alert alert, String keys) 
	{
		Log.message("String " + keys + " has been sent to alert");			
	}

	@Override
	public void beforeSetSize(WebDriver driver, Window window, 	Dimension size) 
	{
		Log.message("Attempt to change window size. New height is " + Integer.toString(size.getHeight()) + 
					" new width is " + Integer.toString(size.getWidth()));
	}

	@Override
	public void afterSetSize(WebDriver driver, Window window, 	Dimension size) 
	{
		Log.message("Window size has been changed!");			
	}
	
	@Override
	public void beforeSetPosition(WebDriver driver, Window window, 	Point position) 
	{
		Log.message("Attempt to change window position. X " + Integer.toString(position.getX()) + 
					" Y " + Integer.toString(position.getY()));			
	}

	@Override
	public void afterSetPosition(WebDriver driver, Window window, Point position) 
	{
		Log.message("Window position has been changed!.");
	}
	
	@Override
	public void beforeMaximize(WebDriver driver, Window window) 
	{
		Log.message("Attempt to maximize browser window");
		
	}

	@Override
	public void afterMaximize(WebDriver driver, Window window) 
	{
		Log.message("Browser window has been maximized");		
	}

	@Override
	public void beforeRefresh(WebDriver driver, Navigation navigate) {
		Log.message("Attempt to refresh current browser window");
		
	}

	@Override
	public void afterRefresh(WebDriver driver, Navigation navigate) {
		Photographer.takeAPictureOfAnInfo(driver, "After window refresh");
		Log.message("Current browser window has been refreshed");		
	}

	@Override
	public void beforeSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		Log.debug("Attempt to set time out. Value is " + Long.toString(timeOut) + " time unit is " + timeUnit.toString());
		
	}

	@Override
	public void afterSetTimeOut(WebDriver driver, Timeouts timeouts,
			long timeOut, TimeUnit timeUnit) {
		Log.message("Time out has been set. Value is " + Long.toString(timeOut) + " time unit is " + timeUnit.toString());		
	}

	@Override
	public <X> X beforeTakingScringShot(WebDriver driver,
			OutputType<X> target) {
		Log.debug("Attempt to take a picture...");
		return null;
	}

	@Override
	public <X> X afterTakingScringShot(WebDriver driver,
			OutputType<X> target) {
		Log.debug("Picture has been taken ...");
		return null;
	}

	@Override
	public void destroy() {
		try 
		{
			elementVisibility = null;
			this.finalize();
		} 
		catch (Throwable e) 
		{
			e.printStackTrace();
			Log.warning(e.getMessage(),e);
		}
		
	}

}
