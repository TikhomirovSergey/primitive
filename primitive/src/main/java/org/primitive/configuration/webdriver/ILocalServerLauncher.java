package org.primitive.configuration.webdriver;

import org.primitive.configuration.interfaces.IConfigurable;

/**
 * Launches remote server locally
 *
 */
interface ILocalServerLauncher extends IConfigurable {
	void launch() throws Exception;
	boolean isLaunched();
	int getPort();
	void setPort(int port);
	void stop();
}
