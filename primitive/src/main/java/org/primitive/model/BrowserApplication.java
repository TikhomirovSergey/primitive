package org.primitive.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.primitive.model.interfaces.IDecomposable;
import org.primitive.model.interfaces.IHasManyHandlesWithURL;
import org.primitive.webdriverencapsulations.Handle;
import org.primitive.webdriverencapsulations.WindowManager;

/**
 * Representation of a browser application
 */
public class BrowserApplication extends Application implements IHasManyHandlesWithURL {


	protected BrowserApplication(Handle handle) {
		super(handle);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title) {
		return super.getFromNewHandle(partClass, title);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, long timeOutSec) {
		return super.getFromNewHandle(partClass, title, timeOutSec);
	}
	
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, Integer frameIndex) {
		return super.getFromNewHandle(partClass, title, frameIndex);
	}
	
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String title) {
		return super.getFromNewHandle(pathToFrame, partClass, title);
	}

	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, String title, long timeOutSec) {
		return super.getFromNewHandle(pathToFrame, partClass, title, timeOutSec);
	}
	
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			String title, long timeOutSec, Integer frameIndex) {
		return super.getFromNewHandle(partClass, title, timeOutSec, frameIndex);
	}

	
	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls) {
		Class<?>[] params = new Class[] {Handle.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(urls)};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url) {
		Class<?>[] params = new Class[] {Handle.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				})};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs and timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec) {
		Class<?>[] params = new Class[] {Handle.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(timeOutSec, urls)};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL  and timeout
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, long timeOutSec) {
		Class<?>[] params = new Class[] {Handle.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				})};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs and index of
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, Integer frameIndex) {
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(urls), frameIndex};
		return get(partClass, params, values);
	}


	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs and path to
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls) {
		Class<?>[] params = new Class[] {Handle.class, String.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(urls), pathToFrame};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs, timeout
     * and index of
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			List<String> urls, long timeOutSec, Integer frameIndex) {
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(timeOutSec, urls), frameIndex};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using list of regular expressions that describe expected URLs,
	 *  timeout and path to the required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, List<String> urls, long timeOutSec) {
		Class<?>[] params = new Class[] {Handle.class, String.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(timeOutSec, urls), pathToFrame};
		return get(partClass, params, values);
	}


	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL and index of
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, Integer frameIndex) {
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				}), frameIndex};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL and path to the
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, final URL url) {
		Class<?>[] params = new Class[] {Handle.class, String.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				}), pathToFrame};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL, timeout
     * and index of
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(Class<T> partClass,
			final URL url, Integer frameIndex, long timeOutSec) {
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(timeOutSec,
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				}), frameIndex};
		return get(partClass, params, values);
	}

	/**
	 * Gets a functional part (page object) from the new opened handle 
	 * using expected URL, timeout
     * and path to the
	 * required frame
	 */
	@Override
	public <T extends IDecomposable> T getFromNewHandle(String pathToFrame,
			Class<T> partClass, final URL url, long timeOutSec) {
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {((WindowManager) manager).switchToNew(timeOutSec,
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				}), pathToFrame};
		return get(partClass, params, values);
	}
	
	public WindowManager getWindowManager(){
		return (WindowManager) manager;
	}
}
