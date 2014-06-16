package org.primitive.model;

import org.openqa.selenium.WebElement;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.interfaces.IHasActivity;

public abstract class Context extends FunctionalPart implements IHasActivity {

	protected Context(SingleContext context) {
		super(context);
	}

	protected Context(FunctionalPart parent) {
		super(parent);
	}

	protected Context(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
	}

	protected Context(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
	}

	protected Context(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
	}

	protected Context(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
	}

	protected Context(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
	}

	protected Context(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
	}

	@Override
	public String currentActivity() {
		return ((SingleContext) handle).currentActivity();
	}

}
