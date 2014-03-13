package apps.app.android;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class ContactManagerForm extends FunctionalPart {
	@FindBy(name = "Add Contact")
	private WebElement addBtn;
	@FindBy(name = "textfield")
	private List<WebElement> textFields;
	@FindBy(name = "Save")
	private WebElement saveBtn;
	
	protected ContactManagerForm(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		load();
	}
	
	@InteractiveMethod
	public void addContactClick(){
		addBtn.click();
	}
	
	@InteractiveMethod
	public void setName(String name) {
		textFields.get(0).sendKeys(name);
	}
	
	@InteractiveMethod
	public void setEmail(String eMail) {
		textFields.get(2).sendKeys(eMail);
	}
	
	public void clickSaveBtn() {
		saveBtn.click();
	}
	
	//TODO to add some more methods
}
