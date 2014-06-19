package org.primitive.webdriverencapsulations.eventlisteners;

import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.interfaces.IHasActivity;
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
		String activity = String.valueOf(((IHasActivity) handle).currentActivity());
		String message = "A new context "
				+ handle.getHandle() + ". Activity is " + activity;
		if (toDoScreenShotsOfNewWindows) {
			((ITakesPictureOfItSelf) handle)
					.takeAPictureOfAnInfo(message);
			return;
		}
		Log.message(message);
	}

	@Override
	public void whenIsSwitchedOn(IHasHandle handle) {
		String activity = String.valueOf(((IHasActivity) handle).currentActivity());
		if (toDoScreenShotsOfNewWindows)
		{
			((ITakesPictureOfItSelf) handle).takeAPictureOfAnInfo("The new context");
		}
		Log.message("Current context is "
				+ handle.getHandle() + ". Activity is " + activity);
	}

}
