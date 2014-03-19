package apps.testexample;

import junit.framework.Assert;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import apps.app.MobileApplicationClient;
import apps.app.android.ApiDemos;
public class TestExamples {
	
	/**
	 * For comparison see here: @link{https://github.com/appium/appium/blob/master/sample-code/examples/java/testng/src/test/java/com/saucelabs/appium/AndroidTest.java}
	 */
	
	@Test
	@Parameters(value = { "config" })
	public void testOnAndroidApiDemos(
			@Optional("androidApp.json") String config) {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/mobile/android/application/" + config);
		MobileApplicationClient client = MobileApplicationClient
				.getNew(configuration);

		try {
			ApiDemos apiDemos = client.getPart(ApiDemos.class);
	        Assert.assertEquals("App", apiDemos.getViewTextByIndex(1));
	        Assert.assertEquals("Content", apiDemos.getViewTextByIndex(2));
	        Assert.assertEquals("Graphics", apiDemos.getViewTextByIndex(3));
	        //TODO make it more complex
		} finally {
			client.quit();
		}

	}
}
