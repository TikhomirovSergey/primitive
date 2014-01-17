package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;

/**
 * @author s.tihomirov Parameters of a webdriver that will be created
 */
public class WebDriverSettings extends AbstractConfigurationAccessHelper {

	private final String remoteAddress = "remoteAdress";
	private final String webDriverName = "driverName";
	private final String webDriverGroup = "webdriver";

	public WebDriverSettings(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(webDriverGroup, name);
	}

	public String getRemoteAddress() {
		return (String) getSetting(remoteAddress);
	}

	public ESupportedDrivers getSupoortedWebDriver() {
		String name = (String) getSetting(webDriverName);
		if (name != null) {
			return ESupportedDrivers.parse(name);
		} else {
			return null;
		}
	}

}
