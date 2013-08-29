package org.primitive.logging;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.primitive.configuration.Configuration;



public final class Photographer 
{
	private static final String pictureNameByDefault = "Picture";
	private static final String pictureFolderNameByDefault = "Imgs/"; //in case if there is no customized settings for picture storing
	public static final String format = "png";
	
	private static String picture = resetPictureName();
	private static String folder  = resetFolderName();
	
	private static String resetPictureName()
	{
		String picName = Configuration.byDefault.getScreenShots().getFile();
		if (picName == null)
		{
			picture = pictureNameByDefault;
		}
		else
		{
			picture = picName; 
		}
		return picture;
	}
	
	private static String resetFolderName()
	{
		String dirName = Configuration.byDefault.getScreenShots().getFolder();
		if (dirName == null)
		{
			folder = pictureFolderNameByDefault;
		}
		else
		{
			folder= dirName; 
		}
		return folder;
	}
	
	private static BufferedImage getImageFromDriver(WebDriver driver) throws IOException
	{
		byte[] bytes = null;
		try
	    {
	    	bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    }
	    catch (ClassCastException e)
	    {
	    	WebDriver augmentedDriver = new Augmenter().augment(((WrapsDriver) driver).getWrappedDriver());
	    	bytes = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);	
	    }
		BufferedImage buffer = getBufferedImage(bytes); 
		return(buffer);
	}
	
	//takes pictures and makes buffered images
	private static BufferedImage takeAPicture(WebDriver driver) throws IOException, NoSuchWindowException, UnsupportedOperationException
	{
		try
		{
			return(getImageFromDriver(driver));
		}
		catch (UnsupportedOperationException e)
		{
			throw e;
		}
		catch (NoSuchWindowException e1)
		{
			throw e1;
		}
		catch (IOException e2)
		{
			throw e2;
		}
	}
		
	private static BufferedImage getBufferedImage(byte[] original) throws IOException
	{
		BufferedImage buffer = null;
		try 
		{
			buffer = ImageIO.read(new ByteArrayInputStream(original));
			return(buffer);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	private static void changeStyle(JavascriptExecutor jScriptExecutor, WebElement element, String style)
	{
		jScriptExecutor.executeScript("arguments[0].setAttribute('style', '" + style + "');",  element);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			Log.warning(e.getMessage(),e);
		}
	}
	
	//takes pictures, highlights elements on page and makes buffered images
	private static BufferedImage takeAPicture(WebDriver driver, WebElement webElement, Color highlight) throws IOException, NoSuchWindowException
	{
		try
		{
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			String originalStyle  = webElement.getAttribute("style");
			changeStyle(js, webElement, "border: 2px solid rgb("+ Integer.toString(highlight.getRed()) +  ","+Integer.toString(highlight.getGreen())+","+Integer.toString(highlight.getBlue())+");");
			BufferedImage buffer = takeAPicture(driver);
			changeStyle(js, webElement, originalStyle);
			return(buffer);
		}
		catch (IOException e)
		{
			throw e;
		}
		catch (NoSuchWindowException e)
		{
			throw e;
		}
	}
		
	//applies images
	private static void makeFileForLog(BufferedImage imageForLog, Level LogLevel, String Comment)
	{
		
		String FolderPath = "";	
		FolderPath = folder;	
				
		File ImgFolder = new File(FolderPath);
		ImgFolder.mkdirs();
		
		long currentMills = Calendar.getInstance().getTimeInMillis();
		File picForLog = new File(FolderPath + picture + '_' + Long.toString(currentMills) + "." + format);
		try 
		{
			ImageIO.write(imageForLog, format, picForLog);
			Log.log(LogLevel, Comment, picForLog); 
		} 
		catch (IOException e) 
		{
			Log.warning("Can't take a screenshot! " + e.getMessage()); 
		}
	}
	
	//takes pictures of full browser windows
	public synchronized static void takeAPictureForLog(WebDriver driver, Level LogLevel, String Comment) throws NoSuchWindowException
	{
		try
		{
			BufferedImage imageForLog = takeAPicture(driver); 
			makeFileForLog(imageForLog, LogLevel, Comment);
		}
		catch(IOException e)
		{
			Log.warning("Can't post a picture to log! " + e.getMessage());
			Log.log(LogLevel, Comment);
		}
		catch (NoSuchWindowException e) 
		{
			throw e;
		}
		catch (ClassCastException|UnsupportedOperationException e)
		{
			Log.debug("Operation is not supported! Take a screenshot. " + e.getMessage(), e);
			Log.log(LogLevel, Comment);
		}		
	}
	
	//takes pictures of full browser windows with highlighted web element
	public synchronized static void takeAPictureForLog(WebDriver driver, WebElement webElement, Color highlight, Level LogLevel, String Comment) throws NoSuchWindowException
	{
		try 
		{
			BufferedImage imageForLog= takeAPicture(driver, webElement, highlight);
			makeFileForLog(imageForLog, LogLevel, Comment);
		} 
		catch(IOException e)
		{
			Log.warning("Can't post a picture to log! " + e.getMessage());
			Log.log(LogLevel, Comment);
		}
		catch (NoSuchWindowException e) 
		{
			throw e;
		}
		catch (ClassCastException|UnsupportedOperationException e)
		{
			Log.debug("Operation is not supported! Take a screenshot." + e.getMessage(), e);
			Log.log(LogLevel, Comment);
		}
	}
	
	public synchronized static void takeAPictureOfASevere(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.SEVERE, Comment);
	}
	
	public synchronized static void takeAPictureOfAWarning(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.WARNING, Comment);
	}
	
	public synchronized static void takeAPictureOfAnInfo(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.INFO, Comment);
	}
	
	public synchronized static void takeAPictureOfAFine(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.FINE, Comment);
	}
	
	public synchronized static void takeAPictureOfASevere(WebDriver driver, WebElement webElement, Color highlight, String Comment)
	{
		takeAPictureForLog(driver, webElement, highlight, Level.SEVERE, Comment);
	}
			
	public synchronized static void takeAPictureOfAWarning(WebDriver driver, WebElement webElement, Color highlight, String Comment)
	{
		takeAPictureForLog(driver, webElement, highlight,Level.WARNING, Comment);
	}
			
	public synchronized static void takeAPictureOfAnInfo(WebDriver driver, WebElement webElement, Color highlight,  String Comment)
	{
		takeAPictureForLog(driver, webElement, highlight,  Level.INFO, Comment);
	}
	
	public synchronized static void takeAPictureOfAFine(WebDriver driver,  WebElement webElement, Color highlight,   String Comment)
	{
		takeAPictureForLog(driver, webElement, highlight, Level.FINE, Comment);
	}
	
	public synchronized static void takeAPictureOfASevere(WebDriver driver, WebElement webElement, String Comment)
	{
		takeAPictureForLog(driver, webElement, eLogColors.SEVERESTATECOLOR, Level.SEVERE, Comment);
	}

	public synchronized static void takeAPictureOfAWarning(WebDriver driver, WebElement webElement, String Comment)
	{
		takeAPictureForLog(driver, webElement, eLogColors.WARNSTATECOLOR, Level.WARNING, Comment);
	}
		
	public synchronized static void takeAPictureOfAnInfo(WebDriver driver, WebElement webElement, String Comment)
	{
		takeAPictureForLog(driver, webElement, eLogColors.CORRECTSTATECOLOR,  Level.INFO, Comment);
	}
	
	public synchronized static void takeAPictureOfAFine(WebDriver driver,  WebElement webElement, String Comment)
	{
		takeAPictureForLog(driver, webElement, eLogColors.DEBUGCOLOR, Level.FINE, Comment);
	}	
}
