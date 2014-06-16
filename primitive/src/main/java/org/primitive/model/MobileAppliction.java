package org.primitive.model;

import org.primitive.webdriverencapsulations.ContextManager;
import org.primitive.webdriverencapsulations.SingleContext;

/**
 * Representation of a mobile application
 */
public abstract class MobileAppliction extends Application {

	public MobileAppliction(SingleContext context) {
		super(context);
	}
	
	public ContextManager getContextManager(){
		return (ContextManager) manager;
	}

}
