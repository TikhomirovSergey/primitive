package org.primitive.configuration.commonhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

/**
 * @author s.tihomirov
 * There are specified webdriver capabilities settings
 */
public class CapabilitySettings extends AbstractConfigurationAccessHelper
		implements HasCapabilities, Capabilities {

	//specified settings for capabilities
	private final String capabilityGroup = "DesiredCapabilities";
	private final DesiredCapabilities builtCapabilities = new DesiredCapabilities();
	private final String browserNameCapabity = "browserName";
	private final String versionNameCapabity  = "version";	
	private final String platformNameCapabity = "platform";

	public CapabilitySettings(Configuration configuration) {
		super(configuration);
		buildCapabilities();
	}

	@Override
	public Capabilities getCapabilities() {
		return builtCapabilities;
	}

	@Override
	public Object getSetting(String name) {
		return  getSettingValue(capabilityGroup, name);
	}

	@Override
	public Map<String, ?> asMap() {
		return builtCapabilities.asMap();
	}

	@Override
	public String getBrowserName() {
		return builtCapabilities.getBrowserName();
	}

	@Override
	public Object getCapability(String capabilityName) {
		return builtCapabilities.getCapability(capabilityName);
	}

	@Override
	public Platform getPlatform() {
		return builtCapabilities.getPlatform();
	}

	@Override
	public String getVersion() {
		return builtCapabilities.getVersion();
	}

	@Override
	public boolean is(String capabilityName) {
		return builtCapabilities.is(capabilityName);
	}

	@Override
	public boolean isJavascriptEnabled() {
		return builtCapabilities.isJavascriptEnabled();
	}

	private void buildCapabilities()
	{
		HashMap<String,Object> capabilities = getGroup(capabilityGroup);
		if (capabilities!=null)
		{			
			List<String> capabilityStrings = new ArrayList<String>(capabilities.keySet());
			
			for (String capabilityStr: capabilityStrings)
			{	
				if (capabilities.get(capabilityStr)!=null)
				{	
					//set browser name
					if (capabilityStr.equals(browserNameCapabity))
					{
						builtCapabilities.setBrowserName((String) capabilities.get(capabilityStr));
					} //set version
					else if (capabilityStr.equals(versionNameCapabity)) 
					{
						builtCapabilities.setVersion((String) capabilities.get(capabilityStr));
					} //set platform
					else if (capabilityStr.equals(platformNameCapabity)) 
					{
						builtCapabilities.setPlatform(Platform.valueOf((String) capabilities.get(capabilityStr)));
					}
					else
					{	
						builtCapabilities.setCapability(capabilityStr, capabilities.get(capabilityStr));
					}
				}
			}
		}
	}

}
