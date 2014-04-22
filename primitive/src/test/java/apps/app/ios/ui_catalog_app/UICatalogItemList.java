package apps.app.ios.ui_catalog_app;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class UICatalogItemList extends FunctionalPart {

	@FindBy(tagName = "cell")
	private List<WebElement> cells;
	
	public UICatalogItemList(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public void clickOnCell(String text){
		for (WebElement cell: cells){
			if (cell.getAttribute("name").equals(text)){
				cell.click();
				return;
			}
		}
		throw new NoSuchElementException("There is no cell with the text '" + text +" '");
	}
	
	@InteractiveMethod
	public void clickOnCell(int index){
		if (cells.size() <= (index+1)){
			cells.get(index).click();
		}
		throw new NoSuchElementException("Cell collection size is lower than '" + index + 1 +" '");
	}
	
	
	
	

}
