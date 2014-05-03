package org.primitive.configuration.webdriver;

/**
 * Launches remote server locally
 *
 */
interface ILocalServerLauncher {
	void launch() throws Exception;
	boolean isLaunched();
	int getPort();
	void setPort(int port);
	void stop();
}
