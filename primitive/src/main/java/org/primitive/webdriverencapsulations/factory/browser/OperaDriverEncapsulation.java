package org.primitive.webdriverencapsulations.factory.browser;

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

	public OperaDriverEncapsulation() {
		super(Configuration.byDefault);
		constructBodyInGeneral(operaDriver);
	}
	
	public OperaDriverEncapsulation(Configuration configuration) {
		super(configuration);
		constructBodyInGeneral(operaDriver);
	}
	
	public OperaDriverEncapsulation(Capabilities capabilities) {
		super(Configuration.byDefault);
		constructBodyInGeneral(operaDriver);
	}
	
	public OperaDriverEncapsulation(Configuration configuration, Capabilities capabilities) {
		super(configuration);
		constructBodyInGeneral(operaDriver, capabilities);
	}	
	
	private void constructBody(OperaProfile profile)
	{
		createWebDriver(operaDriver, new Class[] {OperaProfile.class}, new Object[] {profile}); 
	}
	
	private void constructBody(OperaSettings settings)
	{
		createWebDriver(operaDriver, new Class[] {OperaSettings.class}, new Object[] {settings});
	}	
	
	public OperaDriverEncapsulation(OperaProfile profile)
	{
		super(Configuration.byDefault);
		constructBody(profile);

	}
	
	public OperaDriverEncapsulation(Configuration configuration, OperaProfile profile)
	{
		super(configuration);
		constructBody(profile);

	}	
	
	public OperaDriverEncapsulation(OperaSettings settings)
	{
		super(Configuration.byDefault);
		constructBody(settings);
	}	
	
	public OperaDriverEncapsulation(Configuration configuration, OperaSettings settings)
	{
		super(configuration);
		constructBody(settings);
	}


	@Override
	@Deprecated
	protected void prepare(){
		Log.debug("There is nothing to prepare for " + operaDriver.getSimpleName());
	}

}

