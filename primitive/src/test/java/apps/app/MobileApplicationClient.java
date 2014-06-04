package apps.app;

import org.primitive.configuration.Configuration;
import org.primitive.model.Entity;
import org.primitive.model.ObjectFactory;
import org.primitive.webdriverencapsulations.SingleWindow;
import org.primitive.webdriverencapsulations.WebDriverEncapsulation;

/**
 * 
 * There is a presentation of mobile application client An instance will be
 * launched on Android, iOS and FireFox OS
 */
public class MobileApplicationClient extends Entity{

	public MobileApplicationClient(SingleWindow browserWindow){
		super(browserWindow);
	}

	public static MobileApplicationClient getNew() {
		return ObjectFactory.getEntity(MobileApplicationClient.class);
	}

	public static MobileApplicationClient getNew(Configuration config) {
		return ObjectFactory.getEntity(MobileApplicationClient.class, config);
	}

	public static MobileApplicationClient getNew(
			WebDriverEncapsulation externalEncapsulation) {
		return ObjectFactory.getEntity(externalEncapsulation,
				MobileApplicationClient.class);
	}

}
