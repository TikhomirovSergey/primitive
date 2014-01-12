package org.primitive.webdriverencapsulations.webdrivercomponents;

import java.net.URL;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Window;

public final class WindowTool extends WebdriverComponent implements Navigation,
		Window {

	public WindowTool(WebDriver driver) {
		super(driver);
	}

	public void back()	{
		driver.navigate().back();			
	}

	public void forward()	{
		driver.navigate().forward();	
	}

	@Override
	public Point getPosition() 	{
		return driver.manage().window().getPosition();
	}

	@Override
	public Dimension getSize() {
		return driver.manage().window().getSize();
	}

	@Override
	public void maximize() {
		driver.manage().window().maximize();
		
	}

	@Override
	public void refresh() 
	{
		driver.navigate().refresh();			
	}

	@Override
	public void setPosition(Point arg0) {
		driver.manage().window().setPosition(arg0);
		
	}

	@Override
	public void setSize(Dimension arg0) {
		driver.manage().window().setSize(arg0);			
	}

	public void to(String link)	{
		driver.navigate().to(link);
	}

	@Override
	public void to(URL url) {
		driver.navigate().to(url);			
	}

}
