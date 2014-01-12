package org.primitive.webdriverencapsulations.eventlisteners;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.interfaces.IConfigurable;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;

/**
* @author s.tihomirov
*Implementation of IWindowListener by default
*/
public class DefaultWindowListener implements IWindowListener, IConfigurable{

	private boolean toDoScreenShotsOfNewWindows = true;
	
	public DefaultWindowListener() {
		super();
	}
	
	private void postWindowTitleAndUrl(IExtendedWindow window)
	{
		Log.message("Title is " + window.getTitle() + " URL is " + window.getCurrentUrl());
	}

	@Override
	public void whenNewWindewIsAppeared(IExtendedWindow window) {
		if (toDoScreenShotsOfNewWindows)
		{
			window.takeAPictureOfAnInfo("The new window");
		}
		
	}

	@Override
	public void beforeWindowIsClosed(IExtendedWindow window) {
		Log.message("Attempt to close browser window...");
		postWindowTitleAndUrl(window);
	}

	@Override
	public void whenWindowIsClosed(IExtendedWindow window) {
		Log.message("Not any problem has occurred when browser window was closed...");					
	}

	@Override
	public void beforeWindowIsSwitchedOn(IExtendedWindow window) {
		Log.debug("Attempt to switch browser window on by handle "+ window.getWindowHandle());
	}

	@Override
	public void whenWindowIsSwitchedOn(IExtendedWindow window) {
		postWindowTitleAndUrl(window);			
	}

	@Override
	public void beforeWindowIsMaximized(IExtendedWindow window) {
		Log.message("Attempt to maximize browser window");	
		postWindowTitleAndUrl(window);
	}

	@Override
	public void whenWindowIsMaximized(IExtendedWindow window) {
		Log.message("Browser window has been maximized");			
	}

	@Override
	public void beforeWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Attempt to refresh browser window");	
		postWindowTitleAndUrl(window);
	}

	@Override
	public void whenWindowIsRefreshed(IExtendedWindow window) {
		Log.message("Current browser window has been refreshed");
		postWindowTitleAndUrl(window);		
	}

	@Override
	public void beforeWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Attempt to change window position. X " + Integer.toString(point.getX()) + 
				" Y " + Integer.toString(point.getY()));	
		postWindowTitleAndUrl(window);
	}

	@Override
	public void whenWindowIsMoved(IExtendedWindow window, Point point) {
		Log.message("Window position has been changed to X " + Integer.toString(point.getX()) + 
				" Y " + Integer.toString(point.getY()));
		postWindowTitleAndUrl(window);
	}

	@Override
	public void beforeWindowIsResized(IExtendedWindow window, Dimension dimension) {
		Log.message("Attempt to change window size. New height is " + Integer.toString(dimension.getHeight()) + 
				" new width is " + Integer.toString(dimension.getWidth()));
		postWindowTitleAndUrl(window);
	}

	@Override
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension) {
		Log.message("Window size has been changed! New height is " + Integer.toString(dimension.getHeight()) + 
				" new width is " + Integer.toString(dimension.getWidth()));
		postWindowTitleAndUrl(window);			
	}

	@Override
	public void resetAccordingTo(Configuration config) {
		Boolean toDoScreenshots = config.getScreenShots().getToDoScreenShotsOfNewWindows();
		if (toDoScreenshots!=null)
		{
			toDoScreenShotsOfNewWindows = toDoScreenshots;
		}
	}

}
