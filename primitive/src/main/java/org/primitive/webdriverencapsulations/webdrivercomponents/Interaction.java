package org.primitive.webdriverencapsulations.webdrivercomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;

public final class Interaction extends WebdriverComponent implements HasInputDevices, HasTouchScreen {

	public Interaction(WebDriver driver) {
		super(driver);
	}

	@Override
	public Keyboard getKeyboard()	{
		return ((HasInputDevices) driver).getKeyboard();				
	}

	@Override
	public Mouse getMouse()	{
		return ((HasInputDevices) driver).getMouse();			
	}

	@Override
	public TouchScreen getTouch()	{
		return ((HasTouchScreen) driver).getTouch();
	}

}
