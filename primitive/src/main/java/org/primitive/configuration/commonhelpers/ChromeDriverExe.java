package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.Configuration;

/**
 * @author s.tihomirov
 * settings of chromedriver.exe
 */
public class ChromeDriverExe extends LocalWebDriverServiceSettings {
	private static final String chromeDriverGroup = "ChromeDriver"; 
	
	public ChromeDriverExe(Configuration configuration) {
		super(configuration, chromeDriverGroup);
	}

}
