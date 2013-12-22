package googledescripription;

import org.primitive.configuration.Configuration;
import org.primitive.exceptions.ConcstructTestObjectException;
import org.primitive.testobjects.Entity;
import org.primitive.testobjects.ObjectFactory;
import org.primitive.webdriverencapsulations.SingleWindow;

//модель google как приложени€
public class Google extends Entity implements IPerformsSearch, IPerformsClickOnALink{
	
	private final static String url = "http://www.google.com/";
	private SearchBar searchBar;
	private LinksAreFound linksAreFound;
	
    //да да, браузерное окно это тоже окно. Ћюбое вэб-приложение имеет окно 
	//с открытой главной страницей
	public Google(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
		searchBar     = getPart(SearchBar.class);    //эти элементы €вл€ютс€ как бы частью приложени€
		linksAreFound = getPart(LinksAreFound.class); //через них оно как бы выполн€ет свои функции
	}
	
	//так экземпл€р google уйдет в тест
	//собираетс€ по некорой дефолтной конфигурации
	public static Google getNew() throws ConcstructTestObjectException
	{
		return ObjectFactory.getEntity(Google.class, url);
	}
	
	public static Google getNew(Configuration config) throws ConcstructTestObjectException
	{
		return ObjectFactory.getEntity(Google.class, config, url);
	}

	public void performSearch(String searchString) {
		searchBar.performSearch(searchString);		
	}

	public void clickOn(int index) {
		linksAreFound.clickOn(index);		
	}

	@Deprecated
	public void clickOn(String text) {
		//It does nothing		
	}

	public int getLinkCount() {
		return linksAreFound.getLinkCount();
	}
}
