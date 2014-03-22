package apps.testexample;

import junit.framework.Assert;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import apps.app.MobileApplicationClient;
import apps.app.android.apidemos.ApiDemos;
import apps.app.android.vk.AndroidVKAuthActivity;
import apps.app.android.vk.ErrorMsg;
public class TestExamples {
	
	/**
	 * For comparison see here: @link{https://github.com/appium/appium/blob/master/sample-code/examples/java/testng/src/test/java/com/saucelabs/appium/AndroidTest.java}
	 */
	/**
	 * JSON setting see here: /primitive/src/test/resources/configs/mobile/android/application/androidApiDemos.json
	 * Appium settings: - IP Address = 127.0.0.1
	 * 			       - Application Path = path to apiDemos.apk
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
	 */
	@Test
	@Parameters(value = { "config" })
	public void testOnAndroidApiDemos(
			@Optional("androidApiDemos.json") String config) {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/mobile/android/application/" + config);
		MobileApplicationClient client = MobileApplicationClient
				.getNew(configuration);

		try {
			ApiDemos apiDemos = client.getPart(ApiDemos.class);
	        Assert.assertEquals("App", apiDemos.getViewTextByIndex(1));
	        Assert.assertEquals("Content", apiDemos.getViewTextByIndex(2));
	        Assert.assertEquals("Graphics", apiDemos.getViewTextByIndex(3));
	        
	        apiDemos.clickOnTextView(1);
	        
	        Assert.assertEquals("Activity", apiDemos.getViewTextByIndex(1));
	        Assert.assertEquals("Alarm", apiDemos.getViewTextByIndex(2));
	        Assert.assertEquals("Device Admin", apiDemos.getViewTextByIndex(3));
	        Assert.assertEquals("Dialog", apiDemos.getViewTextByIndex(4));
		} finally {
			client.quit();
		}
	}
	
	/**
	 * JSON setting see here: /primitive/src/test/resources/configs/mobile/android/application/androidVK.json
	 * Appium settings: - IP Address = 127.0.0.1
	 * 			       - Application Path = path to com.vkontakte.android.apk 
	 * 					{@link https://play.google.com/store/apps/details?id=com.vkontakte.android}
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
	 */	
	@Test
	@Parameters(value = { "config" })
	public void testVKInvalidAuth(@Optional("androidVK.json") String config) {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/mobile/android/application/"
						+ config);
		MobileApplicationClient client = MobileApplicationClient
				.getNew(configuration);

		try {
			AndroidVKAuthActivity authActivity = client
					.getPart(AndroidVKAuthActivity.class);
			authActivity.loginClick();
			authActivity.setLogin("Fake login");
			authActivity.setPassword("Fake password");
			authActivity.authClick();
			
			@SuppressWarnings("unused")
			ErrorMsg errorMsg = authActivity.getErrorMsg();
			//TODO I cann't get an element for now. I think there is a "frame"
			//I'll fix this example later.
			//errorMsg.getText();
		} finally {
			client.quit();
		}
	}
}
