package org.primitive.webdriverencapsulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.primitive.interfaces.IDestroyable;
import org.primitive.logging.Photographer;
import org.primitive.webdriverencapsulations.components.overriden.Awaiting;
import org.primitive.webdriverencapsulations.interfaces.IHasHandle;

public abstract class AbstractManager implements IDestroyable {

	final Awaiting awaiting;
	final WebDriverEncapsulation driverEncapsulation;
	boolean isAlive = true;
	private final HandleReceptionist handleReceptionist = new HandleReceptionist();
	private final static List<AbstractManager> managerList = Collections
	.synchronizedList(new ArrayList<AbstractManager>());
	
	final static long defaultTime = 1; // default time we waiting for anything
    final static long defaultTimeForNew = 30; // we will wait
    // appearance of a new handle for 30 seconds by default

	static long getTimeOut(Long possibleTimeOut, long defaultValue) {
		if (possibleTimeOut == null) {
			return defaultValue;
		} else {
			return possibleTimeOut;
		}
	}
    
	AbstractManager(WebDriverEncapsulation initialDriverEncapsulation) {
		driverEncapsulation = initialDriverEncapsulation;
		awaiting = driverEncapsulation.getAwaiting();
		managerList.add(this);
	}

	@Override
	public void destroy(){
		managerList.remove(this);
		isAlive = false;

		List<IHasHandle> toBeDestroyed = handleReceptionist.getInstantiated();
		for (IHasHandle hasHandle : toBeDestroyed) {
			((IDestroyable) hasHandle).destroy();
		}
	}
	
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

	synchronized void switchTo(String Handle){
		changeActive(Handle);
	}
	public abstract String getHandleByInex(int index);
	public abstract  Set<String> getHandles();
	public abstract String switchToNew();
	public abstract String switchToNew(long timeOutInSeconds);
	public abstract String switchToNew(long timeOutInSeconds, String stringIdentifier);
	public abstract String switchToNew(String stringIdentifier);

	HandleReceptionist getHandleReceptionist(){
		return handleReceptionist;
	}
}
