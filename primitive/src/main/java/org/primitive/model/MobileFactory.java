package org.primitive.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.ContextManager;


public final class MobileFactory extends DefaultApplicationFactory {

	private MobileFactory() {
		super();
	}

	/**
	 * Common method that creates an instance of a mobile application
	 * using defined configuration
	 * It is important: URL to remote server and capabilities should be defined in configuration
	 */
	public static <T extends MobileAppliction> T getApplication(
			Class<T> appClass, Configuration configuration) {
		try {
			T result = getApplication(appClass,
					configuration.getCapabilities(), new URL(
							configuration.getWebDriverSettings()
									.getRemoteAddress()));
			result.getWebDriverEncapsulation().resetAccordingTo(configuration);
			return result;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Common method that creates an instance of a mobile application
	 * using default configuration
	 * It is important: URL to remote server and capabilities should be defined in default configuration
	 */
	public static <T extends MobileAppliction> T getApplication(
			Class<T> appClass) {
		return getApplication(appClass, Configuration.byDefault);
	}
	
	/**
	 * Common method that creates an instance of a mobile application
	 * using defined address of remote server. 
	 * It is important: Remote Appium server should be tuned
	 */
	public static <T extends MobileAppliction> T getApplication(Class<T> appClass, URL remoteAddress){
		return getApplication(ContextManager.class, appClass, ESupportedDrivers.MOBILE, remoteAddress);
	}
	
	/**
	 * Common method that creates an instance of a mobile application
	 * using defined address of remote server and capabilities. 
	 */
	public static <T extends MobileAppliction> T getApplication(Class<T> appClass, Capabilities capabilities, URL remoteAddress){
		return getApplication(ContextManager.class, appClass, ESupportedDrivers.MOBILE, capabilities, remoteAddress);
	}

}
