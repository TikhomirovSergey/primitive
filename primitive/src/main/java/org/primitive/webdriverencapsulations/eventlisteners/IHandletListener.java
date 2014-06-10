package org.primitive.webdriverencapsulations.eventlisteners;

import org.primitive.webdriverencapsulations.interfaces.IHasHandle;

public interface IHandletListener {

	public void beforeIsSwitchedOn(IHasHandle handle);

	public void whenNewHandleIsAppeared(IHasHandle handle);

	public void whenIsSwitchedOn(IHasHandle handle);

}
