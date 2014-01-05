package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.Configuration;

/**
 * @author s.tihomirov
 * settings of IEDriver.exe
 */
public class IEDriverExe extends LocalWebDriverServiceSettings {
	private static final String ieDriverGroup = "IEDriver";

	public IEDriverExe(Configuration configuration) {
		super(configuration, ieDriverGroup);
	}

}
