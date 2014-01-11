/**
 * 
 */
package org.primitive.webdriverencapsulations;

import org.openqa.selenium.WebDriverException;

/**
 * @author s.tihomirov
 *
 */
public class UnclosedBrowserWindowException extends WebDriverException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -323127995328722233L;

	public UnclosedBrowserWindowException(String message, Exception e) {
		super(message, e);
	}
}
