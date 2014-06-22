package org.primitive.model.common.mobile;

import java.lang.reflect.Method;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.primitive.model.common.FunctionalPart;
import org.primitive.proxy.DefaultInterceptor;
import org.primitive.proxy.EnhancedProxyFactory;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.components.bydefault.ByAccessibilityId;
import org.primitive.webdriverencapsulations.components.bydefault.ByAndroidUIAutomator;
import org.primitive.webdriverencapsulations.components.bydefault.ByIosUIAutomation;
import org.primitive.webdriverencapsulations.components.bydefault.ComponentFactory;
import org.primitive.webdriverencapsulations.components.bydefault.TouchActionsPerformer;
import org.primitive.webdriverencapsulations.interfaces.IHasActivity;

public abstract class Context extends FunctionalPart implements IHasActivity,
		Rotatable {
	
	private static class TouchActionsInterceptor extends DefaultInterceptor {
		/**
		 * Unpacks wrapped {@link RemoteWebElement} before some method of a
		 * {@link TouchAction} instance is performed
		 */
		@Override
		public Object intercept(Object obj, Method method, Object[] args,
				MethodProxy proxy) throws Throwable {
			for (int i=0; i < args.length; i++){
				Object arg = args[i];
				if (arg instanceof WebElement){
					while (arg instanceof WrapsElement){
						arg = ((WrapsElement) arg).getWrappedElement();
					}
					args[i] = arg;
				}
			}			
			return super.intercept(obj, method, args, proxy);
		}
	}

	/** 
	 * {@link WebElement} implementations are not instances of {@link RemoteWebElement} some times
	 * They wrap object of {@link RemoteWebElement}. So it should be unpacked before some touch action 
	 * is performed 
	 *
	 */
	protected class TouchActions {
		private final Context context;

		private TouchActions(Context context) {
			this.context = context;
		}

		public TouchAction getTouchAction() {
			return EnhancedProxyFactory.getProxy(TouchAction.class,
					new Class<?>[] { MobileDriver.class },
					new Object[] { (MobileDriver) context.driverEncapsulation
							.getWrappedDriver() },
					new TouchActionsInterceptor());
		}
	}

	protected final ByAccessibilityId byAccessibilityId;
	protected final ByAndroidUIAutomator byAndroidUIAutomator;
	protected final ByIosUIAutomation byIosUIAutomation;
	protected final TouchActionsPerformer touchActionsPerformer;
	protected final TouchActions touchActions = new TouchActions(this); 

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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
		touchActionsPerformer = ComponentFactory.getComponent(TouchActionsPerformer.class, 
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
