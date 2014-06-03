package org.primitive.webdriverencapsulations;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.NoSuchContextException;
import org.primitive.webdriverencapsulations.interfaces.ISwitchesToItself;

/**
 * Single context for a hybrid app with lazy instantiation
 */
public class SingleContext implements ISwitchesToItself {
    private final String context;
    private ContextAware contextAware;
    
	public SingleContext(String context){
		this.context = context;
	}
	
	public void instantiateContextAware(ContextAware contextAware){
		this.contextAware = contextAware;
	}
	
	private void checkContextAwareInstantiation() {
		if (contextAware == null) {
			throw new RuntimeException(
					"Context aware wasn't instantiated. Use method "
					+ "instantiateContextAware(ContextAware contextAware) for this purpose");
		}
	}
	
	public boolean exists(){
		checkContextAwareInstantiation();
		return contextAware.getContextHandles().contains(context);
	}
	
	@Override
	public void switchToMe() {
		checkContextAwareInstantiation();
		if (!exists()){
			throw new NoSuchContextException("There is no context " + context + "was found!");
		}
		contextAware.context(context);
	}
	
	@Override
	public String toString(){
		return context;
	}

}
