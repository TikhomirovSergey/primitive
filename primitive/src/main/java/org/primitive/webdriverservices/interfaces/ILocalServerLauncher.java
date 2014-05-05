package org.primitive.webdriverservices.interfaces;

import java.net.URL;

import org.primitive.configuration.interfaces.IConfigurable;

/**
 * Launches remote server locally
 *
 */
public interface ILocalServerLauncher extends IConfigurable {
	final String defaultLocalHost = "http://localhost:4444/wd/hub";
	final int defaulPort = 4444;
	public void launch() throws Exception;
	public boolean isLaunched();
	public int getPort();
	public void setPort(int port);
	public void stop();
	public URL getLocalHost();
}
