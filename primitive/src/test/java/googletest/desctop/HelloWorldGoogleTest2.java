package googletest.desctop;
import java.net.URL;

import googledescripription.AnyPage;
import googledescripription.Google;
import mocklistener.MockTestListener;

import org.primitive.configuration.Configuration;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.webdrivercomponents.Awaiting;
import org.primitive.webdriverencapsulations.webdrivercomponents.FluentWindowConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(MockTestListener.class)
public class HelloWorldGoogleTest2 {
	
	
	private enum HowToGetANewWindow {
		BYPARTIALTITLE,
		BYPARTIALURL;
		
		private AnyPage get(Google google){
			
			Awaiting awaiting = new Awaiting(google.getWrappedDriver());
			FluentWindowConditions fluentWindowConditions = new FluentWindowConditions(google.getWrappedDriver());
			try {
				URL url = new URL("http://ru.wikipedia.org/wiki");
				switch (this) {
				case BYPARTIALTITLE:
					awaiting.awaitCondition(4, fluentWindowConditions.newWindowIsAppeared("Hello, world"));
					break;
				default:
					awaiting.awaitCondition(4, new FluentWindowConditions(google.getWrappedDriver()).newWindowIsAppeared(url));
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return google.getFromWinow(AnyPage.class, 1);
		}
	}

	private class WaitingThread extends Thread {
        private final Google google;
        private final HowToGetANewWindow howToGet;
        private Exception exception;
        private AnyPage anyPage;
        private boolean isRunning = false;
        
		private WaitingThread(Google google, HowToGetANewWindow howToGet){
			this.google = google;
			this.howToGet = howToGet; 
		}
		
		@Override
		public void run(){
			isRunning = true;
			try	{
				anyPage = howToGet.get(google);
			}
			catch (Exception e){
				exception = e;
				throw e;
			}
			finally{
				isRunning = false;
			}
		}
	}
	
	private void test(Google google, HowToGetANewWindow howToGet) throws Exception {
		WaitingThread waitingThread = new WaitingThread(google, howToGet);
		waitingThread.start();
		Thread.sleep(1000);
		google.clickOn(1);
		while (waitingThread.isRunning){Log.message("Waiting for...");}
		if (waitingThread.exception != null) {
			throw new RuntimeException(waitingThread.exception);
		}
		waitingThread.anyPage.close();
	}
	
	@Test(description = "This is just a test of basic functionality. It gets a new object by its partial title and url")
	@Parameters(value = { "config" })
	public void typeHelloWorldAndOpenTheFirstLink(
			@Optional("chrome.json") String config) throws Exception {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/desctop/" + config);
		Google google = Google.getNew(configuration);
		try {
			google.performSearch("Hello world Wikipedia");
			test(google, HowToGetANewWindow.BYPARTIALTITLE);
			test(google, HowToGetANewWindow.BYPARTIALURL);
		} finally {
			google.quit();
		}
	}
}
