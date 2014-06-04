package apps.app.ios.ui_catalog_app;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class UINavigationBar extends FunctionalPart {

	@FindBy(xpath = "//navigationBar[1]/text[1]")
	private WebElement text;
	
	@FindBy(xpath = "//navigationBar[1]/button[1]")
	private WebElement back;
	
	public UINavigationBar(SingleWindow browserWindow){
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public void back(){
		back.click();
	}
	
	public String getCaption() {
		return text.getAttribute("name");
	}

}
