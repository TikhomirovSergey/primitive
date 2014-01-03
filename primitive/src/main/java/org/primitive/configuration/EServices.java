package org.primitive.configuration;
import java.io.File;

import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.primitive.configuration.Configuration;
import org.primitive.configuration.Configuration.FileSystemProperty;

enum EServices {	
	CHROMESERVICE(Configuration.byDefault.getChromeDriverSettings(), ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,  "chromedriver.exe"), 
	IEXPLORERSERVICE(Configuration.byDefault.getIEDriverSettings() , InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY,"IEDriverServer.exe"), 
	PHANTOMJSSERVICE(Configuration.byDefault.getPhantomJSDriverSettings(), PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs.exe");
	
	private static final String defaultFolder = "";
	
	private FileSystemProperty filePaths;
	private final String defaultExeName;
	private final String propertyName;
	
	//system properties should be set
	void setSystemProperty()
	{
		String path = String.valueOf(filePaths.getFolder()) + "/" + 
							 String.valueOf(filePaths.getFile());
		if (System.getProperty(propertyName)==null)
        { 
			if (!(new File(path).exists()))
			{
				path = defaultFolder + defaultExeName;
			}
			System.setProperty(propertyName, path);
        }
	}
	
	private EServices(FileSystemProperty filePaths, String propertyName, String defaultExeName)
	{
		this.filePaths      = filePaths; 
		this.defaultExeName = defaultExeName;
		this.propertyName   = propertyName;
	}

}
