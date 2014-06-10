package org.primitive.webdriverencapsulations.eventlisteners;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.interfaces.IConfigurable;

public abstract class DefaultHandleListener implements IHandletListener,
		IConfigurable {

	boolean toDoScreenShotsOfNewWindows = true;

	@Override
	public void resetAccordingTo(Configuration config) {
		Boolean toDoScreenshots = config.getScreenShots().getToDoScreenShotsOfNewHandles();
		if (toDoScreenshots!=null)
		{
			toDoScreenShotsOfNewWindows = toDoScreenshots;
		}
	}

}
