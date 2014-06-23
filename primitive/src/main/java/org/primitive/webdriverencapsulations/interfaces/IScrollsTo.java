package org.primitive.webdriverencapsulations.interfaces;

import io.appium.java_client.MobileElement;

public interface IScrollsTo {
	public MobileElement scrollTo(String text);
	public MobileElement scrollToExact(String text);
}
