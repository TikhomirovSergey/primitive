package apps.app.android.apidemos;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class ApiDemos extends FunctionalPart {
	
	@FindBy(className = "android.widget.TextView")
	private List<WebElement> textViews;
	
	protected ApiDemos(SingleWindow browserWindow){
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public String getViewTextByIndex(int index){
		try{
			return textViews.get(index).getText();
		}
		catch (IndexOutOfBoundsException e){
			throw new NoSuchElementException("There is no view that is indexed " + String.valueOf(index), e);
		}
	}
	
	@InteractiveMethod
	public void clickOnTextView(int index){
		try{
			textViews.get(index).click();;
		}
		catch (IndexOutOfBoundsException e){
			throw new NoSuchElementException("There is no view that is indexed " + String.valueOf(index), e);
		}
		
	}
}
