package org.primitive.webdriverservices;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverservices.interfaces.ILocalServerLauncher;

/**
 * 
 * Launches {@link RemoteWebDriver} server locally 
 *
 */
public class RemoteSeleniumServerLauncer implements ILocalServerLauncher {

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
		PortBinder.releasePort(this);
	}


	@Override
	public void launch() throws Exception {
		if (PortBinder.isBusy(rcc.getPort())){
			rcc.setPort(PortBinder.getMaxBusyPort()+1);
		}
		
		try {
			server = new SeleniumServer(slowResources, rcc);
			server.start();
			PortBinder.setBusyPort(this, rcc.getPort());
		} catch (Exception e) {
			throw new WebDriverException(
					"Cann't start server on localhost!", e);
		}	
	}


	@Deprecated
	@Override
	public void resetAccordingTo(Configuration config) {
		//Does nothing		
	}


	@Override
	public URL getLocalHost() {
		URL localHost = null;
		try {
			localHost = new URL(defaultLocalHost.replace(
					Integer.toString(defaulPort),
					Integer.toString(rcc.getPort())));
		} catch (MalformedURLException ignored) {
		}
		return localHost;
	}

}