package org.primitive.webdriverservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primitive.webdriverservices.interfaces.ILocalServerLauncher;

/**
 * 
 * Binds ports with servers launched locally
 * 
 */
class PortBinder {

	private final static Map<ILocalServerLauncher, Integer> busyPorts = 
			new HashMap<ILocalServerLauncher, Integer>();
	
	static synchronized void setBusyPort(ILocalServerLauncher launcher, int port){
		busyPorts.put(launcher, port);
	}
	
	static synchronized void releasePort(ILocalServerLauncher launcher){
		busyPorts.remove(launcher);
	}
	
	private static List<Integer> getBusyPorts(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		result.addAll(busyPorts.values());
		return result;
	}
	
	static synchronized boolean isBusy(int port){
		List<Integer> ports = getBusyPorts();
		return ports.contains(port);
	}
	
	static synchronized int getMaxBusyPort(){
		List<Integer> ports = getBusyPorts();
		Collections.sort(ports);
		return ports.get(ports.size()-1);		
	}

}

