package org.primitive.webdriverencapsulations.inheritors;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.webdriver.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.opera.core.systems.OperaProfile;
import com.opera.core.systems.OperaSettings;



public final class OperaDriverEncapsulation extends WebDriverEncapsulation {
	
	public OperaDriverEncapsulation(OperaProfile profile)	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaProfile.class}, new Object[] {profile}); 
	}
	
	public OperaDriverEncapsulation(OperaProfile profile, Configuration config)	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaProfile.class}, new Object[] {profile}); 
		resetAccordingTo(config);
	}
	
	public OperaDriverEncapsulation(OperaSettings settings)	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaSettings.class}, new Object[] {settings});
	}
	
	public OperaDriverEncapsulation(OperaSettings settings, Configuration config)	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaSettings.class}, new Object[] {settings});
		resetAccordingTo(config);
	}

}

