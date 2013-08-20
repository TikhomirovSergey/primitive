package org.primitive.interfaces;

/**
 * @author s.tihomirov
 *
 */
public interface ISingleBrowserWindow {
	public String getWindowHandle();
	
	public String getCurrentUrl();
	
	public String getTitle();
}
