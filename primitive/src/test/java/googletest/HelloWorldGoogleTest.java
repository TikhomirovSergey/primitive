package googletest;

import junit.framework.Assert;
import googledescripription.AnyPage;
import googledescripription.Google;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class HelloWorldGoogleTest {
	
	
  private void test(Google google)
  {
	  google.performSearch("Hello world. I have just wrote it using Selenium Webdriver");
	  Assert.assertEquals(10, google.getLinkCount());
	  google.clickOn(1);
	  AnyPage anyPage =  google.getFromWinow(AnyPage.class, 1);
	  anyPage.close();
	  
	  google.performSearch("In is Just another attempt to search something");
	  google.quit();
  }
	
  @Test(description = "This is just a test of basic functionality without any configuration")
  public void typeHelloWorldAndOpenTheFirstLink(
		  
		  ) {
	  //Configuration configuration = Configuration.get("safarisetting.xml");
	  //Google google = Google.getNew(Configuration.byDefault);
	  test(Google.getNew());
  }
  
  @Test(description = "This is just a test of basic functionality with specified configurations")
  @Parameters(value={"config"})
  public void typeHelloWorldAndOpenTheFirstLink2(String config) {
	  Configuration configuration = Configuration.get("src/test/resources/configs/" + config); 
	  test(Google.getNew(configuration));
  }
}
