package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.interfaces.IHasPathToFile;

/**
 * @author s.tihomirov getters for local webdriver service configuration data
 */
class LocalWebDriverServiceSettings extends AbstractConfigurationAccessHelper
		implements IHasPathToFile {

	protected final String localWebdriverServiceGroup;
	private final String fileSettingName = "file";
	// spicified settings for *Driver.exe
	private final String folderSettingName = "folder";

	public LocalWebDriverServiceSettings(Configuration configuration,
			String groupName) {
		super(configuration);
		localWebdriverServiceGroup = groupName;
	}

	@Override
	public String getFolder() {
		return (String) getSetting(folderSettingName);
	}

	@Override
	public String getFile() {
		return (String) getSetting(fileSettingName);
	}

	@Override
	public Object getSetting(String name) {
		return getSettingValue(localWebdriverServiceGroup, name);
	}

}
