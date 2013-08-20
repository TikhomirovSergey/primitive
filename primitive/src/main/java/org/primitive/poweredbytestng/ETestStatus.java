package org.primitive.poweredbytestng;

import org.testng.ITestResult;

public enum ETestStatus {
	SUCCESS, FAILURE, SKIP, SUCCESS_PERCENTAGE_FAILURE, STARTED; 
	
	public int getTestNGStatus()
	{
		int result = 0;
		if (this.equals(SUCCESS))
		{
			result = ITestResult.SUCCESS;
		}
		
		if (this.equals(FAILURE))
		{
			result = ITestResult.FAILURE;
		}
		
		if (this.equals(SKIP))
		{
			result = ITestResult.SKIP;
		}
		
		if (this.equals(SUCCESS_PERCENTAGE_FAILURE))
		{
			result = ITestResult.SUCCESS_PERCENTAGE_FAILURE;
		}
		
		if (this.equals(STARTED))
		{
			result = ITestResult.STARTED;
		}		
		return result;
	}
	
	public synchronized static ETestStatus parse(String original)
	{
		String parcingStr = original.toUpperCase().trim();
		
		ETestStatus[] values = ETestStatus.values();
		for (ETestStatus enumElem: values)
		{
			if (parcingStr.equals(enumElem.toString()))
			{
				return enumElem;
			}
		}
		throw new IllegalArgumentException("TestNG status with specified name " + original + " is not defined");
	}
}
