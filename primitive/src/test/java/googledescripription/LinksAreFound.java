package googledescripription;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class LinksAreFound extends FunctionalPart implements ILinkList {
	
	@FindBy(xpath = ".//*[@class='r']/a")
	private List<WebElement> linksAreFound;
	
	public LinksAreFound(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		load();
	}

	@InteractiveMethod
	public void openLinkByIndex(int index) {
		String reference = linksAreFound.get(index - 1).getAttribute("href");
		scriptExecutor.executeScript("window.open('" + reference + "');");
	}

	@InteractiveMethod
	public int getLinkCount() {
		return linksAreFound.size();
	}

	@InteractiveMethod
	public void clickOnLinkByIndex(int index) {
		Actions clickAction = new Actions(driverEncapsulation.getWrappedDriver());
		clickAction.click(linksAreFound.get(index - 1));
		clickAction.perform();
	}

}
