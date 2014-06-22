package googledescripription;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;
import org.primitive.configuration.Configuration;
import org.primitive.model.common.browser.BrowserApplication;
import org.primitive.model.common.browser.WebFactory;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

public class Google extends BrowserApplication implements IPerformsSearch, ILinkList, WrapsDriver{
	
	private final static String url = "http://www.google.com/";
	private SearchBar searchBar;
	private LinksAreFound linksAreFound;
	
	protected Google(SingleWindow browserWindow){
		super(browserWindow);
		searchBar     = getPart(SearchBar.class);    
		linksAreFound = getPart(LinksAreFound.class); 
	}
	
	public static Google getNew()
	{
		return WebFactory.getApplication(Google.class, url);
	}
	
	public static Google getNew(Configuration config)
	{
		return WebFactory.getApplication(Google.class, config, url);
	}
	
	public static Google getNew(WebDriverEncapsulation externalEncapsulation)
	{
		return WebFactory.getApplication(Google.class, externalEncapsulation, url);
	}

	public void performSearch(String searchString) {
		searchBar.performSearch(searchString);		
	}

	public void openLinkByIndex(int index) {
		linksAreFound.openLinkByIndex(index);		
	}

	public int getLinkCount() {
		return linksAreFound.getLinkCount();
	}
	
	//closes google main page
	public void close()
	{
		//TODO workaround
		((SingleWindow) handle).close();
		destroy();
	}	

	@Override
	public WebDriver getWrappedDriver() {
		return driverEncapsulation.getWrappedDriver();
	}

	@Override
	public void clickOnLinkByIndex(int index) {
		linksAreFound.clickOnLinkByIndex(index);		
	}
}
