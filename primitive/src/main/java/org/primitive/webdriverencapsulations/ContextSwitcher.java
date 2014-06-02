package org.primitive.webdriverencapsulations;

import io.appium.java_client.NoSuchContextException;

import java.util.Set;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.bydefault.ContextTool;

final class ContextSwitcher implements ContextAware {
	private final SingleWindow window;
	private final ContextTool  contextTool;
	
	ContextSwitcher(SingleWindow window) {
		this.window = window;
		contextTool = ComponentFactory.getComponent(ContextTool.class, window
				.getDriverEncapsulation().getWrappedDriver());
	}

	@Override
	public WebDriver context(String name) {
		try{
			window.switchToMe();
			WebDriver result = contextTool.context(name);
			Log.message("Current context is " + name);
			return result;
		}
		catch (NoSuchContextException| org.openqa.selenium.NoSuchContextException e){
			throw e;
		}
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
