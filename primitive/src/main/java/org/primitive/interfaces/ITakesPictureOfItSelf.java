package org.primitive.interfaces;

/**
 * @author s.tihomirov
 * Implementors should take pictires of inself 
 */
public interface ITakesPictureOfItSelf {
	public void takeAPictureOfAnInfo(String Comment);
	public void takeAPictureOfAFine(String Comment);	
	public void takeAPictureOfAWarning(String Comment);
	public void takeAPictureOfASevere(String Comment);
}
