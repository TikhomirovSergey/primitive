/**
 * 
 */
package org.primitive.exceptions;

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

	public UnclosedBrowserWindowException(Exception e) {
		super(e);
	}
	
	public UnclosedBrowserWindowException(Throwable reason) {
		super(reason);
	}
}
