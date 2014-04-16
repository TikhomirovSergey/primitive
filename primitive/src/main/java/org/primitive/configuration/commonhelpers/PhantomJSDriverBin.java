package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.Configuration;

public class PhantomJSDriverBin extends LocalWebDriverServiceSettings {
	private static final String phantomJSDriverGroup = "PhantomJSDriver";

	public PhantomJSDriverBin(Configuration configuration) {
		super(configuration, phantomJSDriverGroup);
	}

}
