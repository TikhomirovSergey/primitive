package org.primitive.webdriverencapsulations;

import java.util.Set;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.components.overriden.Awaiting;

public abstract class AbstractSwitcher implements IDestroyable {

	final Awaiting awaiting;
	final WebDriverEncapsulation driverEncapsulation;
	boolean isAlive = true;

	AbstractSwitcher(WebDriverEncapsulation initialDriverEncapsulation) {
		driverEncapsulation = initialDriverEncapsulation;
		awaiting = driverEncapsulation.getAwaiting();
	}

	@Override
	public abstract void destroy();
	abstract void changeActive(String handle);
	
	protected synchronized void takeAPictureOfAFine(String handle,
			String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAFine(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	protected synchronized void takeAPictureOfAnInfo(String handle,
			String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAnInfo(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	protected synchronized void takeAPictureOfASevere(String handle,
			String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfASevere(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	protected synchronized void takeAPictureOfAWarning(String handle,
			String Comment) {
		changeActive(handle);
		Photographer.takeAPictureOfAWarning(
				driverEncapsulation.getWrappedDriver(), Comment);
	}

	WebDriverEncapsulation getWebDriverEncapsulation(){
		return driverEncapsulation;
	}

	WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	boolean isAlive() {
		return isAlive;
	}

	synchronized void switchTo(String Handle)
			throws NoSuchWindowException {
		changeActive(Handle);
	}
	public abstract String getHandleByInex(int windowIndex);
	public abstract  Set<String> getHandles();
	public abstract String switchToNew();
	public abstract String switchToNew(long timeOutInSeconds);
	public abstract String switchToNew(long timeOutInSeconds, String stringIdentifier);
	public abstract String switchToNew(String stringIdentifier);
}
