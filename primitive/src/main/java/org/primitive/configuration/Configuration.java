/**
 * 
 */
package org.primitive.configuration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.openqa.selenium.Capabilities;
import org.primitive.configuration.commonhelpers.BrowserWindowsTimeOuts;
import org.primitive.configuration.commonhelpers.CapabilitySettings;
import org.primitive.configuration.commonhelpers.ChromeDriverExe;
import org.primitive.configuration.commonhelpers.IEDriverExe;
import org.primitive.configuration.commonhelpers.Logging;
import org.primitive.configuration.commonhelpers.PhantomJSDriver;
import org.primitive.configuration.commonhelpers.ScreenShots;
import org.primitive.configuration.commonhelpers.UnhandledWindowsChecking;
import org.primitive.configuration.commonhelpers.WebDriverSettings;
import org.primitive.configuration.commonhelpers.WebDriverTimeOuts;
import org.primitive.configuration.commonhelpers.WebElementVisibility;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//for customizing project
public class Configuration
{
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
	
	
	private final static String commonFileName = "settings.xml"; //settings file should be put in project directory
	public final static Configuration byDefault = get(getPathToDefault("."));
	
	private static final String singleSetting = "setting";
	private static final String settingsGroup = "group";
	private static final String typeTag = "type";
	private static final String valueTag = "value";
		
	//specified settings for capabilities
	private static final String capabilityGroup = "DesiredCapabilities";
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
		
	final HashMap<String, HashMap<String, Object>> mappedSettings = new HashMap<String, HashMap<String, Object>>();
	
	private CapabilitySettings capability;
	
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
		
		EAvailableDataTypes requiredType = null;
		try
		{
			requiredType = EAvailableDataTypes.valueOf(type);
		}
		catch (IllegalArgumentException|NullPointerException e)
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
				return requiredType.getValue(value);
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
	
	//returns HashMap of settings by its group name
	HashMap<String, Object> getGroup(String group)
	{
		return mappedSettings.get(capabilityGroup);
	}
	
	public Capabilities getCapabilities()
	{
		if (capability!=null)
		{
			return capability;
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
		capability   = new CapabilitySettings(this);
		chromeDriver = new ChromeDriverExe(this);
		ieDriver     = new IEDriverExe(this);
		webDriverTimeOuts     = new WebDriverTimeOuts(this);
		screenShots  = new ScreenShots(this);
		logging      = new Logging(this);
		webDriverSettings = new WebDriverSettings(this);
		webElementVisibility = new WebElementVisibility(this);
		windowTimeOuts       = new BrowserWindowsTimeOuts(this);
		unhandledWindowsChecking = new UnhandledWindowsChecking(this);
		phantomJSDriver  = new PhantomJSDriver(this);
	}
}
