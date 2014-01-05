package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

public class WebElementVisibility extends AbstractConfigurationAccessHelper {

	private final String visibilityTimeOutSetting = "visibilityTimeOutSec";
	private final String webElementVisibilityGroup   = "webElementVisibilityTimeOut";

	public WebElementVisibility(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Object getSetting(String name)
	{
		return  getSettingValue(webElementVisibilityGroup, name);
	}

	public Long getVisibilityTimeOutSec()
	{
		return (Long) getSetting(visibilityTimeOutSetting);			
	}

}
