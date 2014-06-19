package mobiledescription.android.bbc;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primitive.model.FunctionalPart;

public class TopicList extends FunctionalPart {

	@FindBy(id = "bbc.mobile.news.ww:id/personalisationListView")
	private WebElement topicList;	
	@FindBy(id = "bbc.mobile.news.ww:id/personlisationOkButton")
	private WebElement okButton;
	
	protected TopicList(FunctionalPart parent) {
		super(parent);
		load();
	}
	
	@InteractiveMethod
	public void setTopicChecked(String topicText, boolean checked){
		List<WebElement> topics = topicList.findElements(By.className("android.widget.LinearLayout"));
		for (WebElement topic: topics){
			if (topic.findElement(By.id("bbc.mobile.news.ww:id/feedTitle")).getText().equals(topicText)){
				WebElement checkBox = topic.findElement(By.className("android.widget.CheckBox"));
				if (!checkBox.getAttribute("checked").equals(String.valueOf(checked))){
					checkBox.click();
				}
				return;
			}
		}
		throw new NoSuchElementException("There is no topic " + topicText);
	}
	
	@InteractiveMethod
	public void ok(){
		okButton.click();
	}

}
