package apps.testexample;

import org.primitive.configuration.Configuration;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import apps.app.MobileApplicationClient;
import apps.app.android.ContactManagerForm;

public class TestExamples {
	
	@Test
	@Parameters(value = { "config" })
	public void testOnAndroidContactManagerApp(
			@Optional("androidApp.json") String config) {
		Configuration configuration = Configuration
				.get("src/test/resources/configs/desctop/mobile/android/application/" + config);
		MobileApplicationClient client = MobileApplicationClient
				.getNew(configuration);

		try {
			ContactManagerForm contactManager = client
					.getPart(ContactManagerForm.class);
			contactManager.addContactClick();
			contactManager.setName("Some Name");
			contactManager.setEmail("Some@example.com");
		} finally {
			client.quit();
		}

	}
}
