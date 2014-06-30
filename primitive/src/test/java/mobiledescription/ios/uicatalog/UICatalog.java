package mobiledescription.ios.uicatalog;

import io.appium.java_client.TouchAction;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.common.mobile.ios.IOSContext;
import org.primitive.webdriverencapsulations.SingleContext;
import org.primitive.webdriverencapsulations.interfaces.IShakes;

public class UICatalog extends IOSContext implements IShakes {
	@FindBy(name = "UICatalog")
	private WebElement backToMe;
	
	public UICatalog(SingleContext context) {
		super(context);
		load();
	}

	@Override
	public void shake() {
		shaker.shake();		
	}
	
	@InteractiveMethod
	public void selectItem(String item){
		TouchAction touchAction = touchActions.getTouchAction();
		touchAction.tap(namedTextFieldGetter.getNamedTextField(item));
		touchActionsPerformer.performTouchAction(touchAction);
	}
	
	@InteractiveMethod
	public void backToMe(){
		backToMe.click();
	}
	
}
