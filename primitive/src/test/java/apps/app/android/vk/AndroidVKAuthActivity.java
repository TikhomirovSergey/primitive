package apps.app.android.vk;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

public class AndroidVKAuthActivity extends FunctionalPart {

	@FindBy(id = "com.vkontakte.android:id/auth_login_btn")
	private WebElement logInBtn;
	@FindBy(id = "com.vkontakte.android:id/auth_login")
	private WebElement login;
	@FindBy(id = "com.vkontakte.android:id/auth_pass")
	private WebElement password;
	@FindBy(id = "com.vkontakte.android:id/auth_btn")
	private WebElement auth;
	

	protected AndroidVKAuthActivity(SingleWindow window)
			throws ConcstructTestObjectException {
		super(window);
		load();
	}
	
	@InteractiveMethod
	public void loginClick(){
		logInBtn.click();
	}
	
	@InteractiveMethod
	public void setLogin(String login){
		this.login.sendKeys(login);
	}
	
	@InteractiveMethod
	public void setPassword(String password){
		this.password.sendKeys(password);
	}
	
	@InteractiveMethod
	public void authClick(){
		auth.click();
	}
}
