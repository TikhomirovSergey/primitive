/**
 * 
 */
package org.primitive.testobjects;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.exceptions.UnclosedBrowserWindowException;
import org.primitive.interfaces.ITakesPictureOfItSelf;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.testobjects.decomposition.IDecomposable;
import org.primitive.testobjects.decomposition.IHasWebElementFrames;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.ui.WebElementHighLighter;
import org.primitive.webdriverencapsulations.webdrivercomponents.FrameSupport;
import org.primitive.webdriverencapsulations.webdrivercomponents.Interaction;
import org.primitive.webdriverencapsulations.webdrivercomponents.PageFactoryWorker;
/**
 * @author s.tihomirov
 *It describes simple web page or its fragment 
 */
public abstract class FunctionalPart extends TestObject implements IHasWebElementFrames, ITakesPictureOfItSelf {

	/**
	 * @author s.tihomirov
	 *
	 */
	@Target(value=ElementType.METHOD)
	@Retention(value= RetentionPolicy.RUNTIME)
	public static @interface InteractiveMethod {
	
	}

	protected PageFactoryWorker pageFactoryWorker;	
	protected FunctionalPart parent; //parent test object	
	protected FrameSupport frameSupport;	
	//Integer specification of a frame that object is placed on.
	private Integer frameToSwitchOnInt = null;
	//String specification of a frame that object is placed on.
	private String frameToSwitchOnStr  = null;	
	//WebElement specification of a frame that object is placed on
	private Object frameToSwitchOnElem = null;
    //page object is created by specified entity
	protected Entity originalEntity;
	private WebElementHighLighter highLighter;
	protected Interaction interaction;
	
	//default constructor body
	private void constroctorBody()
	{
		pageFactoryWorker = driverEncapsulation.getPageFactoryWorker();
		frameSupport      = driverEncapsulation.getFrameSupport();
		highLighter   = driverEncapsulation.getHighlighter();
		interaction    = driverEncapsulation.getInteraction();
	}
	
	//switches to object
	//this method can be overridden
	protected synchronized void switchToMe()
	{
		//firstly we should switch parent browser window on
		if (parent!=null)
		{
			parent.switchToMe();		
		}
		else
		{
			nativeWindow.switchToMe();
		}
		//if this object is placed on some frame
		if (frameToSwitchOnInt!=null)
		{   //we should switch to it
			frameSupport.switchTo(frameToSwitchOnInt);
			return;			
		}
		if (frameToSwitchOnStr!=null)
		{
			frameSupport.switchTo(frameToSwitchOnStr);
			return;
		}
		if (frameToSwitchOnElem!=null)
		{
			frameSupport.switchTo((WebElement) frameToSwitchOnElem);
			return;
		}
		return;
	}	
	
	@Override
	void addChild(TestObject child)
	{
		super.addChild(child);
		FunctionalPart childPart = (FunctionalPart) child;
		childPart.parent = this;
		childPart.originalEntity = this.originalEntity;
	}
	
	protected FunctionalPart(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		constroctorBody();
	}
	
	//constructs from another page object
	protected FunctionalPart(FunctionalPart parent)
			throws ConcstructTestObjectException {
		super(parent.nativeWindow);
		constroctorBody();
		parent.addChild(this);
	}
	
	
	
	//constructor with specified integer frame value
	protected FunctionalPart(SingleWindow browserWindow, Integer frameIndex) throws ConcstructTestObjectException
	{
		super(browserWindow);
		constroctorBody();
		frameToSwitchOnInt = frameIndex;
	}
	
	//constructs from another page object
	protected FunctionalPart(FunctionalPart parent, Integer frameIndex)
			throws ConcstructTestObjectException {
		super(parent.nativeWindow);
		constroctorBody();
		parent.addChild(this);
		frameToSwitchOnInt = frameIndex;
	}
	
	//constructor with specified string frame value. pathToFrame can be relative to another frame
	protected FunctionalPart(SingleWindow browserWindow, String pathToFrame) throws ConcstructTestObjectException
	{
		super(browserWindow);
		constroctorBody();
		frameToSwitchOnStr = pathToFrame;
	}
	
	//constructs from another page object
	protected FunctionalPart(FunctionalPart parent, String pathToFrame) throws ConcstructTestObjectException
	{
		super(parent.nativeWindow);
		constroctorBody();
		parent.addChild(this);
		frameToSwitchOnStr = pathToFrame;
	}
	
	//constructor with specified WebElement frame value.
	protected FunctionalPart(SingleWindow browserWindow, WebElement frameElement) throws ConcstructTestObjectException
	{
		super(browserWindow);
		constroctorBody();
		frameToSwitchOnElem = frameElement;
	}	
	
	//constructs from another page object
	protected FunctionalPart(FunctionalPart parent, WebElement frameElement) throws ConcstructTestObjectException
	{
		super(parent.nativeWindow);
		constroctorBody();
		parent.addChild(this);
		frameToSwitchOnElem = frameElement;
	}
	
	//constructor with specified string frame value. pathToFrame can be relative to another frame
	//timeOutInSec is specified for situations when frame can't be switched on instantly
	protected FunctionalPart(SingleWindow browserWindow, String pathToFrame, Long timeOutInSec) throws ConcstructTestObjectException
	{
		super(browserWindow);
		constroctorBody();
		nativeWindow.switchToMe();
		frameSupport.switchTo(pathToFrame, timeOutInSec);
		frameToSwitchOnStr = pathToFrame;
	}
	
	//constructs from another page object
	protected FunctionalPart(FunctionalPart parent, String pathToFrame, Long timeOutInSec) throws ConcstructTestObjectException
	{
		super(parent.nativeWindow);
		constroctorBody();
		parent.addChild(this);
		nativeWindow.switchToMe();
		frameSupport.switchTo(pathToFrame, timeOutInSec);
		frameToSwitchOnStr = pathToFrame;
	}	
	
	
	//Methods that you see below can be used for loading of a page object
	//This method loads page object with the list of field decorators
	protected void load(ArrayList<FieldDecorator> decorators)
	{
		for (FieldDecorator decorator: decorators)
		{   //decorators that are given from outside or generated inside
			pageFactoryWorker.initPageFactory(decorator, this);
		}
	}
	
	//This method loads page object with the list of ElementLocatorFactory implementations
	protected  void load(List<ElementLocatorFactory> factories)
	{
		for (ElementLocatorFactory factory: factories)
		{   //factory that are given from outside or generated inside
			pageFactoryWorker.initPageFactory(factory, this);
		}	
	}
	
	//The method below simply loads page factory
	protected void load()
	{
		pageFactoryWorker.initPageFactory(this);
	}
	
	
	private Class<?>[] restructureParamArray(Class<?>[] original)
	{
		Class<?>[] constructParams = new Class<?> [original.length + 1];
		constructParams[0] = FunctionalPart.class;
		for (int i=0; i < original.length; i++)
		{
			constructParams[i+1] = original[i];
		}
		return constructParams;
	}
	
	private Object[] restructureValueArray(Object[] original)
	{
		Object[] constructValues = new Object[original.length + 1];
		constructValues[0]       = this;
		for (int i=0; i < original.length; i++)
		{
			constructValues[i+1] = original[i];
		}
		return constructValues;
	}
	
	//gets another page fragment
	//using any accessible (!!!) Page constructor Page creates another dependent page objects
	//Class "Page" should be first in the list of constructor parameters
	//"params" we specify without "Page" because it will be added by this method
	//So, this way we build hierarchy of page objects
	protected  <T extends IDecomposable> T get(Class<T> partClass, Class<?>[] params, Object[] values) throws ConcstructTestObjectException
	{
		return ObjectFactory.get(partClass, restructureParamArray(params), restructureValueArray(values));
	}
	
	
	// - simple constructor 
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {}; 
		Object[] values = new Object[] {}; 
		return get(partClass, params, values);
	}
	
		
	//- with specified frame index
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass, Integer frameIndex) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {Integer.class}; 
		Object[] values = new Object[] {frameIndex};
		return get(partClass, params, values);
	}
		
	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass, String pathToFrame) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class}; 
		Object[] values = new Object[] {pathToFrame};
		return get(partClass, params, values);
	}
		
	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass, String pathToFrame, Long timeOutInSec) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {String.class, Long.class}; 
		Object[] values = new Object[] {pathToFrame, timeOutInSec};
		return get(partClass, params, values);
	}
	
	// - with frame that specified as web element
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass, WebElement frameElement) throws ConcstructTestObjectException
	{
		Class <?>[] params = new Class[] {WebElement.class}; 
		Object[] values = new Object[] {frameElement};
		return get(partClass, params, values);
	}
	
	@Override
	public void destroy()
	{
		//if browser window disappeared
		//all objects that are placed on this will be destroyed. I think it 
		//should work this way				
		//if window was closed or it disappeared 			
		if (!nativeWindow.exists())
		{
			nativeWindow.destroy();
		}
		super.destroy();
		return;
	}
	
	//Closes browser window and destroys all page objects that are placed on it
	public void close() throws UnclosedBrowserWindowException, NoSuchWindowException, UnhandledAlertException, UnreachableBrowserException
	{
		try
		{
			nativeWindow.close();
			destroy();
		}	
		catch (UnclosedBrowserWindowException e)
		{
			Log.warning("Browser window which test object is placed on hasn't been closed!",e);
			throw e;
		}
		catch (NoSuchWindowException e)
		{
			Log.warning("Browser window which test object is placed on has disappeared!",e);
			destroy();
			throw e;
		}	
		catch (UnreachableBrowserException e)
		{
			destroy();
			throw e;
		}
	}
	
	//takes screenshots for log messages with SEVERE level
	@InteractiveMethod
	@Override
	public void takeAPictureOfASevere(String comment)
	{
		Photographer.takeAPictureOfASevere(driverEncapsulation.getWrappedDriver(), comment);
	}
	
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, String comment)
	{
		highLighter.highlightAsSevere(driverEncapsulation.getWrappedDriver(), element, comment);
	}
	
	@InteractiveMethod
	public void highlightAsSevere(WebElement element, Color highlight, String comment)
	{
		highLighter.highlightAsSevere(driverEncapsulation.getWrappedDriver(), element, highlight, comment);
	}
	
	
	//takes screenshots for log messages with WARNING level
	@InteractiveMethod
	@Override
	public void takeAPictureOfAWarning(String comment)
	{
		Photographer.takeAPictureOfAWarning(driverEncapsulation.getWrappedDriver(), comment);
	}
	
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, String comment)
	{
		highLighter.highlightAsWarning(driverEncapsulation.getWrappedDriver(), element, comment);
	}
	
	@InteractiveMethod
	public void highlightAsWarning(WebElement element, Color highlight, String comment)
	{
		highLighter.highlightAsWarning(driverEncapsulation.getWrappedDriver(), element, highlight, comment);
	}	
	
	//takes screenshots for log messages with INFO level
	@InteractiveMethod
	@Override
	public void takeAPictureOfAnInfo(String comment)
	{
		Photographer.takeAPictureOfAnInfo(driverEncapsulation.getWrappedDriver(), comment);
	}
	
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, String comment)
	{
		highLighter.highlightAsInfo(driverEncapsulation.getWrappedDriver(), element, comment);
	}
	
	@InteractiveMethod
	public void highlightAsInfo(WebElement element, Color highlight, String comment)
	{
		highLighter.highlightAsInfo(driverEncapsulation.getWrappedDriver(), element, highlight, comment);
	}	
	
	//takes screenshots for log messages with FINE level
	@InteractiveMethod
	@Override
	public void takeAPictureOfAFine(String comment)
	{
		Photographer.takeAPictureOfAFine(driverEncapsulation.getWrappedDriver(), comment);
	}
		
	@InteractiveMethod
	public void highlightAsFine(WebElement element, String comment)
	{
		highLighter.highlightAsFine(driverEncapsulation.getWrappedDriver(), element, comment);
	}
		
	@InteractiveMethod
	public void highlightAsFine(WebElement element, Color highlight, String comment)
	{
		highLighter.highlightAsFine(driverEncapsulation.getWrappedDriver(), element, highlight, comment);
	}
	
}
