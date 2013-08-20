package org.primitive.exceptions;

import org.openqa.selenium.WebDriverException;

/**
 * @author s.tihomirov
 *
 */
public class InteractionError extends WebDriverException {

	private static final long serialVersionUID = 3231447290816193635L;

	public InteractionError(String msg, Throwable reason) {
		super(msg, reason);
	}

}
