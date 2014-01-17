package org.primitive.configuration.commonhelpers;

import java.util.logging.Level;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

/**
 * @author s.tihomirov Settings of logger. I use java.utils.logging
 */
public class Logging extends AbstractConfigurationAccessHelper {

	private final String levelSetting = "Level";
	// Logging group
	private final String loggingGroup = "Log";

	public Logging(Configuration configuration) {
		super(configuration);
	}

	public Level getLevel() {
		String levelName = (String) getSetting(levelSetting);
		if (levelName != null) {
			return Level.parse(levelName.toUpperCase());
		} else {
			return null;
		}
	}

	public Object getSetting(String name) {
		return getSettingValue(loggingGroup, name);
	}

}
