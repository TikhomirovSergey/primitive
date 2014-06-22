package org.primitive.model.common.mobile.android;

import org.primitive.model.common.mobile.MobileAppliction;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.components.bydefault.AppStringGetter;
import org.primitive.webdriverencapsulations.interfaces.IGetsAppStrings;

public abstract class AndroidApplication extends MobileAppliction implements IGetsAppStrings{

	private final AppStringGetter appStringGetter;
	
	protected AndroidApplication(SingleContext context) {
		super(context);
		appStringGetter = getComponent(AppStringGetter.class);
	}

	@Override
	public String getAppStrings() {
		return appStringGetter.getAppStrings();
	}

	@Override
	public String getAppStrings(String language) {
		return appStringGetter.getAppStrings(language);
	}

}
