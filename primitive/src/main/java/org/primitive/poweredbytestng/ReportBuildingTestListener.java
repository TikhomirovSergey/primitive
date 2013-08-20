package org.primitive.poweredbytestng;


import java.util.logging.Level;

import org.primitive.configuration.Configuration;
import org.primitive.interfaces.IConfigurable;
import org.primitive.logging.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;




public class ReportBuildingTestListener implements IConfigurable, ITestListener {
	private static final int defaultStatusOnWarning = ITestResult.SUCCESS_PERCENTAGE_FAILURE;
	//By default test will be failed if there are warnings
	private int statusOnWarning;
	private static final ConverterToTestNGReport converter = new ConverterToTestNGReport();;
	
	@Override
	public void onFinish(ITestContext arg0)
	{
		Log.removeConverter(converter);
	}

	@Override
	public void onStart(ITestContext arg0)
	{
		Log.addConverter(converter);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0)
	{	
		synchronizeTestResults(arg0);
	}

	@Override
	public void onTestFailure(ITestResult arg0) 
	{
		Log.error("Test has failed");
	}

	@Override
	public void onTestSkipped(ITestResult arg0) 
	{
		Log.warning("Is skipped");
	}

	@Override
	public void onTestStart(ITestResult arg0) 
	{
		resetAccordingTo(Configuration.byDefault);
	}

	@Override
	public void onTestSuccess(ITestResult arg0) 
	{
		synchronizeTestResults(arg0);
	}
	
	//sets real status according to Log information
	protected void synchronizeTestResults(ITestResult resultForSync)
	{
		Level resultLevel = ResultStore.getLevel(resultForSync);
		if (resultLevel==Level.SEVERE)
		{
			resultForSync.setStatus(ITestResult.FAILURE);
			Log.error("Test has failed");
		}
		else if (resultLevel == Level.WARNING)
		{
			resultForSync.setStatus(statusOnWarning);
		}
		
		
	}

	@Override
	public void resetAccordingTo(Configuration config) {
		
		String status = config.getTestStatus().getOnWarning();
		if (status==null)
		{
			statusOnWarning = defaultStatusOnWarning;
		}
		else
		{
			ETestStatus realStatus = ETestStatus.parse(status);
			statusOnWarning = realStatus.getTestNGStatus();
		}		
	}

}
