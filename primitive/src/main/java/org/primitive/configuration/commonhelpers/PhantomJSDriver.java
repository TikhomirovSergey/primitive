package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.Configuration;

public class PhantomJSDriver extends LocalWebDriverServiceSettings {
	private static final String phantomJSDriverGroup = "PhantomJSDriver";

	public PhantomJSDriver(Configuration configuration) {
		super(configuration, phantomJSDriverGroup);
	}

}
