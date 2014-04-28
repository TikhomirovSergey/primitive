package googletest.desctop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.Assert;
import googledescripription.AnyPage;
import googledescripription.Google;
import mocklistener.MockTestListener;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.UnhandledWindowChecker;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import services.MockUnhandledWindowEventListener;
import services.MockWebDriverEventListener2;
import services.MockWebDriverListener;
import services.MockWindowListener;

@Listeners(MockTestListener.class)
public class HelloWorldGoogleTest {
	
	// settings according to current OS
	private final HashMap<Platform, List<String>> settings = new HashMap<Platform, List<String>>();
	
	@BeforeTest
	public void beforeTest() {
		//for Windows
		settings.put(Platform.WINDOWS, new ArrayList<String>(){
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");
				
				add("firefox_remote.json");
				add("firefox.json");
				
				add("internetexplorer_remote.json");
				add("internetexplorer.json");
				
				add("phantomjs_remote.json");
				add("phantomjs.json");
			}
			
		});
		//for MAC
		settings.put(Platform.MAC, new ArrayList<String>(){
			private static final long serialVersionUID = -1718278594717074313L;
			{
				add("chrome_remote.json");
				add("chrome.json");
				
				add("firefox_remote.json");
				add("firefox.json");
				
				add("safari_remote.json");
				add("safari.json");
				
				add("phantomjs_remote.json");
				add("phantomjs.json");
			}
			
		});
		
	}

	List<String> getConfigsByCurrentPlatform(){
		Set<Entry<Platform, List<String>>> entries = settings.entrySet();
		for (Entry<Platform, List<String>> entry: entries){
			if (entry.getKey().is(Platform.getCurrent())){
				return entry.getValue();
			}
		}
		
		return new ArrayList<String>();
	}
	
	private void test(Google google) {
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertEquals(10, google.getLinkCount());
			google.clickOnByMouse(1);
			AnyPage anyPage = google.getFromWindow(AnyPage.class, 1);
			anyPage.close();
			google.clickOnByMouse(1);
			anyPage = google.getFromWindow(AnyPage.class, 1);
		} finally {
			google.quit();
		}
	}

	private void test2(Google google) {
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertEquals(10, google.getLinkCount());
			google.close();
		} finally {
			google.quit();
		}
	}

	private void test3(Google google) {
		try {
			google.performSearch("Hello world Wikipedia");
			Assert.assertEquals(10, google.getLinkCount());
			google.clickOnByMouse(1);
			UnhandledWindowChecker.getChecker(google.getSwitcher())
					.killUnexpectedWindows();
			Assert.assertEquals(true, MockUnhandledWindowEventListener.wasInvoked);
		} finally {
			google.quit();
		}
	}
	
	@Test(description = "This is just a test of basic functionality without any configuration")
	public void typeHelloWorldAndOpenTheFirstLink() {
		test(Google.getNew());
	}

	@Test(description = "This is just a test of basic functionality with specified configurations")
	public void typeHelloWorldAndOpenTheFirstLink2() {
		List<String> configs = getConfigsByCurrentPlatform();
		for (String config: configs){
			Configuration configuration = Configuration
					.get("src/test/resources/configs/desctop/" + config);
			test(Google.getNew(configuration));		
		}
	}

	@Test(description = "This is just a test of basic functionality with a webdriver instance that was created externally")
	public void typeHelloWorldAndOpenTheFirstLink3() {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/desctop/firefox.json");
		WebDriverEncapsulation encapsulation = new WebDriverEncapsulation(
				new FirefoxDriver(), configuration);
		test(Google.getNew(encapsulation));
	}

	@Test(description = "This is just a test of basic functionality. It performs search and closes google as visible browser window")
	public void typeHelloWorldAndOpenTheFirstLink4() {
		List<String> configs = getConfigsByCurrentPlatform();
		for (String config: configs){
			Configuration configuration = Configuration
					.get("src/test/resources/configs/desctop/" + config);
			test2(Google.getNew(configuration));
		}
	}

	@Test(description = "This is just a test of basic functionality. Checks possibility of service provider working")
	public void typeHelloWorldAndOpenTheFirstLink5() {
		MockWebDriverListener.listener = null;
		MockWindowListener.listener = null;
		MockWebDriverListener.wasInvoked = false;
		MockWindowListener.wasInvoked = false;
		MockWebDriverEventListener2.wasInvoked = false;
		MockWebDriverEventListener2.wasInvoked = false;
		try {
			test(Google.getNew());
		} catch (Exception e) {
		}
		Assert.assertEquals(true, MockWebDriverListener.wasInvoked);
		Assert.assertEquals(true, MockWindowListener.wasInvoked);
		Assert.assertEquals(true, MockWebDriverEventListener2.wasInvoked);
	}

	@Test(description = "This is just a test of basic functionality. It watches how UnhandledWindowChecker kills windows that exist but weren't instantiated as objects of SingleWindow")
	public void typeHelloWorldAndOpenTheFirstLink6() {
		List<String> configs = getConfigsByCurrentPlatform();
		for (String config: configs){
			Configuration configuration = Configuration
					.get("src/test/resources/configs/desctop/" + config);
			test3(Google.getNew(configuration));
		}
	}

}
