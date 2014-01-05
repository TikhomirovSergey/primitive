package org.primitive.configuration.commonhelpers;

import org.primitive.configuration.AbstractConfigurationAccessHelper;
import org.primitive.configuration.Configuration;

public class ScreenShots extends AbstractConfigurationAccessHelper {

	private final String toDoScreenShotsOnElementHighLighting = "toDoScreenShotsOnElementHighLighting";
	//screenshot group
	private final String screenShotssGroup = "screenShots";

	public ScreenShots(Configuration configuration) {
		super(configuration);
	}

	public Boolean getToDoScreenShotsOnElementHighLighting()
	{
		return (Boolean) getSetting(toDoScreenShotsOnElementHighLighting);
	}
	
	@Override
	public Object getSetting(String name)
	{
		return  getSettingValue(screenShotssGroup, name);
	}

}
