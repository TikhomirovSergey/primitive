package org.primitive.testobjects.interfaces;

import java.net.URL;
import java.util.List;

import org.primitive.webdriverencapsulations.WindowSwitcher;

//For objects that can exist in namy browser windows
public interface IHasManyWindows {

	//From new browser windows:
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass);

	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass, String title);
	
	public <T extends IDecomposable> T getFromNewWindow(Class<T>partClass, List<String> urls);

	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass, List<String> urls, long timeOutSec);
	
	public <T extends IDecomposable> T getFromNewWindow(Class<T>partClass, URL url);

	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass, URL url, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass, WindowSwitcher switcher, String title, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, String title);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, String title, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, List<String> urls);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, URL url);

	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex, Class<T> partClass, URL urls, long timeOutSec);
	
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, String title);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, String title, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, List<String> urls);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, URL url);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Class<T> partClass, URL url, long timeOutSec);
	
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, String title);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, String title, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, List<String> urls);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, List<String> urls, long timeOutSec);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, URL url);

	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, URL url, long timeOutSec);
	
	//From browser windows by their indexes
	public <T extends IDecomposable> T getFromWindow(Class<T> partClass, int windowIndex);

	public <T extends IDecomposable> T getFromWindow(Class<T> partClass, Integer frameIndex, int windowIndex);

	public <T extends IDecomposable> T getFromWindow(String pathToFrame, Class<T> partClass, int windowIndex);
	
	public <T extends IDecomposable> T getFromWindow(String pathToFrame, Long timeOutInSec,  Class<T> partClass, int windowIndex);


}
