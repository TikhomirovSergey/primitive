package org.primitive.webdriverencapsulations.ui;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.primitive.logging.Log;


/**
 * @author s.tihomirov
 *org.openqa.selenium.interactions.Actions with asynchron execution
 */
public class AsyncActions extends Actions {
	/**
	 * @author s.tihomirov
	 *Performs interactive actions in another thread asynchronously
	 */
	private class AsyncPerformer extends Thread {
		private Actions action;
		private boolean isExecuting=false; //Are actions executed yet? If time has passed.
		private RuntimeException error; //it will refer to some exception if it is caught 
		
		private AsyncPerformer(Actions actions) 
		{
			this.action = actions;
			this.start(); //threat starting
			this.isExecuting = true;
		}
		
		private void execute(long secsToWait) throws RuntimeException
		{
			//Verifying execution in another thread.
			long startTime = Calendar.getInstance().getTimeInMillis();
			long currentTime = Calendar.getInstance().getTimeInMillis();
			
			while ((isExecuting)&(Math.abs(currentTime-startTime)<secsToWait*1000))
			{
				currentTime = Calendar.getInstance().getTimeInMillis();
			}
			if (error!=null)
			{
				throw error;
			}
			if (isExecuting)
			{
				Log.message("Actions have not been completed. Specified time: "+Long.toString(secsToWait)+" sec. "+
							"We will watch results outside.");
			}
			else
			{
				Log.message("Actions have been completed successfully.");
			}		
		}
		
		//execution for specified time
		private void perform(long secsToWait)
		{
			execute(secsToWait);
		}
		
		//execution for default time
		private void perform()
		{
			execute(averageSecsToWait);	
		}
		
		public void run()
		{
			try 
			{
				action.perform();
			} 
			catch (RuntimeException e)
			{
				Log.warning("During execution of specified actions the problem has been found out: " + e.getMessage(),e);
				error = e;
			}
			isExecuting = false;
		}

	}

	private long averageSecsToWait = 2;
	
	public AsyncActions(WebDriver driver) {
		super(driver);
	}

	public AsyncActions(Keyboard keyboard) {
		super(keyboard);
	}

	public AsyncActions(Keyboard keyboard, Mouse mouse) {
		super(keyboard, mouse);
	}
	
	public AsyncActions(long avaerageTimeForExecution, WebDriver driver) {
		super(driver);
		averageSecsToWait = avaerageTimeForExecution;
	}

	public AsyncActions(long avaerageTimeForExecution, Keyboard keyboard) {
		super(keyboard);
		averageSecsToWait = avaerageTimeForExecution;
	}

	public AsyncActions(long avaerageTimeForExecution, Keyboard keyboard, Mouse mouse) {
		super(keyboard, mouse);
		averageSecsToWait = avaerageTimeForExecution;
	}
	
	public void performÀsynchronously()
	{
		
		AsyncPerformer performer = new AsyncPerformer(this);
		performer.perform();		
	}

	public void performÀsynchronously(long secsToWait)
	{
		AsyncPerformer performer = new AsyncPerformer(this);
		performer.perform(secsToWait);	
	}	

}
