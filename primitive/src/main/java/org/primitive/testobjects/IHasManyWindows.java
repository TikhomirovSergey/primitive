package org.primitive.testobjects;

import java.net.URL;

import org.primitive.webdriverencapsulations.WindowSwitcher;

//For objects that can exist in namy browser windows
public interface IHasManyWindows {

	//From new browser windows:
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass);

	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, String title);
	
	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, URL url);

	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, URL url, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(Class<? extends FunctionalPart> partClass, WindowSwitcher switcher, String title, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, String title);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, String title, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, URL url);

	public <T extends FunctionalPart> T getFromNewWindow(Integer frameIndex, Class<? extends FunctionalPart> partClass, URL url, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, String title);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, String title, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, URL url);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Class<? extends FunctionalPart> partClass, URL url, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, String title);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, String title, long timeOutSec);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, URL url);

	public <T extends FunctionalPart> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, URL url, long timeOutSec);

	//From browser windows by their indexes
	public <T extends FunctionalPart> T getFromWinow(Class<? extends FunctionalPart> partClass, int windowIndex);

	public <T extends FunctionalPart> T getFromWinow(Class<? extends FunctionalPart> partClass, Integer frameIndex, int windowIndex);

	public <T extends FunctionalPart> T getFromWinow(String pathToFrame, Class<? extends FunctionalPart> partClass, int windowIndex);
	
	public <T extends FunctionalPart> T getFromWinow(String pathToFrame, Long timeOutInSec,  Class<? extends FunctionalPart> partClass, int windowIndex);


}
