package org.primitive.webdriverencapsulations.firing;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class SealedDriver implements WebDriver {

	final WebDriver sealed; 
	
	SealedDriver(WebDriver driver){
		sealed = driver;
	}
	
	@Override
	public TargetLocator switchTo() {
		return sealed.switchTo();
	}
	
	@Override
	public void quit() {
		sealed.quit();				
	}
	
	@Override
	public Navigation navigate() {
		return sealed.navigate();
	}
	
	@Override
	public Options manage() {
		return sealed.manage();
	}
	
	@Override
	public Set<String> getWindowHandles() {
		return sealed.getWindowHandles();
	}
	
	@Override
	public String getWindowHandle() {
		return sealed.getWindowHandle();
	}
	
	@Override
	public String getTitle() {
		return sealed.getTitle();
	}
	
	@Override
	public String getPageSource() {
		return sealed.getPageSource();
	}
	
	@Override
	public String getCurrentUrl() {
		return sealed.getCurrentUrl();
	}
	
	@Override
	public void get(String url) {
		sealed.get(url);
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		return sealed.findElements(by);
	}
	
	@Override
	public WebElement findElement(By by) {
		return sealed.findElement(by);
	}
	
	@Override
	public void close() {
		sealed.close();				
	}

}
