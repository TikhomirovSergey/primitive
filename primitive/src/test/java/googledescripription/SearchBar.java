package googledescripription;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class SearchBar extends FunctionalPart implements IPerformsSearch{
	@FindBy(name = "q")
	private WebElement searchInput;
	@FindBy(name="btnG")
	private WebElement searchButton;
	
	public SearchBar(SingleWindow browserWindow) {
		super(browserWindow);
		load();
	}

	@InteractiveMethod
	public void performSearch(String searchString) {
		searchInput.sendKeys(searchString);
		searchButton.click();
	}
}
