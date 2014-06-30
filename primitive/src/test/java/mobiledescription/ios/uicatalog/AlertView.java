package mobiledescription.ios.uicatalog;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.common.mobile.Context;
import org.primitive.webdriverencapsulations.SingleContext;


public class AlertView extends Context {
	@FindBy(name = "Simple")
	private WebElement simpleAlert;
	
	protected AlertView(SingleContext context) {
		super(context);
		load();
	}
	
	@InteractiveMethod
	public void invokeSimpleAlert(){
		simpleAlert.click();
	}


}
