package org.primitive.webdriverencapsulations.components.overriden;

import java.util.Set;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.primitive.webdriverencapsulations.components.WebdriverComponent;

/**
 * Waits for some conditions on native and web-based contexts.
 *
 */
public class FluentContextConditions extends WebdriverComponent {

	public FluentContextConditions(WebDriver driver) {
		super(driver);
	}
	
	private Boolean isContextPresent(final WebDriver from, String conext){
		Set<String> conexts = ((ContextAware) from).getContextHandles();
		if (conexts.contains(conext)){
			return true;
		}
		return null;
	}
	
	public ExpectedCondition<Boolean> isContextPresent(final String conext){
		return new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver input) {
				return isContextPresent(input, conext);
			}			
		};
	}

}
