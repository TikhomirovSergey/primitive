package org.primitive.webdriverencapsulations;

import java.util.Set;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.primitive.configuration.commonhelpers.ContextTimeOuts;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.bydefault.ContextTool;
import org.primitive.webdriverencapsulations.components.overriden.Awaiting;
import org.primitive.webdriverencapsulations.components.overriden.FluentContextConditions;

public final class ContextSwitcher implements ContextAware {
	private final SingleWindow window;
	private final ContextTool  contextTool;
	private final FluentContextConditions fluentConditions;	
	private final Long defaultTimeOut = (long) 20;
	private final Awaiting awaiting;
	
	ContextSwitcher(SingleWindow window) {
		this.window = window;
		contextTool = ComponentFactory.getComponent(ContextTool.class, window
				.getDriverEncapsulation().getWrappedDriver());
		fluentConditions = new FluentContextConditions(this.window
				.getDriverEncapsulation().getWrappedDriver());
		awaiting = this.window
				.getDriverEncapsulation().getAwaiting();
	}

	
	private WebDriver getContextIfPresents(String name, long timeOut){
		window.switchToMe();
		awaiting.awaitCondition(timeOut, fluentConditions.isContextPresent(name));
		WebDriver result = contextTool.context(name);
		Log.message("Current context is " + name);
		return result;
	}
	
	@Override
	public WebDriver context(String name) {
		ContextTimeOuts contextTimeOuts = window.getDriverEncapsulation().configuration
				.getContextTimeOuts();
		Long timeOut = contextTimeOuts.getContextPresenceTimeOut();
		if (timeOut == null){
			timeOut = defaultTimeOut;
		}
		return getContextIfPresents(name, timeOut);
	}
	
	public WebDriver context(String name, long timeOut){
		return getContextIfPresents(name, timeOut);
	}

	@Override
	public Set<String> getContextHandles() {
		window.switchToMe();
		return contextTool.getContextHandles();
	}

	@Override
	public String getContext() {
		window.switchToMe();
		return contextTool.getContext();
	}

}
