package org.primitive.exceptions;

import org.openqa.selenium.WebDriverException;

/**
 * @author s.tihomirov
 *
 */
public class UnswitchableBrowserWindowException extends WebDriverException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8621747682116934023L;

	/**
	 * 
	 */
	public UnswitchableBrowserWindowException(Exception e) {
		super(e);
	}

}
