package services;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.primitive.webdriverencapsulations.eventlisteners.IWindowListener;
import org.primitive.webdriverencapsulations.interfaces.IExtendedWindow;

public class MockWindowListener implements IWindowListener {
	public static MockWindowListener listener;
	public static boolean wasInvoked = false;
	
	public MockWindowListener() {
		super();
		listener = this;
	}

	@Override
	public void whenNewWindewIsAppeared(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsClosed(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void whenWindowIsClosed(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsSwitchedOn(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void whenWindowIsSwitchedOn(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsMaximized(IExtendedWindow window) {
		wasInvoked = true;

	}

	@Override
	public void whenWindowIsMaximized(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsRefreshed(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void whenWindowIsRefreshed(IExtendedWindow window) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsMoved(IExtendedWindow window, Point point) {
		wasInvoked = true;
	}

	@Override
	public void whenWindowIsMoved(IExtendedWindow window, Point point) {
		wasInvoked = true;
	}

	@Override
	public void beforeWindowIsResized(IExtendedWindow window,
			Dimension dimension) {
		wasInvoked = true;
	}

	@Override
	public void whenWindowIsResized(IExtendedWindow window, Dimension dimension) {
		wasInvoked = true;
	}

}
