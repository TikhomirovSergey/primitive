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

	/**
	 * returns handle by it's index
	 */
	public abstract String getHandleByInex(int windowIndex);
	public abstract  Set<String> getHandles();

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

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration
	 */
	public abstract String switchToNew();

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time
	 */
	public abstract String switchToNew(long timeOutInSeconds);

	/**
	 * returns handle of a new window that we have been waiting for specified
	 * time. new window should has defined title. We can specify title partially
	 * as a regular expression
	 */
	public abstract String switchToNew(long timeOutInSeconds, String stringIdentifier);

	/**
	 * returns handle of a new window that we have been waiting for time that
	 * specified in configuration. new window should has defined title. We can
	 * specify title partially
	 * as a regular expression
	 */
	public abstract String switchToNew(String stringIdentifier);
}
