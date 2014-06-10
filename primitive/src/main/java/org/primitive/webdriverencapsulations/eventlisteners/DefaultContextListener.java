package org.primitive.webdriverencapsulations.eventlisteners;

import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.interfaces.IHasHandle;
import org.primitive.webdriverencapsulations.interfaces.ITakesPictureOfItSelf;

public class DefaultContextListener extends DefaultHandleListener implements IContextListener {
	
	public DefaultContextListener(){
		super();
	}

	@Override
	public void beforeIsSwitchedOn(IHasHandle handle) {
		Log.debug("Attempt to switch to context "+ handle.getHandle());
	}

	@Override
	public void whenNewHandleIsAppeared(IHasHandle handle) {
		if (toDoScreenShotsOfNewWindows)
		{
			((ITakesPictureOfItSelf) handle).takeAPictureOfAnInfo("Current context is " + handle.getHandle());
			return;
		}
		Log.message("Current context is " + handle.getHandle());
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		if (toDoScreenShotsOfNewWindows)
		{
			((ITakesPictureOfItSelf) handle).takeAPictureOfAnInfo("The new context");
		}
		Log.message("A new context '" + handle.getHandle() + "' is here");
	}

}
