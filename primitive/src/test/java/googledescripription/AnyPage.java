package googledescripription;

import org.primitive.testobjects.ConcstructTestObjectException;
import org.primitive.testobjects.FunctionalPart;
import org.primitive.webdriverencapsulations.SingleWindow;

//любая найденная страница, открытая через google
public class AnyPage extends FunctionalPart {

	public AnyPage(SingleWindow browserWindow)
			throws ConcstructTestObjectException {
		super(browserWindow);
	}
}
