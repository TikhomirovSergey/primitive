package googletest.mobile.browser;

import googledescripription.AnyPage;
import googledescripription.Google;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HelloWorldGoogleMobileTestTest {
	
	
	private void test(Google google) throws Exception {
		try {
			google.performSearch("http://docs.seleniumhq.org/");
			google.clickOn(1);
			AnyPage anyPage = google.getFromWinow(AnyPage.class, 1);
			Thread.sleep(5000);
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

	@Test(description = "This is just a test of basic functionality on Android Chrome using Appium")
	@Parameters(value = {"mobileOS",  "config" })
	public void typeHelloWorldAndOpenTheFirstLinkOnAndroidChrome(
			@Optional("android") String mobileOS,
			@Optional("android_chrome.json") String config) throws Exception {
		launchTest(mobileOS, config);
	}
}
