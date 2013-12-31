/**
 * 
 */
package org.primitive.configuration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//for customizing project
public class Configuration
{
	public class PhantomJSDriver implements FileSystemProperty, GroupedSetting {
		//getters for phantomjs.exe
		private static final String phantomJSDriverGroup = "PhantomJSDriver"; 
				
		private PhantomJSDriver() 
		{
			super();
		}		
		
		@Override
		public String getFolder() 
		{
			return (String) getSettingValue(phantomJSDriverGroup, folderSettingName);
		}

		@Override
		public String getFile() 
		{
			return (String) getSettingValue(phantomJSDriverGroup, fileSettingName);
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(phantomJSDriverGroup, name);
		}

	}

	/**
	 * @author s.tihomirov
	 *
	 */
	private interface GroupedSetting {
		public Object getSetting(String name);
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	private interface TimeUnitSetting {
		public static final String timeUnitSetting = "timeUnit";
		
		public TimeUnit getTimeUnit();
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public class UnhandledWindowsChecking implements TimeUnitSetting, GroupedSetting {
		private final static String sessionTimeSetting = "sessionTime";
		private final static String timeForWaitingSetting = "timeForWaiting";
		private final static String secsForAwaitinAlertPresentSetting = "secsForAwaitinAlertPresent";
		
		private UnhandledWindowsChecking() {
			super();
		}
		
		@Override
		public TimeUnit getTimeUnit() {
			String timeUnitStr = (String) getSettingValue(unhandledWindowsCheckingGroup, timeUnitSetting);
			if (timeUnitStr != null)
			{
				return TimeUnit.valueOf(timeUnitStr.toUpperCase());
			}
			else
			{
				return null;
			}
		}
		
		public Long getSessionTime()
		{
			return (Long) getSettingValue(unhandledWindowsCheckingGroup, sessionTimeSetting);
		}
		
		public Long getTimeForWaiting()
		{
			return (Long) getSettingValue(unhandledWindowsCheckingGroup, timeForWaitingSetting);
		}
		
		public Long getSecsForAwaitinAlertPresent()
		{
			return (Long) getSettingValue(unhandledWindowsCheckingGroup, secsForAwaitinAlertPresentSetting);
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(unhandledWindowsCheckingGroup,  name);
		}
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public class BrowserWindowsTimeOuts implements GroupedSetting {

		private static final String newWindowTimeOutSetting = "newBrowserWindowTimeOutSec";
		private static final String windowCountTimeOutSetting = "browserWindowCountTimeOutSec";
		private static final String windowClosingTimeOutSetting = "browserWindowClosingTimeOutSec";
		

		private BrowserWindowsTimeOuts() {
			super();
		}
		
		public Long getNewBrowserWindowTimeOutSec()
		{
			return (Long) getSettingValue(browserWindowsTimeOutsGroup, newWindowTimeOutSetting);			
		}
		
		public Long getWindowCountTimeOutSec()
		{
			return (Long) getSettingValue(browserWindowsTimeOutsGroup, windowCountTimeOutSetting);			
		}
		
		public Long getWindowClosingTimeOutSec()
		{
			return (Long) getSettingValue(browserWindowsTimeOutsGroup, windowClosingTimeOutSetting);			
		}		

		public Object getSetting(String name)
		{
			return getSettingValue(browserWindowsTimeOutsGroup, name);
		}
		
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public class WebElementVisibility implements GroupedSetting{

		private static final String visibilityTimeOutSetting = "visibilityTimeOutSec";

		private WebElementVisibility() {
			super();
		}
		
		public Long getVisibilityTimeOutSec()
		{
			return (Long) getSettingValue(webElementVisibilityGroup, visibilityTimeOutSetting);			
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(webElementVisibilityGroup, name);
		}	
	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public class WebDriverSettings implements GroupedSetting {
		private final static String webDriverName = "driverName";
		private final static String remoteAddress = "remoteAdress";
		private WebDriverSettings() {
			super();
		}
		
		public ESupportedDrivers getSupoortedWebDriver()
		{
			String name = (String) getSettingValue(webDriverGroup, webDriverName);
			if (name!=null)
			{
				return ESupportedDrivers.parse(name);
			}
			else
			{
				return null;
			}
		}
		
		public String getRemoteAddress()
		{
			return (String) getSettingValue(webDriverGroup, remoteAddress);
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(webDriverGroup, name);
		}	

	}


	/**
	 * @author s.tihomirov
	 *
	 */
	private static class getterInterceptor implements MethodInterceptor {

		/**
		 * it Intercepts getter methods and redirects its invocation to default configuration when value is null
		 */
		@Override
		public Object intercept(Object proxy, Method method, Object[] args,
				MethodProxy methodProxy) throws Throwable {
			Object result = methodProxy.invokeSuper(proxy, args);
			if ((result==null)&(proxy!=(byDefault)&(byDefault!=null)))
			{
				result = methodProxy.invokeSuper(byDefault, args);
			}
			return result;
		}
	}
	
	
	/**
	 * @author s.tihomirov
	 *
	 */
	public class Logging implements GroupedSetting{
		private static final String levelSetting = "Level";
		
		private Logging() {
			super();
		}
		
		public Level getLevel()
		{
			String levelName = (String) getSettingValue(loggingGroup, levelSetting);
			if (levelName!=null)
			{	
				return Level.parse(levelName.toUpperCase());
			}
			else
			{
				return null;
			}
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(loggingGroup, name);
		}		

	}	

	/**
	 * @author s.tihomirov
	 *
	 */
	public class ScreenShots implements FileSystemProperty, GroupedSetting{
		private static final String toDoScreenShotsOnElementHighLighting = "toDoScreenShotsOnElementHighLighting";
		
		private ScreenShots() {
			super();
		}
		
		@Override
		public String getFolder()
		{
			return (String) getSettingValue(screenShotssGroup, folderSettingName);
		}

		@Override
		public String getFile() {
			return (String) getSettingValue(screenShotssGroup, fileSettingName);
		}
		
		public Boolean getToDoScreenShotsOnElementHighLighting()
		{
			return (Boolean) getSettingValue(screenShotssGroup, toDoScreenShotsOnElementHighLighting);
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(screenShotssGroup, name);
		}			

	}

	/**
	 * @author s.tihomirov
	 *
	 */
	public class WebDriverTimeOuts implements TimeUnitSetting, GroupedSetting{		
		private static final String implicitlyWaitTimeOutSetting = "implicitlyWait";
		private static final String scriptTimeOutSetting = "setScriptTimeout";
		private static final String pageLoadTimeoutSetting = "pageLoadTimeout";
		/**
		 * Groups specified time outs
		 */
		private WebDriverTimeOuts() {
			super();
		}
		
		public TimeUnit getTimeUnit()
		{
			String timeUnitStr = (String) getSettingValue(webDriverTimeOutsGroup, timeUnitSetting);
			if (timeUnitStr != null)
			{
				return TimeUnit.valueOf(timeUnitStr.toUpperCase());
			}
			else
			{
				return null;
			}
		}
		
		public Long getImplicitlyWaitTimeOut()
		{
			return (Long) getSettingValue(webDriverTimeOutsGroup, implicitlyWaitTimeOutSetting);
		}
		
		public Long getScriptTimeOut()
		{
			return (Long) getSettingValue(webDriverTimeOutsGroup, scriptTimeOutSetting);
		}
		
		public Long getLoadTimeout()
		{
			return (Long) getSettingValue(webDriverTimeOutsGroup, pageLoadTimeoutSetting);
		}	
		
		public Object getSetting(String name)
		{
			return  getSettingValue(webDriverTimeOutsGroup, name);
		}			

	}

	public class IEDriverExe implements FileSystemProperty, GroupedSetting {
		//getters for chromeDriver.exe
		private static final String ieDriverGroup = "IEDriver"; 
						
		private IEDriverExe() 
		{
			super();
		}		
		
		@Override
		public String getFolder() 
		{
			return (String) getSettingValue(ieDriverGroup, folderSettingName);
		}

		@Override
		public String getFile() 
		{
			return (String) getSettingValue(ieDriverGroup, fileSettingName);
		}
		
		public Object getSetting(String name)
		{
			return  getSettingValue(ieDriverGroup, name);
		}		

	}

	public interface FileSystemProperty {
		
		public String getFolder();
		public String getFile();
	}

	public class ChromeDriverExe implements FileSystemProperty,GroupedSetting {
		//getters for chromeDriver.exe
		private static final String chromeDriverGroup = "ChromeDriver"; 
				
		private ChromeDriverExe() 
		{
			super();
		}

		@Override
		public String getFolder() 
		{
			return (String) getSettingValue(chromeDriverGroup, folderSettingName);
		}

		@Override
		public String getFile() 
		{
			return (String) getSettingValue(chromeDriverGroup, fileSettingName);
		}
		
		public Object getSetting(String name)
		{
			return getSettingValue(chromeDriverGroup, name);
		}

	}

	private enum EAvailableDataTypes {
		STRING, BOOL, LONG, FLOAT, INT;
	}
	
	private final static String commonFileName = "settings.xml"; //settings file should be put in project directory
	public final static Configuration byDefault = get(getPathToDefault("."));
	
	private static final String singleSetting = "setting";
	private static final String settingsGroup = "group";
	private static final String typeTag = "type";
	private static final String valueTag = "value";
	
	private final static String webDriverGroup		        = "webdriver";
	
	//specified settings for capabilities
	private static final String capabilityGroup = "DesiredCapabilities";
	private static final String browserNameCapabity = "browserName";
	private static final String platformNameCapabity = "platform";
	private static final String versionNameCapabity  = "version";	
	
	private final static String webDriverTimeOutsGroup 	    = "webDriverTimeOuts";
	private final static String webElementVisibilityGroup   = "webElementVisibilityTimeOut";
	private final static String browserWindowsTimeOutsGroup = "browserWindowsTimeOuts";

	private final static String unhandledWindowsCheckingGroup= "unhandledWindowsChecking"; 
	
	//spicified settings for *Driver.exe
	private static final String folderSettingName = "folder";
	private static final String fileSettingName   = "file";
	
	//screenshot group
	private static final String screenShotssGroup = "screenShots";
	
	//Logging group
	private static final String loggingGroup = "Log";
	
	//settings for chrome driver exe
	private ChromeDriverExe chromeDriver;
	
	//settings for ie driver exe
	private IEDriverExe ieDriver;
	
	//settings for phantomjs.exe	
	private PhantomJSDriver phantomJSDriver;
	
	//settings for time outs
	private WebDriverTimeOuts webDriverTimeOuts;
	private WebElementVisibility webElementVisibility;
	private BrowserWindowsTimeOuts windowTimeOuts;
	
	private ScreenShots screenShots;
		
	private final HashMap<String, HashMap<String, Object>> mappedSettings = new HashMap<String, HashMap<String, Object>>();
	
	private DesiredCapabilities capability;
	
	private Logging logging;
	
	private WebDriverSettings webDriverSettings;
	//unhandled windows and alerts group
	private UnhandledWindowsChecking unhandledWindowsChecking;
	private static String getPathToDefault(String startPath)
	{
		//attempt to find configuration in the specified directory
		File defaultConfig = new File(startPath);
		File list[] = defaultConfig.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(commonFileName);
			}
			
		});
		
		if (list.length>0)
		{
			return list[0].getPath();
		}
		
		if (list.length == 0)
		{
			File inner[] = defaultConfig.listFiles();
			String result = null;
			for (int i=0; i<inner.length; i++)
			{
				if (inner[i].isDirectory())
				{
					result = getPathToDefault(inner[i].getPath());
				}
				if (result!=null)
				{
					return result;
				}
			}
		}
		return null;
	}
	
	
	public static Configuration get(String filePath)
	{
		Callback interceptor = new getterInterceptor();
		
		Enhancer enhancer = new Enhancer();
	    enhancer.setCallback(interceptor);
	    enhancer.setSuperclass(Configuration.class);
	    
	    return  (Configuration) enhancer.create(new Class[] {String.class}, new Object[] {filePath});
	}
	
	private Object returnSettingValue(Element settingElement) throws ParserConfigurationException
	{
		NodeList typeNodes = settingElement.getElementsByTagName(typeTag);
		if (typeNodes.getLength()==0)
		{
			throw new ParserConfigurationException("There is not any node that specifies data type! Setting name is " + settingElement.getAttribute("name"));
		}
		Node typeNode = typeNodes.item(0);
		
		NodeList valueNodes = settingElement.getElementsByTagName(valueTag);
		if (valueNodes.getLength()==0)
		{
			throw new ParserConfigurationException("There is not any node that specifies value! Setting name is " + settingElement.getAttribute("name"));
		}
		Node valueNode = valueNodes.item(0);
		
		String type = typeNode.getChildNodes().item(0).getNodeValue();
		type = type.toUpperCase();
		type = type.trim(); 
		
		if ((!type.equals(EAvailableDataTypes.STRING.toString()))&
				(!type.equals(EAvailableDataTypes.BOOL.toString()))&
				(!type.equals(EAvailableDataTypes.LONG.toString()))&
				(!type.equals(EAvailableDataTypes.FLOAT.toString()))&
				(!type.equals(EAvailableDataTypes.INT.toString())))
		{
			throw new ParserConfigurationException("Type specification that is not supported! Specification is " + type + ". " +
					" STRING, BOOL, LONG, FLOAT, INT are suppurted. Setting name is " + settingElement.getAttribute("name"));
		}		
		
		Object returnValue = null;
		if (valueNode.getChildNodes().getLength()==0)
		{
			return null;
		}
		
		String value = valueNode.getChildNodes().item(0).getNodeValue();
		value = value.trim();
	
		if (value.equals(""))
		{
			return returnValue;
		}
		else
		{
			try
			{
				if (type.equals(EAvailableDataTypes.STRING.toString()))
				{
					returnValue = new String(value);
				}
				if (type.equals(EAvailableDataTypes.BOOL.toString()))
				{
					returnValue = new Boolean(value);
				}
				if (type.equals(EAvailableDataTypes.LONG.toString()))
				{
					returnValue = new Long(value);
				}
				if (type.equals(EAvailableDataTypes.FLOAT.toString()))
				{
					returnValue = new Float(value);
				}
				if (type.equals(EAvailableDataTypes.INT.toString()))
				{
					returnValue = new Integer(value);
				}
				return returnValue;
			}
			catch (Exception e)
			{
				throw new ParserConfigurationException("Value cann't be converted! Value is " + value + ". Setting name is " + settingElement.getAttribute("name"));
			}
		}
	}
	
	private void buildSettingGroup(Node groupNode) throws ParserConfigurationException
	{
		Element groupElem = (Element) groupNode;
		String name = groupElem.getAttribute("name");
		name = name.trim();
		
		HashMap<String, Object> settings = mappedSettings.get(name);
		if (settings==null) //if settings have not created yet
		{
			settings = new HashMap<String, Object>();
		}
		
		NodeList settingNodes = groupElem.getElementsByTagName(singleSetting);
		int settingCount = settingNodes.getLength();
		
		for (int i=0; i<settingCount; i++){
			
			Element settingElem = (Element) settingNodes.item(i);
			String settingName  = settingElem.getAttribute("name");
			settingName = settingName.trim();
			try
			{
				Object value = returnSettingValue(settingElem);
				settings.put(settingName, value);
			}
			catch (ParserConfigurationException e)
			{
				throw e;
			}
		}
		mappedSettings.put(name, settings);		
	}
	
	private void parseSettings(String filePath)
	{
		
		File settingFile = new File(filePath);
		if (settingFile.exists())
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder;
			
	        Document doc = null;
	        try 
			{
				docBuilder = docBuilderFactory.newDocumentBuilder();
				doc = docBuilder.parse(settingFile);
			} catch (ParserConfigurationException | SAXException  e) {
				new RuntimeException("Cann't parse file " + commonFileName + "! Please, check it. You can look at SAMPLE_SETTING.xml for verifying. " + e.getLocalizedMessage(),e);
			}
			catch (IOException e) 
			{
				new RuntimeException("IO Exception: " + e.getLocalizedMessage(),e);
			}
			
			doc.getDocumentElement().normalize();
			
			NodeList nodes = doc.getElementsByTagName(settingsGroup);
			int nodeCount  = nodes.getLength();
			
			try
			{
				for (int i=0; i<nodeCount; i++){
					buildSettingGroup(nodes.item(i));
				}
			}
			catch (ParserConfigurationException e)
			{
				new RuntimeException("Configuration building has failed! " + e.getLocalizedMessage(),e);
			}

		}		
	}
	
	private void buildCapability()
	{
		HashMap<String,Object> capabilities = mappedSettings.get(capabilityGroup);
		if (capabilities!=null)
		{
			DesiredCapabilities customizedCapability = new DesiredCapabilities();
			
			List<String> capabilityStrings = new ArrayList<String>(capabilities.keySet());
			
			for (String capabilityStr: capabilityStrings)
			{	
				if (capabilities.get(capabilityStr)!=null)
				{	
					//set browser name
					if (capabilityStr.equals(browserNameCapabity))
					{
						customizedCapability.setBrowserName((String) capabilities.get(capabilityStr));
					} //set version
					else if (capabilityStr.equals(versionNameCapabity)) 
					{
						customizedCapability.setVersion((String) capabilities.get(capabilityStr));
					} //set platform
					else if (capabilityStr.equals(platformNameCapabity)) 
					{
						customizedCapability.setPlatform(Platform.valueOf((String) capabilities.get(capabilityStr)));
					}
					else
					{	
						customizedCapability.setCapability(capabilityStr, capabilities.get(capabilityStr));
					}
				}
			}
			capability = customizedCapability;
		}
	}
	
	//gets setting group from mapped serrings
	protected HashMap<String, Object> getSettingGroup(String groupName)
	{
		return mappedSettings.get(groupName);
	}
	
	public Object getSettingValue(String groupName, String settingName)
	{
		HashMap<String, Object> group = getSettingGroup(groupName);
		//if there is no group with specified name
		if (group == null)
		{
			return null;
		}
		else
		{
			return group.get(settingName);
		}
	}
	
	public DesiredCapabilities getCapabilities()
	{
		if (capability!=null)
		{
			return capability;
		}
		ESupportedDrivers supportedDriver = webDriverSettings.getSupoortedWebDriver();
		if (supportedDriver!=null)
		{
			return (DesiredCapabilities) supportedDriver.getDefaultCapabilities();
		}
		return null;
	}

	public ChromeDriverExe getChromeDriverSettings()
	{
		return chromeDriver;
	}
	
	public IEDriverExe getIEDriverSettings()
	{
		return ieDriver;
	}
	
	public PhantomJSDriver getPhantomJSDriverSettings()
	{
		return phantomJSDriver;
	}
	
	public WebDriverTimeOuts getWebDriverTimeOuts()
	{
		return webDriverTimeOuts;
	}
	
	public ScreenShots getScreenShots()
	{
		return screenShots;
	}
	
	public Logging getLogging()
	{
		return logging;
	}
	
	public WebDriverSettings getWebDriverSettings() 
	{
		return webDriverSettings;
	}
	
	public WebElementVisibility getWebElementVisibility()
	{
		return webElementVisibility;
	}
	
	public BrowserWindowsTimeOuts getBrowserWindowsTimeOuts()
	{
		return windowTimeOuts;
	}
	
	public UnhandledWindowsChecking getUnhandledWindowsChecking()
	{
		return unhandledWindowsChecking;
	}
	
	protected Configuration(String filePath)
	{
		parseSettings(String.valueOf(filePath));
		buildCapability();
		chromeDriver = new ChromeDriverExe();
		ieDriver     = new IEDriverExe();
		webDriverTimeOuts     = new WebDriverTimeOuts();
		screenShots  = new ScreenShots();
		logging      = new Logging();
		webDriverSettings = new WebDriverSettings();
		webElementVisibility = new WebElementVisibility();
		windowTimeOuts       = new BrowserWindowsTimeOuts();
		unhandledWindowsChecking = new UnhandledWindowsChecking();
		phantomJSDriver  = new PhantomJSDriver();
	}
}
