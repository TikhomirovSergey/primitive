package org.primitive.webdriverencapsulations.inheritors;

import org.primitive.configuration.Configuration;
import org.primitive.configuration.ESupportedDrivers;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.opera.core.systems.OperaProfile;
import com.opera.core.systems.OperaSettings;



public class OperaDriverEncapsulation extends WebDriverEncapsulation {
	private void constructBody(OperaProfile profile)
	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaProfile.class}, new Object[] {profile}); 
	}
	
	private void constructBody(OperaSettings settings)
	{
		createWebDriver(ESupportedDrivers.OPERA.getUsingWebDriverClass(), new Class[] {OperaSettings.class}, new Object[] {settings});
	}	
	
	public OperaDriverEncapsulation(OperaProfile profile)
	{
		constructBody(profile);

	}
	
	public OperaDriverEncapsulation(Configuration configuration, OperaProfile profile)
	{
		this.configuration = configuration;
		constructBody(profile);

	}	
	
	public OperaDriverEncapsulation(OperaSettings settings)
	{
		constructBody(settings);
	}	
	
	public OperaDriverEncapsulation(Configuration configuration, OperaSettings settings)
	{
		this.configuration = configuration;
		constructBody(settings);
	}

}

