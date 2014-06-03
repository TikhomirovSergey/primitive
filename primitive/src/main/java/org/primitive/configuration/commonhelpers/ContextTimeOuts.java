package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

public class ContextTimeOuts extends AbstractConfigurationAccessHelper{

	public ContextTimeOuts(Configuration configuration) {
		super(configuration);
	}

	// screenshot group
	private final String contextGroup = "contexts";
	private final String contextPresenceTimeOut = "isContextPresentTimeOut";

	
	@Override
	public Object getSetting(String name) {
		return getSettingValue(contextGroup, name);
	}
	
	public Long getContextPresenceTimeOut(){
		return (Long) getSetting(contextPresenceTimeOut); 
	}

}
