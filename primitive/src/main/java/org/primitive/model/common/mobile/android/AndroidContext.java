package org.primitive.model.common.mobile.android;

import org.openqa.selenium.WebElement;
import org.primitive.model.common.FunctionalPart;
import org.primitive.model.common.mobile.Context;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.components.bydefault.AppStringGetter;
import org.primitive.webdriverencapsulations.components.bydefault.MetastateKeyEventSender;

public abstract class AndroidContext extends Context {

	protected final MetastateKeyEventSender metastateKeyEventSender;
	protected final AppStringGetter appStringGetter;
	
	protected AndroidContext(SingleContext context) {
		super(context);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);
	}

	protected AndroidContext(FunctionalPart parent) {
		super(parent);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);
	}

	protected AndroidContext(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

	protected AndroidContext(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		metastateKeyEventSender = getComponent(MetastateKeyEventSender.class);
		appStringGetter         = getComponent(AppStringGetter.class);		
	}

}
