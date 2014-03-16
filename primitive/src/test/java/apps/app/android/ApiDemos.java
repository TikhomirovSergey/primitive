package apps.app.android;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class ApiDemos extends FunctionalPart {
	@FindBy(name = "Graphics")
	private WebElement graphics;
	@FindBy(name = "text")
	private List<WebElement> textElements;
	@FindBy(name = "App")
	private WebElement app;
	
	protected ApiDemos(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public String getTextOfTheGraphicsElement(){
		return graphics.getText();
	}
	
	@InteractiveMethod
	public String getTextOfTheTextElement(){
		return getTextOfTheTextElement(0);
	}
	
	@InteractiveMethod
	public String getTextOfTheTextElement(int index){
		return textElements.get(index).getTagName();
	}
	
	@InteractiveMethod
	public void clickApp(){
		app.click();
	}
}
