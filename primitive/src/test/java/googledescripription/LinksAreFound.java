package googledescripription;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class LinksAreFound extends FunctionalPart implements IPerformsClickOnALink {
	
	@FindBy(xpath = ".//*[@class='r']/a")
	private List<WebElement> linksAreFound;
	
	public LinksAreFound(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		load();
	}

	@InteractiveMethod
	public void clickOn(int index) {
		linksAreFound.get(index-1).click();		
	}

	@Deprecated
	public void clickOn(String text) {
		// It does nothing		
	}

	@InteractiveMethod
	public int getLinkCount() {
		return linksAreFound.size();
	}

	@InteractiveMethod
	public void clickOnByMouse(int index) {
		WebElement link = linksAreFound.get(index-1);
		Actions click = new Actions(interaction.getKeyboard(), interaction.getMouse());
		click.click(link);
		highlightAsInfo(link, "Element will be clicked on");
		click.perform();
		
	}

}
