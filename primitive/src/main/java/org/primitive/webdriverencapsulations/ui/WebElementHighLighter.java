package org.primitive.webdriverencapsulations.ui;

import java.awt.Color;
import java.util.logging.Level;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;
import org.primitive.interfaces.IDestroyable;
import org.primitive.interfaces.IWebElementHighlighter;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.logging.eLogColors;

/**
 * @author s.tihomirov
 * it can highlight elements and do screenshots
 */	
public class WebElementHighLighter implements IConfigurable,
		IWebElementHighlighter, IDestroyable {

	/**
	 * @author s.tihomirov
	 * it highlights element changing its style
	 * All this happens in another thread
	 */	
	private final class HighLighter extends Thread {
		private final JavascriptExecutor scriptExecutor;
		private final WebElement elementToBeHiglighted;
		private final Color highlightColor;
		
		private String originalStyle;
		
		private final String borderExpression = "2px solid ";
		
		private HighLighter(JavascriptExecutor scriptExecutor, WebElement elementToBeHiglighted, Color color)
		{
			this.scriptExecutor = scriptExecutor;
			this.elementToBeHiglighted = elementToBeHiglighted;
			this.highlightColor = color;
		}
		
		private void setOriginalStyle()
		{
			scriptExecutor.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');",  elementToBeHiglighted);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		private void getOriginalStyle()
		{
			originalStyle  = elementToBeHiglighted.getAttribute("style");
		}
		
		private String getNewColor()
		{
			return borderExpression + "rgb("+ Integer.toString(highlightColor.getRed()) +  ","+Integer.toString(highlightColor.getGreen())+","+Integer.toString(highlightColor.getBlue())+")";
		}
		
		private void setNewColor(String colorExpression)
		{
			scriptExecutor.executeScript("arguments[0].style.border = '" + colorExpression + "'",  elementToBeHiglighted);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		@Override
		public void run()
		{
			try
			{
				getOriginalStyle();
				setNewColor(getNewColor());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				setOriginalStyle();
			}
			catch (StaleElementReferenceException e)
			{
				Log.debug(e.getClass().getSimpleName() + " has been caught out!",e);
			}
		}
		
		@Override
		public synchronized void interrupt()
		{
			try
			{
				super.interrupt();
			}
			catch (IllegalThreadStateException e)
			{
				Log.debug("Highlighting has neen already finished");
			}
			
			try
			{
				setOriginalStyle();
			}
			catch (StaleElementReferenceException e)
			{
				Log.debug(e.getClass().getSimpleName() + " has been caught out!",e);
			}
			finally
			{				
				try {
					this.finalize();
				} catch (Throwable e) {
				}
			}
		}

	}

	//is this doing screenshots
	private boolean toDoScreenShots; 
	private final boolean isDoingScreenShotsByDefault = true;
	private HighLighter currentHighLighter;
	
	
	private void setNewCurrentHighLigter(WebDriver driver, WebElement webElement, Color color)
	{
		currentHighLighter = new HighLighter((JavascriptExecutor) driver, webElement, color);
		currentHighLighter.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
	}
	
	@Override
	public synchronized void highlight(WebDriver driver, WebElement webElement,
			Color highlight, Level LogLevel, String Comment) {
		setNewCurrentHighLigter(driver, webElement, highlight);
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureForLog(driver, LogLevel, Comment);
		}	
		else
		{
			Log.log(LogLevel, Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void highlightAsFine(WebDriver driver, WebElement webElement,
			Color highlight, String Comment) {
		setNewCurrentHighLigter(driver, webElement, highlight);
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAFine(driver, Comment);
		}	
		else
		{
			Log.debug(Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void highlightAsFine(WebDriver driver, WebElement webElement,
			String Comment) {
		setNewCurrentHighLigter(driver, webElement, eLogColors.DEBUGCOLOR.getStateColor());
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAFine(driver, Comment);
		}	
		else
		{
			Log.debug(Comment);
		}
		currentHighLighter.interrupt();

	}

	@Override
	public synchronized void highlightAsInfo(WebDriver driver, WebElement webElement,
			Color highlight, String Comment) {
		setNewCurrentHighLigter(driver, webElement, highlight);
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAnInfo(driver, Comment);
		}
		else
		{
			Log.message(Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void highlightAsInfo(WebDriver driver, WebElement webElement,
			String Comment) {
		setNewCurrentHighLigter(driver, webElement, eLogColors.CORRECTSTATECOLOR.getStateColor());
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAnInfo(driver, Comment);
		}	
		else
		{
			Log.message(Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void highlightAsSevere(WebDriver driver, WebElement webElement,
			Color highlight, String Comment) {
		setNewCurrentHighLigter(driver, webElement, highlight);
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfASevere(driver, Comment);
		}
		else
		{
			Log.error(Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void highlightAsSevere(WebDriver driver, WebElement webElement,
			String Comment) {
		setNewCurrentHighLigter(driver, webElement, eLogColors.SEVERESTATECOLOR.getStateColor());
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfASevere(driver, Comment);
		}
		else
		{
			Log.error(Comment);
		}
		currentHighLighter.interrupt();

	}

	@Override
	public synchronized void highlightAsWarning(WebDriver driver, WebElement webElement,
			Color highlight, String Comment) {
		setNewCurrentHighLigter(driver, webElement, highlight);
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAWarning(driver, Comment);
		}	
		else
		{
			Log.warning(Comment);
		}
		currentHighLighter.interrupt();

	}

	@Override
	public synchronized void highlightAsWarning(WebDriver driver, WebElement webElement,
			String Comment) {
		setNewCurrentHighLigter(driver, webElement, eLogColors.WARNSTATECOLOR.getStateColor());
		if (toDoScreenShots)
		{	
			Photographer.takeAPictureOfAWarning(driver, Comment);
		}
		else
		{
			Log.warning(Comment);
		}
		currentHighLighter.interrupt();
	}

	@Override
	public synchronized void resetAccordingTo(Configuration config) {
		Boolean toDoScreenShots = config.getScreenShots().getToDoScreenShotsOnElementHighLighting();
		if (toDoScreenShots==null)
		{
			this.toDoScreenShots = isDoingScreenShotsByDefault;
		}
		else
		{
			this.toDoScreenShots = toDoScreenShots;
		}
	}

	@Override
	public void destroy() {
		try {
			this.finalize();
		} catch (Throwable e) {}		
	}

}
