package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

public class ContextTimeOuts extends AbstractConfigurationAccessHelper{

	public ContextTimeOuts(Configuration configuration) {
		super(configuration);
	}

	// screenshot group
	private final String contextGroup = "Contexts";
	private final String contextCountTimeOutSetting = "contextCountTimeOutSec";
	private final String newContextTimeOutSetting   = "newContextTimeOutSec";
	private final String isContextPresentTimeOutSetting  = "isContextPresentTimeOut";

	
	@Override
	public Object getSetting(String name) {
		return getSettingValue(contextGroup, name);
	}
	
	public Long getContextCountTimeOutSec(){
		return (Long) getSetting(contextCountTimeOutSetting); 
	}

	public Long getNewContextTimeOutSec() {
		return (Long) getSetting(newContextTimeOutSetting);
	}
	
	public Long getIsContextPresentTimeOut() {
		return (Long) getSetting(isContextPresentTimeOutSetting);
	}
}
