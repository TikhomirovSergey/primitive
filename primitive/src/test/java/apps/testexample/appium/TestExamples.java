package apps.testexample.appium;

import junit.framework.Assert;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import apps.app.MobileApplicationClient;
import apps.app.android.apidemos.ApiDemos;
import apps.app.android.vk.AndroidVKAuthActivity;
import apps.app.ios.ui_catalog_app.UICatalogItemList;
import apps.app.ios.ui_catalog_app.UINavigationBar;
import apps.app.ios.ui_catalog_app.UIWebView;
public class TestExamples {
	
	/**
	 * For comparison see here: @link{https://github.com/appium/appium/blob/master/sample-code/examples/java/testng/src/test/java/com/saucelabs/appium/AndroidTest.java}
	 */
	/**
	 * JSON setting see here: /primitive/src/test/resources/configs/mobile/android/appium/application/androidApiDemos.json
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
				.get("src/test/resources/configs/mobile/android/appium/application/" + config);
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
	 * JSON setting see here: /primitive/src/test/resources/configs/mobile/android/appium/application/androidVK.json
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
	 * 				   - CPU = Intel Atom(x86)
	 * 				   - Skin = WXGA800
	 * 				   - RAM = 1024M
	 * 				   - VM Heap = 32
	 * 				   - Internal Starage = 200
	 * 				   - Use Host GPU = true
	 */	
	@Test
	public void testVKAuth() throws Exception {
		String config = "androidVKAuth.json";
		MobileApplicationClient client = null;
		try {
			Configuration configuration = Configuration
					.get("src/test/resources/configs/mobile/android/appium/application/"
							+ config);
			client = MobileApplicationClient
					.getNew(configuration);
			AndroidVKAuthActivity authActivity = client
					.getPart(AndroidVKAuthActivity.class);
			
			authActivity.loginClick();
			authActivity.setLogin("Fake login");
			authActivity.setPassword("Fake password");
			authActivity.authClick();			
		} finally {
			if (client!=null){
				client.quit();	
			}
		}
	}
	
	/**
	 * * JSON setting see here: /primitive/src/test/resources/configs/mobile/ios/appium/application/ios_app_local.json
	 *Appium settings: - IP Address = 0.0.0.0
	 * 				   - Port = 4723
	 * 				   - iOS - 	is switched on
	 * 				   - Force Device = yes - iPhone Retina (4-inch)
	 * 				   - Path to {@link}https://github.com/appium/appium/blob/master/assets/UICatalog6.1.app.zip is set
	 * Environment:
	 * 				   - Virtual machine that is made by VMWare.Net bridge
	 * 				   - Mac OS 10.8.5 or newer as a guest OS.
	 * 				   - xCode 4.6.3 or newer is installed
	 * 				   - iPhone Simulator for iOS 6.1 is there
	 *                 - ios-webkit-debug-proxy is installed and run
	 * Test is launched and is run on Mac OS virtual machine (local starting).
	 * I have some problems there. It needs to be run on a real Mac with xCode 5.x.   
	 */
	@Test
	public void testIOSUICatalog() throws Exception {
		String config = "ios_app_local.json";
		MobileApplicationClient client = null;
		try {
			Configuration configuration = Configuration
					.get("src/test/resources/configs/mobile/ios/appium/application/"
							+ config);
			client = MobileApplicationClient
					.getNew(configuration);
			UINavigationBar bar = client.getPart(UINavigationBar.class);
			Assert.assertEquals("UICatalog", bar.getCaption());	
			
			client.getPart(UICatalogItemList.class).clickOnCell("Web");
			Assert.assertEquals("Web", bar.getCaption());	
			
			client.getPart(UIWebView.class).enterUrl("https://www.google.com/");
			bar.back();
			
		} finally {
			if (client!=null){
				client.quit();	
			}
		}
	}
}
