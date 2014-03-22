package apps.app.android.vk;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;

public class ErrorMsg extends FunctionalPart {

	@FindBy(id = "android:id/message")
	private WebElement msgText;
	
	protected ErrorMsg(FunctionalPart parent)
			throws ConcstructTestObjectException {
		super(parent);
		load();
	}
	
	@InteractiveMethod
	public String getText(){
		return msgText.getText();
	}

}
