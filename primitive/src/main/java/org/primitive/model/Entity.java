package org.primitive.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.primitive.model.interfaces.IDecomposable;
import org.primitive.model.interfaces.IHasManyWindows;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WindowSwitcher;
import org.primitive.webdriverencapsulations.components.overriden.Cookies;
import org.primitive.webdriverencapsulations.components.overriden.TimeOut;

/**
 * Using it you can model your testing application as a complex aggregated
 * object it should only generate new objects in general
 */
public abstract class Entity extends TestObject implements IHasManyWindows {

	protected final Cookies cookies;
	protected final TimeOut timeOuts;
	protected final WindowSwitcher nativeSwitcher;

	protected Entity(SingleWindow browserWindow) {
		super(browserWindow);
		cookies = driverEncapsulation.getCookies();
		timeOuts = driverEncapsulation.getTimeOut();
		nativeSwitcher = browserWindow.getSwitcher();
	}

	private static Class<?>[] restructureParamArray(Class<?>[] original) {
		Class<?>[] constructParams = new Class<?>[original.length + 1];
		constructParams[0] = SingleWindow.class;
		for (int i = 0; i < original.length; i++) {
			constructParams[i + 1] = original[i];
		}
		return constructParams;
	}

	private static Object[] restructureValueArray(SingleWindow window,
			Object[] original) {
		Object[] constructValues = new Object[original.length + 1];
		constructValues[0] = window;
		for (int i = 0; i < original.length; i++) {
			constructValues[i + 1] = original[i];
		}
		return constructValues;
	}

	// if application exists on only one browser window and not opens other
	// FunctionalParts in another windows:

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use the first opened window of the test
	 * application
	 */
	protected <T extends IDecomposable> T get(Class<T> partClass,
			Class<?>[] params, Object[] values) {
		T part = ObjectFactory.get(partClass, restructureParamArray(params),
				restructureValueArray(nativeWindow, values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return get(partClass, params, values);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			Integer frameIndex) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return get(partClass, params, values);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			String pathToFrame) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return get(partClass, params, values);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getPart(Class<T> partClass,
			String pathToFrame, Long timeOutInSec) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return get(partClass, params, values);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if application opens other pages in another windows
	// and some browser window is already opened

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use any opened window that specified by index
	 */
	protected <T extends IDecomposable> T get(Class<T> partClass,
			Class<?>[] params, Object[] values, int windowIndex) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(SingleWindow.initWindowByIndex(
						nativeSwitcher, windowIndex), values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromWindow(Class<T> partClass,
			int windowIndex) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return get(partClass, params, values, windowIndex);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromWindow(Class<T> partClass,
			Integer frameIndex, int windowIndex) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return get(partClass, params, values, windowIndex);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromWindow(String pathToFrame,
			Class<T> partClass, int windowIndex) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return get(partClass, params, values, windowIndex);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, int windowIndex) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return get(partClass, params, values, windowIndex);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if application opens other pages in another windows
	// we will wait a new window for default time

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use any window that has appeared while default
	 * time was passing
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(
						SingleWindow.initNewWindow(nativeSwitcher), values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if application opens other pages in another windows
	// if time is specified in seconds

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use any window that has appeared while specified
	 * time was passing
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values, long timeOutSec) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(
						SingleWindow.initNewWindow(nativeSwitcher, timeOutSec),
						values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			long timeOutSec) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, timeOutSec);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, long timeOutSec) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values, timeOutSec);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values, timeOutSec);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values, timeOutSec);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if we need a new page on new browser window with specified title
	// We use either title of a window or piece of its title. Fragment is
	// formatted as:
	// title*, *title, *title*
	// here we use default time

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use some window that has appeared while default
	 * time was passing this window should has the matching title
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values, String title) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(
						SingleWindow.initNewWindow(nativeSwitcher, title),
						values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			String title) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, title);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, String title) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values, title);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, String title) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values, title);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, String title) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values, title);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if we need a new page on new browser window with specified title
	// We use either title of a window or piece of its title. Fragment is
	// formatted as:
	// title*, *title, *title*

	// but here we use specified time

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use some window that has appeared while specified
	 * time was passing this window should has the matching title
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values, String title, long timeOutSec) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(SingleWindow.initNewWindow(
						nativeSwitcher, title, timeOutSec), values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			String title, long timeOutSec) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, String title, long timeOutSec) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, String title, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, String title, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values, title, timeOutSec);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if we need a new page that opens in another window and loads specified
	// url
	// here we use default time

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use some window that has appeared while default
	 * time was passing this window should has page that loaded by possible URLs
	 * specified partially (RegExp)
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values, List<String> urls) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(
						SingleWindow.initNewWindow(nativeSwitcher, urls),
						values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			List<String> urls) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, urls);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			final URL url) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = -1L;
					{
						add(url.toString());
					}
				});
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, List<String> urls) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values, urls);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, final URL url) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}
				});
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, List<String> urls) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values, urls);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, final URL url) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}
				});
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, List<String> urls) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values, urls);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, final URL url) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}
				});
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// if we need a new page that opens in another window and loads specified
	// url
	// but here we use specified time

	/**
	 * using any accessible (!!!) FunctionalPart constructor Entity creates page
	 * objects Class "SingleWindow" should be first in the list of constructor
	 * parameters "params" we specify without "SingleWindow" because it will be
	 * added by this method We use some window that has appeared while specified
	 * time was passing this window should has page that loaded by possible URLs
	 * specified partially (RegExp)
	 */
	protected <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			Class<?>[] params, Object[] values, List<String> urls,
			long timeOutSec) {
		T part = ObjectFactory.get(
				partClass,
				restructureParamArray(params),
				restructureValueArray(SingleWindow.initNewWindow(
						nativeSwitcher, urls, timeOutSec), values));
		((FunctionalPart) part).originalEntity = this;
		addChild((TestObject) part);
		return part;
	}

	// - simple constructor
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			List<String> urls, long timeOutSec) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values, urls, timeOutSec);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(Class<T> partClass,
			final URL url, long timeOutSec) {
		Class<?>[] params = new Class[] {};
		Object[] values = new Object[] {};
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}

				}, timeOutSec);
	}

	// - with specified frame index
	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, List<String> urls, long timeOutSec) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values, urls, timeOutSec);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(Integer frameIndex,
			Class<T> partClass, final URL url, long timeOutSec) {
		Class<?>[] params = new Class[] { Integer.class };
		Object[] values = new Object[] { frameIndex };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}

				}, timeOutSec);
	}

	// - with specified path to any frame
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, List<String> urls, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values, urls, timeOutSec);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Class<T> partClass, final URL url, long timeOutSec) {
		Class<?>[] params = new Class[] { String.class };
		Object[] values = new Object[] { pathToFrame };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}

				}, timeOutSec);
	}

	// - with specified path to any frame and time out for switching to it
	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, List<String> urls,
			long timeOutSec) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values, urls, timeOutSec);
	}

	@Override
	public <T extends IDecomposable> T getFromNewWindow(String pathToFrame,
			Long timeOutInSec, Class<T> partClass, final URL url,
			long timeOutSec) {
		Class<?>[] params = new Class[] { String.class, Long.class };
		Object[] values = new Object[] { pathToFrame, timeOutInSec };
		return getFromNewWindow(partClass, params, values,
				new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{
						add(url.toString());
					}

				}, timeOutSec);
	}

	// destroys an Entity instance and makes WebDriver quit
	public void quit() {
		destroy();
		nativeSwitcher.destroy();
		driverEncapsulation.destroy();
	}
}
