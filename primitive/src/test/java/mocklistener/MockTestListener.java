package mocklistener;

import java.util.Calendar;

import org.primitive.logging.Photographer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class MockTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		String picFolder = result.getTestContext().getOutputDirectory();
		picFolder = picFolder + "/" + Calendar.getInstance().getTime().toString().replace(":", " ");
		Photographer.setOutputFolder(picFolder + "/");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}

}
