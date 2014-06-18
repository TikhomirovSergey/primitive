package org.primitive.model;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.components.bydefault.ByAccessibilityId;
import org.primitive.webdriverencapsulations.components.bydefault.ByAndroidUIAutomator;
import org.primitive.webdriverencapsulations.components.bydefault.ByIosUIAutomation;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.interfaces.IHasActivity;

public abstract class Context extends FunctionalPart implements IHasActivity,
		Rotatable {
	protected final ByAccessibilityId byAccessibilityId;
	protected final ByAndroidUIAutomator byAndroidUIAutomator;
	protected final ByIosUIAutomation byIosUIAutomation;

	protected Context(SingleContext context) {
		super(context);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(FunctionalPart parent) {
		super(parent);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(SingleContext context, Integer frameIndex) {
		super(context, frameIndex);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(FunctionalPart parent, Integer frameIndex) {
		super(parent, frameIndex);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(SingleContext context, String pathToFrame) {
		super(context, pathToFrame);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(FunctionalPart parent, String pathToFrame) {
		super(parent, pathToFrame);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(SingleContext context, WebElement frameElement) {
		super(context, frameElement);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	protected Context(FunctionalPart parent, WebElement frameElement) {
		super(parent, frameElement);
		byAccessibilityId = ComponentFactory
				.getComponent(ByAccessibilityId.class,
						driverEncapsulation.getWrappedDriver());
		byAndroidUIAutomator = ComponentFactory.getComponent(
				ByAndroidUIAutomator.class,
				driverEncapsulation.getWrappedDriver());
		byIosUIAutomation = ComponentFactory
				.getComponent(ByIosUIAutomation.class,
						driverEncapsulation.getWrappedDriver());
	}

	@Override
	public String currentActivity() {
		return ((SingleContext) handle).currentActivity();
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		((SingleContext) handle).rotate(orientation);
	}

	@Override
	public ScreenOrientation getOrientation() {
		return ((SingleContext) handle).getOrientation();
	}

}
