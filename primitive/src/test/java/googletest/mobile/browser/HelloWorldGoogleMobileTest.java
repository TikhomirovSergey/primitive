package googletest.mobile.browser;

import googledescripription.AnyPage;
import googledescripription.Google;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HelloWorldGoogleMobileTest {
	
	
	private void test(Google google) throws Exception {
		try {
			google.performSearch("Hello world Wikipedia");
			google.clickOn(1);
			AnyPage anyPage = google.getFromWinow(AnyPage.class, 1);
			anyPage.close();
			google.clickOn(1);
			anyPage = google.getFromWinow(AnyPage.class, 1);
		} finally {
			google.quit();
		}
	}
	
	private void launchTest(String mobileOS, String config) throws Exception{
		Configuration configuration = Configuration
				.get("src/test/resources/configs/mobile/" + mobileOS + "/browser/" + config);
		test(Google.getNew(configuration));	
	}

	/**
	 * JSON setting see here: /primitive/src/test/resources/configs/mobile/android/browser/android_chrome.json
	 * Appium settings: - IP Address = 127.0.0.1
	 * 				   - Port = 4723
	 * 				   - Use remote server = yes
	 * 				   - Launch AVD = {avd name}
	 * 				   - Device ready timeout = 40 sec
	 * 				   - Perform full reset = yes
	 * 
	 * AVD parameters:
	 * 				   - Device = Galaxy Nexus 4'65
	 * 				   - Target = Android 4.3 API Level 18
	 * 				   - CPU = ARM
	 * 				   - Skin = WXGA800
	 * 				   - RAM = 1024M
	 * 				   - VM Heap = 32
	 * 				   - Internal Starage = 200
	 * 				   - Use Host GPU = true
	 * Path to chromedriver.exe should be set to PATH environment variable 
	 */
	@Test(description = "This is just a test of basic functionality on Android Chrome using Appium")
	@Parameters(value = {"mobileOS",  "config" })
	public void typeHelloWorldAndOpenTheFirstLinkOnAndroidChrome(
			@Optional("android") String mobileOS,
			@Optional("android_chrome.json") String config) throws Exception {
		launchTest(mobileOS, config);
	}
}
