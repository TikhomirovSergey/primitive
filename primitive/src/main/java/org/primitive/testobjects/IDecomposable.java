package org.primitive.testobjects;


/**
 * @author s.tihomirov
 *	It is an interface for any functional part by itself or 
 *  its parts. So, it for any object that can be decomposed. All this should work with webdriver and Page Objects 
 */

public interface IDecomposable {

	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass);
    //if object is placed in frame defined by integer value
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, Integer frameIndex);
	//if object is placed in frame defined by string path
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, String pathToFrame);
	//if object is placed in frame defined by string path. It needs some time to frame switch on 
	public <T extends FunctionalPart> T get(Class<? extends FunctionalPart> partClass, String pathToFrame, Long timeOutInSec);
		
}
