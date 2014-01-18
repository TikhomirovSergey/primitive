package org.primitive.webdriverencapsulations;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.commonhelpers.WindowsTimeOuts;
import org.primitive.configuration.interfaces.IConfigurable;

class WindowTimeOuts implements IConfigurable {

	final long defaultTime = 1; // default time we waiting for
											// anything
	final long defaultTimeForNewWindow = 30; // we will wait
	// appearance of a new browser window for 30 seconds by default
	private WindowsTimeOuts timeOuts;

	WindowTimeOuts(Configuration config) {
		resetAccordingTo(config);
	}

	Long getTimeOut(Long possibleTimeOut, long defaultValue) {
		if (possibleTimeOut == null) {
			return defaultValue;
		} else {
			return possibleTimeOut;
		}
	}

	WindowsTimeOuts getTimeOuts() {
		return timeOuts;
	}

	public synchronized void resetAccordingTo(Configuration config) {
		timeOuts = config.getBrowserWindowsTimeOuts();
	}

}
