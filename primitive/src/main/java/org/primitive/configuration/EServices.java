package org.primitive.configuration;

import java.io.File;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.commonhelpers.ChromeDriverServerBin;
import org.primitive.configuration.commonhelpers.IEDriverServerBin;
import org.primitive.configuration.commonhelpers.PhantomJSDriverBin;
import org.primitive.configuration.interfaces.IHasPathToFile;

enum EServices {
	CHROMESERVICE(ChromeDriverServerBin.class,
			ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, "chromedriver.exe"), 
	IEXPLORERSERVICE(IEDriverServerBin.class,
			InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,
			"IEDriverServer.exe"), 
	PHANTOMJSSERVICE(PhantomJSDriverBin.class,
			PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
			"phantomjs.exe");

	private static final String defaultFolder = "";
	private final String defaultExeName;
	private final String propertyName;
	private final Class<? extends AbstractConfigurationAccessHelper> clazzOfSettings;

	// system properties should be set
	void setSystemProperty(Configuration configInstance) {
		IHasPathToFile pathConfig = (IHasPathToFile) configInstance.getSection(clazzOfSettings);
		String path = String.valueOf(pathConfig.getFolder()) + "/"
				+ String.valueOf(pathConfig.getFile());
		if (System.getProperty(propertyName) == null) {
			if (!(new File(path).exists())) {
				path = defaultFolder + defaultExeName;
			}
			System.setProperty(propertyName, path);
		}
	}

	private EServices(Class<? extends AbstractConfigurationAccessHelper> classOfSetting, String propertyName,
			String defaultExeName) {
		this.clazzOfSettings = classOfSetting;
		this.defaultExeName = defaultExeName;
		this.propertyName = propertyName;
	}

}
