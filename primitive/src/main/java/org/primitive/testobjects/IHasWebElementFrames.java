package org.primitive.testobjects;

import org.openqa.selenium.WebElement;

public interface IHasWebElementFrames extends IDecomposable {
	// - with frame that specified as web element
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, WebElement frameElement);

}
