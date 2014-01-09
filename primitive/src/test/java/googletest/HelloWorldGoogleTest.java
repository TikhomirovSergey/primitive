package googletest;

import junit.framework.Assert;
import googledescripription.AnyPage;
import googledescripription.Google;
import mocklistener.MockTestListener;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.primitive.configuration.Configuration;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Listeners(MockTestListener.class)
public class HelloWorldGoogleTest {
	
	
  private void test(Google google)
  {
	  try
	  {
		  google.performSearch("Hello world Wikipedia");
		  Assert.assertEquals(10, google.getLinkCount());
		  google.clickOn(1);
		  AnyPage anyPage =  google.getFromWinow(AnyPage.class, 1);
		  anyPage.close();
		  google.clickOn(1);
		  anyPage =  google.getFromWinow(AnyPage.class, 1);
	  }
	  finally
	  {
		  google.quit();
	  }	  
  }
  
  private void test2(Google google)
  {
	  try
	  {
		  google.performSearch("Hello world Wikipedia");
		  Assert.assertEquals(10, google.getLinkCount());
		  google.close();
	  }
	  finally
	  {
		  google.quit();
	  }	  
  }
	
  @Test(description = "This is just a test of basic functionality without any configuration")
  public void typeHelloWorldAndOpenTheFirstLink() {
	  test(Google.getNew());
  }
  
  @Test(description = "This is just a test of basic functionality with specified configurations")
  @Parameters(value={"config"})
  public void typeHelloWorldAndOpenTheFirstLink2(String config) {
	  Configuration configuration = Configuration.get("src/test/resources/configs/" + config); 
	  test(Google.getNew(configuration));
  }
  
  @Test(description = "This is just a test of basic functionality with a webdriver instance that was created externally")
  public void typeHelloWorldAndOpenTheFirstLink3()
  {
	  Configuration configuration = Configuration.get("src/test/resources/configs/firefox.json"); 
	  WebDriverEncapsulation encapsulation = new WebDriverEncapsulation(new FirefoxDriver(), configuration);
	  test(Google.getNew(encapsulation));
  }
  
  @Test(description = "This is just a test of basic functionality. It performs search and closes google as visible browser window")
  @Parameters(value={"config"})
  public void typeHelloWorldAndOpenTheFirstLink4(String config) {
	  Configuration configuration = Configuration.get("src/test/resources/configs/" + config); 
	  test2(Google.getNew(configuration));
  }
}
