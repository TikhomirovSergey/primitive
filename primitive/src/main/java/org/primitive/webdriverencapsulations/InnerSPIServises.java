package org.primitive.webdriverencapsulations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.primitive.webdriverencapsulations.eventlisteners.DefaultUnhandledWindowEventListener;
import org.primitive.webdriverencapsulations.eventlisteners.DefaultWebdriverListener;
import org.primitive.webdriverencapsulations.eventlisteners.DefaultWindowListener;
import org.primitive.webdriverencapsulations.eventlisteners.IExtendedWebDriverEventListener;
import org.primitive.webdriverencapsulations.eventlisteners.IUnhandledWindowEventListener;
import org.primitive.webdriverencapsulations.eventlisteners.IWindowListener;

/**
 * @author s.tihomirov Creates implementations of
 *         {@link IExtendedWebDriverEventListener}, {@link IWindowListener}, {@link IExtendedWebDriverEventListener} and
 *         another interfaces that can be made up here. This implementations
 *         should have default constructor (!!!!) They can be loaded using SPI
 *         mechanism.
 */
class InnerSPIServises {
	private final static Map<WebDriverEncapsulation, InnerSPIServises> initedServises = Collections
			.synchronizedMap(new HashMap<WebDriverEncapsulation, InnerSPIServises>());
	
	private final HashMap<Class<?>, Object> defaultProvidedServices = new HashMap<Class<?>, Object>();
	private final HashMap<Class<?>, List<Object>> providedServices = new HashMap<Class<?>, List<Object>>();

	private final Class<?> webdriverExtendedListener    = IExtendedWebDriverEventListener.class;
	private final Class<?> windowEventListener          = IWindowListener.class;
	private final Class<?> unhandledWindowEventListener = IUnhandledWindowEventListener.class;

	private InnerSPIServises() {
		defaultProvidedServices.put(webdriverExtendedListener,
				new DefaultWebdriverListener());
		defaultProvidedServices.put(windowEventListener,
				new DefaultWindowListener());
		defaultProvidedServices.put(unhandledWindowEventListener, 
				new  DefaultUnhandledWindowEventListener());
	}
	
	/**
	 * @author s.tihomirov
	 * gets instantiated services by {@link WebDriverEncapsulation} object that defined as key
	 */
	static InnerSPIServises getBy(WebDriverEncapsulation encapsulation) {
		InnerSPIServises result = initedServises.get(encapsulation);
		if (result!=null){
			return result;
		}
		result = new InnerSPIServises();
		initedServises.put(encapsulation, result);
		return result;
	}
	
	/**
	 * @author s.tihomirov
	 * remoses instantiated services by {@link WebDriverEncapsulation} object that defined as key
	 */
	static void removeBy(WebDriverEncapsulation encapsulation){
		initedServises.remove(encapsulation);
	}

	@SuppressWarnings("unchecked")
	/**
	 * @author s.tihomirov
	 * Initiates services by their class or gets them if they are initiated
	 */
	<T> List<T> getServices(Class<T> requiredService) {
		List<T> initiatedServices = (List<T>) providedServices
				.get(requiredService);
		if (initiatedServices != null) {
			return initiatedServices;
		}

		List<T> serviceList = prepareServices(requiredService);
		T defaultService = (T) getDafaultService(requiredService);
		if (defaultService != null) {
			serviceList.add(defaultService);
		}
		return serviceList;
	}

	/**
	 * @author s.tihomirov gets default service if it is initiated
	 */
	@SuppressWarnings("unchecked") <T> T getDafaultService(Class<T> requiredService) {
		return (T) defaultProvidedServices.get(requiredService);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> prepareServices(Class<?> requiredService) {
		List<T> loadedServices = new ArrayList<T>();
		Iterator<?> providers = ServiceLoader.load(requiredService).iterator();
		while (providers.hasNext()) {
			loadedServices.add((T) providers.next());
		}
		return loadedServices;
	}

}
