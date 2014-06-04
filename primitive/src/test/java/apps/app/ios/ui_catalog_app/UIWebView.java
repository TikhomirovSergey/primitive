package apps.app.ios.ui_catalog_app;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class UIWebView extends FunctionalPart {

	@FindBy(name = "URL entry")
	private WebElement urlEntry;
	
	public UIWebView(SingleWindow browserWindow){
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public void enterUrl(String url){
		urlEntry.sendKeys(url);
	}

}
