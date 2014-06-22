package org.primitive.model.common.mobile;

import org.primitive.model.common.Application;
import org.primitive.model.interfaces.IDecomposable;
import org.primitive.model.interfaces.IHasManyHandlesWithNamedContexts;
import org.primitive.webdriverencapsulations.ContextManager;
import org.primitive.webdriverencapsulations.Handle;
import org.primitive.webdriverencapsulations.SingleContext;

/**
 * Representation of a mobile application
 */
public abstract class MobileAppliction extends Application implements IHasManyHandlesWithNamedContexts {

	protected MobileAppliction(SingleContext context) {
		super(context);
	}
	
	public ContextManager getContextManager(){
		return (ContextManager) manager;
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] { Handle.class };
		Object[] values = new Object[] { newHandle };
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 * and frame index
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			Integer frameIndex, String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] {Handle.class, Integer.class};
		Object[] values = new Object[] {newHandle, frameIndex};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

	/**
	 * Gets a functional part (page object) from the existing handle by context name
	 * and path to frame 
	 */
	@Override
	public <T extends IDecomposable> T getFromHandle(Class<T> partClass,
			String pathToFrame, String contextName) {
		Handle newHandle = ((ContextManager) manager).getByContextName(contextName);
		Class<?>[] params = new Class[] {Handle.class, String.class};
		Object[] values = new Object[] {newHandle, pathToFrame};
		return get(partClass,
				replaceHandleParamIfItNeedsToBe(params, partClass, newHandle),
				values);
	}

}
