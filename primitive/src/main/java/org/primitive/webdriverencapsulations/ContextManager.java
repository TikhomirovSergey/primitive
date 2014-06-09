package org.primitive.webdriverencapsulations;


import java.util.Set;

import org.openqa.selenium.ContextAware;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.TimeoutException;
import org.primitive.configuration.commonhelpers.ContextTimeOuts;
import org.primitive.logging.Log;
import org.primitive.webdriverencapsulations.components.overriden.FluentContextConditions;

public class ContextManager extends Manager {
	private final FluentContextConditions fluent;

	public ContextManager(WebDriverEncapsulation initialDriverEncapsulation) {
		super(initialDriverEncapsulation);
		fluent = new FluentContextConditions(getWrappedDriver());
	}

	private ContextTimeOuts getContextTimeOuts() {
		return driverEncapsulation.configuration.getContextTimeOuts();
	}
	
	@Override
	void changeActive(String context) throws NoSuchContextException {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(timeOuts.getIsContextPresentTimeOut(),
				defaultTime);		
		try{
			awaiting.awaitCondition( timeOut, fluent.isContextPresent(context));
			((ContextAware) getWrappedDriver()).context(context);
		}
		catch (TimeoutException e){
			throw new NoSuchContextException("There is no context "
					+ context + "!");
		}
	}

	@Override
	/**
	 * returns context by it's index
	 */
	public String getHandleByInex(int index) throws NoSuchContextException {
		try {
			Log.debug("Attempt to get context that is specified by index "
					+ Integer.toString(index) + "...");
			ContextTimeOuts timeOuts = getContextTimeOuts();
			long timeOut = getTimeOut(timeOuts.getContextCountTimeOutSec(),
					defaultTime);
			return awaiting.awaitCondition(timeOut, 100, 
					fluent.suchContextWithIndexIsPresent(index));
		} catch (TimeoutException e) {
			throw new NoSuchContextException("Can't find context! Index out of bounds! Specified index is "
							+ Integer.toString(index)
							+ " is more then actual context count", e);
		}
	}

	@Override
	public Set<String> getHandles() {
		return ((ContextAware) getWrappedDriver()).getContextHandles();
	}

	@Override
	/**
	 * returns a new context that we have been waiting for time that
	 * specified in configuration
	 */
	public String switchToNew() throws NoSuchContextException {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(
				timeOuts.getNewContextTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut);
	}

	@Override
	/**
	 * returns a new context that we have been waiting for specified
	 * time
	 */
	public String switchToNew(long timeOutInSeconds) throws NoSuchContextException {
		try {
			Log.debug("Waiting a new context for "
					+ Long.toString(timeOutInSeconds) + " seconds.");
			String context = awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.newContextIsAppeared());
			changeActive(context);
			return context;
		} catch (TimeoutException e) {
			throw new NoSuchContextException(
					"There is no new context! We have been waiting for "
							+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	@Override
	/**
	 * returns a new context that we have been waiting for specified
	 * time. new context is predefined.
	 */
	public String switchToNew(long timeOutInSeconds, String context)
			throws NoSuchContextException {
		try {
			Log.debug("Waiting a new context '" + context + "' for "
					+ Long.toString(timeOutInSeconds));
			awaiting.awaitCondition(timeOutInSeconds, 100,
					fluent.isContextPresent(context));
			changeActive(context);
			return context;
		} catch (TimeoutException e) {
			throw new NoSuchContextException("There is no new context '"
					+ context + "'! We have been waiting for "
					+ Long.toString(timeOutInSeconds) + " seconds", e);
		}
	}

	/**
	 * returns a new context that we have been waiting for specified
	 * time. new context is predefined. Time out is specified in configuration
	 */
	@Override
	public String switchToNew(String context) {
		ContextTimeOuts timeOuts = getContextTimeOuts();
		long timeOut = getTimeOut(
				timeOuts.getNewContextTimeOutSec(),
				defaultTimeForNew);
		return switchToNew(timeOut, context);
	}

}
