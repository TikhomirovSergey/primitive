package mobiletest;

import junit.framework.Assert;
import mobiledescription.android.bbc.BBCMain;
import mobiledescription.android.bbc.TopicList;
import org.primitive.configuration.Configuration;
import org.primitive.model.MobileAppliction;
import org.primitive.model.MobileFactory;
import org.testng.annotations.Test;

public class MobileTestExamples {
	
  @Test
  public void androidNativeAppTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/android/android_bbc.json");
		MobileAppliction bbc = MobileFactory.getApplication(
				MobileAppliction.class, config);
		try {
			BBCMain bbcMain = bbc.getPart(BBCMain.class);
			Assert.assertNotSame(0, bbcMain.getArticleCount());
			Assert.assertNotSame("", bbcMain.getArticleTitle(1));
			Assert.assertNotSame(bbcMain.getArticleTitle(1),
					bbcMain.getArticleTitle(0));
			bbcMain.selectArticle(1);
			Assert.assertEquals(true, bbcMain.isArticleHere());

			bbcMain.refresh();
			bbcMain.edit();

			TopicList topicList = bbcMain.getPart(TopicList.class);
			topicList.setTopicChecked("LATIN AMERICA", true);
			topicList.setTopicChecked("UK", true);
			topicList.ok();

			bbcMain.edit();
			topicList.setTopicChecked("LATIN AMERICA", false);
			topicList.setTopicChecked("UK", false);
			topicList.ok();
		} finally {
			bbc.quit();
		}	  
  }
  
  @Test
  public void androidHybridAppTest() {
		Configuration config = Configuration
				.get("src/test/resources/configs/mobile/app/android/android_selendroid-test-app.json");
		MobileAppliction selendroidTestApp = MobileFactory.getApplication(
				MobileAppliction.class, config);
		
		try {
		} finally {
			selendroidTestApp.quit();
		}
	}
}
