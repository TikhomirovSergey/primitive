package org.primitive.logging;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
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
		byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		BufferedImage buffer = getBufferedImage(bytes); 
		return(buffer);
	}
	
	private static BufferedImage getImageFromAugmenter(WebDriver driver) throws IOException
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		byte[] bytes = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);
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
		catch (ClassCastException|UnsupportedOperationException e)
		{
			return getImageFromAugmenter(driver);
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
	
	//highlights web elements on screenshots
	private static void drawOutLines(BufferedImage buffer, Dimension size, Point p, Color highlight)
	{
		highlight.darker();
		Graphics bufferedGraphics = buffer.getGraphics();
		bufferedGraphics.setColor(highlight); 
		bufferedGraphics.drawRoundRect(p.getX(), p.getY(), size.getWidth(), size.getHeight(), 10, 10);
		bufferedGraphics.dispose();
	}
	
	//takes pictures, highlights elements on page and makes buffered images
	private static BufferedImage takeAPicture(WebDriver driver, WebElement webElement, Color highlight) throws IOException, NoSuchWindowException
	{
		try
		{
			Point location = ((Locatable) webElement).getCoordinates().inViewPort();
			BufferedImage buffer = takeAPicture(driver);
			drawOutLines(buffer, webElement.getSize(), location, highlight);
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
			Log.debug("Operation is not supported! Take a screenshot.", e);
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
			Log.debug("Operation is not supported! Take a screenshot.", e);
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
