package org.primitive.configuration.commonhelpers;

import io.selendroid.SelendroidConfiguration;

import java.lang.reflect.Method;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

/**
 * Settings of Android. See {@link SelendroidConfiguration}
 */
public class AndroidSettings extends AbstractConfigurationAccessHelper {

	private static final String androidGroup = "Android";
	private final SelendroidConfiguration selendroidConfiguration = new SelendroidConfiguration();

	public AndroidSettings(Configuration configuration) {
		super(configuration);
		Method[] methods = SelendroidConfiguration.class.getMethods();
		// settings correspond correspond to the methods of
		// io.selendroid.SelendroidConfiguration which set or add something
		for (Method m : methods) {
			String methName = m.getName();
			if (methName.startsWith("get")) {
				continue;
			}
			Object value = getSetting(methName);
			if (value == null) {
				continue;
			}
			try {
				m.invoke(selendroidConfiguration, new Object[] { value });
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(androidGroup, name);
	}

	public SelendroidConfiguration getSelendroidConfiguration() {
		return selendroidConfiguration;
	}

}
