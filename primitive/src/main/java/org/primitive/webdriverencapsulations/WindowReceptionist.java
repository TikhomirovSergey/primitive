package org.primitive.webdriverencapsulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;

/**@author s.tihomirov
 * it registers window handles that are instantiated as {@link IExtendedWindow}
 *
 */
class WindowReceptionist {

	final private Map<String,IExtendedWindow> openedWindows = Collections.synchronizedMap(new HashMap<String, IExtendedWindow>());

	/**adds a new handle that is instantiated as {@link IExtendedWindow} object**/
	void addKnownWindow(IExtendedWindow window){
		openedWindows.put(window.getWindowHandle(), window);
	}

	/**is window handle known as instance of {@link IExtendedWindow}**/
	IExtendedWindow isInstantiated(String handle)	{
		return openedWindows.get(handle);
	}

	/**removes handle that is instantiated as {@link IExtendedWindow} object**/
	void removeWindow(IExtendedWindow window){
		openedWindows.remove(window.getWindowHandle());
	}
	
	/**gets {@link IExtendedWindow} objects**/
	List<IExtendedWindow> getInstantiatedWindows(){
		return new ArrayList<>(openedWindows.values());
	}
	
	/**gets known handles**/
	List<String> getKnownHandles(){
		return new ArrayList<>(openedWindows.keySet());
	}
	
}
