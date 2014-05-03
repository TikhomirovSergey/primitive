package org.primitive.configuration.webdriver;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

/**
 * 
 * Launches {@link RemoteWebDriver} server locally 
 *
 */
class RemoteSeleniumServerLauncer implements ILocalServerLauncher {

	private SeleniumServer server;
	private final RemoteControlConfiguration rcc;
	final boolean slowResources = false;
	
	public RemoteSeleniumServerLauncer() {
		int port = RemoteControlConfiguration.DEFAULT_PORT;
		rcc = new RemoteControlConfiguration();
		rcc.setPort(port);
	}


	@Override
	public boolean isLaunched() {
		if (server == null){
			return false;
		}
		return server.getServer().isStarted();
	}

	@Override
	public int getPort() {
		return rcc.getPort();
	}

	@Override
	public void setPort(int port) {
		rcc.setPort(port);
	}

	@Override
	public void stop() {
		if (server == null){
			return;
		}
		server.stop();
	}


	@Override
	public void launch() throws Exception {
		try {
			server = new SeleniumServer(slowResources, rcc);
		} catch (Exception e) {
			throw new WebDriverException(
					"Cann't start server on localhost!", e);
		}	
	}

}
