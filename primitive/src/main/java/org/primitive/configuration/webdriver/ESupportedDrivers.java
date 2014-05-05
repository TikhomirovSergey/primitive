package org.primitive.configuration.webdriver;

import io.selendroid.SelendroidDriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverservices.AndroidServerLauncher;
import org.primitive.webdriverservices.EServices;
import org.primitive.webdriverservices.RemoteSeleniumServerLauncer;
import org.primitive.webdriverservices.interfaces.ILocalServerLauncher;

import com.opera.core.systems.OperaDriver;


public enum ESupportedDrivers {
	FIREFOX(DesiredCapabilities.firefox(), FirefoxDriver.class, null, null),
	CHROME(DesiredCapabilities.chrome(), ChromeDriver.class, EServices.CHROMESERVICE, null), 
	INTERNETEXPLORER(DesiredCapabilities.internetExplorer(), InternetExplorerDriver.class, EServices.IEXPLORERSERVICE, null), 
	SAFARI(DesiredCapabilities.safari(), SafariDriver.class, null, null), 
	OPERA(DesiredCapabilities.opera(), OperaDriver.class, null, null),
	HTMLUNIT(DesiredCapabilities.htmlUnitWithJs(), HtmlUnitDriver.class, null, null), 
	PHANTOMJS(DesiredCapabilities.phantomjs(), PhantomJSDriver.class, EServices.PHANTOMJSSERVICE, null),
	REMOTE(DesiredCapabilities.firefox(), RemoteWebDriver.class, null, new RemoteSeleniumServerLauncer()){
		@Override
		public void setSystemProperty(Configuration configInstance, Capabilities capabilities) {
			String brofserName = capabilities.getBrowserName();

			if (DesiredCapabilities.chrome().getBrowserName().equals(brofserName)) {
				CHROME.setSystemProperty(configInstance);
			}
			if (DesiredCapabilities.internetExplorer().getBrowserName().equals(brofserName)) {
				INTERNETEXPLORER.setSystemProperty(configInstance);
			}
			if (DesiredCapabilities.phantomjs().getBrowserName().equals(brofserName)) {
				PHANTOMJS.setSystemProperty(configInstance);
			}
		}	
	},
	ANDROID(DesiredCapabilities.android(), SelendroidDriver.class, null, new AndroidServerLauncher()){
		@Override
		public synchronized void launchRemoteServerLocallyIfWasDefined(Configuration configuration){
			if (serverLauncher.isLaunched()){
				serverLauncher.stop();
			}
			try {
				serverLauncher.resetAccordingTo(configuration);
				serverLauncher.launch();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	};
	
	private Capabilities capabilities;
	private Class<? extends WebDriver> driverClazz;
	private EServices service;
	final ILocalServerLauncher serverLauncher;

	private ESupportedDrivers(Capabilities capabilities,
			Class<? extends WebDriver> driverClazz, EServices sevice, ILocalServerLauncher serverLauncher) {
		this.capabilities = capabilities;
		this.driverClazz = driverClazz;
		this.service = sevice;
		this.serverLauncher = serverLauncher;
	}

	public static ESupportedDrivers parse(String original) {
		String parcingStr = original.toUpperCase().trim();

		ESupportedDrivers[] values = ESupportedDrivers.values();
		for (ESupportedDrivers enumElem : values) {
			if (parcingStr.equals(enumElem.toString())) {
				return enumElem;
			}
		}
		throw new IllegalArgumentException("Webdriver with specified name "
				+ original + " is not supported");
	}

	public Capabilities getDefaultCapabilities() {
		return capabilities;
	}

	public Class<? extends WebDriver> getUsingWebDriverClass() {
		return driverClazz;
	}

	private void setSystemProperty(Configuration configInstance) {
		if (service != null) {
			this.service.setSystemProperty(configInstance);
		}
	}
	
	/**
	 * It is useful for {@link RemoteWebDriver} instantiation. Local services depend
	 * on capabilities 
	 */
	public void setSystemProperty(Configuration configInstance, Capabilities ignored) {
		setSystemProperty(configInstance);
	}
	
	public synchronized void launchRemoteServerLocallyIfWasDefined(Configuration configuration){
		if (serverLauncher==null){
			return;
		}
		if (serverLauncher.isLaunched()){
			return;
		}
		try {
			serverLauncher.resetAccordingTo(configuration);
			serverLauncher.launch();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
