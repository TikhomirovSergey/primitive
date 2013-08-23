package org.primitive.webdriverencapsulations.factory;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

import com.opera.core.systems.OperaDriver;
import com.opera.core.systems.OperaProfile;
import com.opera.core.systems.OperaSettings;



public class OperaDriverEncapsulation extends WebDriverEncapsulation {
	private static final Class<? extends WebDriver> operaDriver = OperaDriver.class;

	public OperaDriverEncapsulation(String openingURL) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, operaDriver);
	}
	
	public OperaDriverEncapsulation(Configuration configuration, String openingURL) {
		super(configuration);
		constructBodyInGeneral(openingURL, operaDriver);
	}
	
	public OperaDriverEncapsulation(String openingURL, Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(openingURL, operaDriver);
	}
	
	public OperaDriverEncapsulation(Configuration configuration, String openingURL, Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(openingURL, operaDriver, capabilities);
	}	
	
	private void constructBody(String openingURL, OperaProfile profile)
	{
		createWebDriver(openingURL, operaDriver, new Class[] {OperaProfile.class}, new Object[] {profile}); 
	}
	
	private void constructBody(String openingURL, OperaSettings settings)
	{
		createWebDriver(openingURL, operaDriver, new Class[] {OperaSettings.class}, new Object[] {settings});
	}	
	
	public OperaDriverEncapsulation(String URL, OperaProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(URL, profile);

	}
	
	public OperaDriverEncapsulation(Configuration configuration, String URL, OperaProfile profile)
	{
		super(configuration);
		constructBody(URL, profile);

	}	
	
	public OperaDriverEncapsulation(String URL, OperaSettings settings)
	{
		super(Configuration.byDefault);
		constructBody(URL, settings);
	}	
	
	public OperaDriverEncapsulation(Configuration configuration, String URL, OperaSettings settings)
	{
		super(configuration);
		constructBody(URL, settings);
	}


	@Override
	@Deprecated
	protected void prepare(){
		Log.debug("There is nothing to prepare for " + operaDriver.getSimpleName());
	}

}

