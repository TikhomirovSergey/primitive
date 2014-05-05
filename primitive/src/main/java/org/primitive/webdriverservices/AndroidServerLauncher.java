package org.primitive.webdriverservices;

import java.net.MalformedURLException;
import java.net.URL;

import io.selendroid.SelendroidConfiguration;
import io.selendroid.SelendroidDriver;
import io.selendroid.SelendroidLauncher;

import org.primitive.configuration.Configuration;
import org.primitive.webdriverservices.interfaces.ILocalServerLauncher;

/**
 * 
 * Launches {@link SelendroidDriver} server locally 
 *
 */
public class AndroidServerLauncher implements ILocalServerLauncher {
	private SelendroidConfiguration configuration = new SelendroidConfiguration();
	private SelendroidLauncher selendroidServer = new SelendroidLauncher(configuration);
	private boolean isLaunched = false;
	
	@Override
	public void resetAccordingTo(Configuration config) {
		configuration = config.getAndroidSettings().getSelendroidConfiguration();
	}

	@Override
	public void launch() throws Exception {
		if (PortBinder.isBusy(configuration.getPort())){
			configuration.setPort(PortBinder.getMaxBusyPort()+1);
		}
		
		selendroidServer = new SelendroidLauncher(configuration);
		selendroidServer.lauchSelendroid();
		isLaunched = true;
		PortBinder.setBusyPort(this, configuration.getPort());
	}

	@Override
	public boolean isLaunched() {
		return isLaunched;
	}

	@Override
	public int getPort() {
		return configuration.getPort();
	}

	@Override
	public void setPort(int port) {
		configuration.setPort(port);
	}

	@Override
	public void stop() {
		if (isLaunched){
			selendroidServer.stopSelendroid();
			isLaunched = false;
			PortBinder.releasePort(this);
		}

	}

	@Override
	public URL getLocalHost() {
		URL localHost = null;
		try {
			localHost = new URL(defaultLocalHost.replace(
					Integer.toString(defaulPort),
					Integer.toString(configuration.getPort())));
		} catch (MalformedURLException ignored) {
		}
		return localHost;
	}

}
