package org.primitive.logging;

import java.awt.Color;

public enum eLogColors {
	CORRECTSTATECOLOR(new Color(0, 139, 0), "Green4"),
	WARNSTATECOLOR(new Color(255, 69, 0),"OrangeRed"),
	DEBUGCOLOR(new Color(255, 250, 250), "Snow"), SEVERESTATECOLOR(new Color(139, 0, 0 ),"DarkRed");
	
	private Color stateColor;
	private String htmlColorDescription;
	
	private eLogColors(Color color, String htmlColorDescription)
	{
		this.stateColor = color;
		this.htmlColorDescription = htmlColorDescription;
	}
	
	public Color getStateColor()
	{
		return stateColor;
	}
	
	public String getHTMLColorDescription()
	{
		return htmlColorDescription;
	}

}
