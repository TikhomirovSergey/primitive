package org.primitive.logging;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.Augmenter;
import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;



public final class Photographer implements IConfigurable 
{
	private static final String pictureNameByDefault = "Picture";
	private static final String pictureFolderNameByDefault = "Imgs/"; //in case if there is no customized settings for picture storing
	public static final String format = "png";
	private static final ThreadLocal<Photographer> photographerThreadLocal = new ThreadLocal<Photographer>();
	
	private String picture = pictureNameByDefault;
	private String folder  = pictureFolderNameByDefault;
	
	
	private Photographer()
	{
		super();
		resetAccordingTo(Configuration.byDefault);
	}
	
	private String resetPictureName(Configuration configuration)
	{
		String picName = configuration.getScreenShots().getFile();
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
	
	private String resetFolderName(Configuration configuration)
	{
		String dirName =configuration.getScreenShots().getFolder();
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
	
	@Override
	public void resetAccordingTo(Configuration config)
	{
		resetPictureName(config);
		resetFolderName(config);
	}
	
	private BufferedImage getImageFromDriver(WebDriver driver) throws IOException
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
	private synchronized BufferedImage takeAPicture(WebDriver driver) throws IOException, NoSuchWindowException, UnsupportedOperationException
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
		
	private BufferedImage getBufferedImage(byte[] original) throws IOException
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
	
	//applies images
	private synchronized void makeFileForLog(BufferedImage imageForLog, Level LogLevel, String Comment)
	{
		
		String FolderPath = folder;		
				
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
		Photographer photographer = photographerThreadLocal.get();
		if (photographer==null)
		{
			photographer = new Photographer();
			photographerThreadLocal.set(photographer);
		}
		try
		{
			BufferedImage imageForLog = photographer.takeAPicture(driver); 
			photographer.makeFileForLog(imageForLog, LogLevel, Comment);
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
	
	public static void takeAPictureOfASevere(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.SEVERE, Comment);
	}
	
	public static void takeAPictureOfAWarning(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.WARNING, Comment);
	}
	
	public static void takeAPictureOfAnInfo(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.INFO, Comment);
	}
	
	public static void takeAPictureOfAFine(WebDriver driver,  String Comment)
	{
		takeAPictureForLog(driver, Level.FINE, Comment);
	}	
}
