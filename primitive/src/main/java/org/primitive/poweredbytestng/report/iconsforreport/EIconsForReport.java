package org.primitive.poweredbytestng.report.iconsforreport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public enum EIconsForReport {
	ERROR("error.png"),
	FINE("fine.png"),
	SUCCESS("success.png"),
	WARNING("warning.png");
	
	private String fileName;
	
	private EIconsForReport(String fileName)
	{
		this.fileName = fileName;
	}
	
	public String getCopy()
	{
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		try {
			File tempIconFile = File.createTempFile("temp_", fileName);
			FileOutputStream outputStream = new FileOutputStream(tempIconFile);
			int data = inputStream.read();
			
			while(data != -1) {
				outputStream.write(data);
				data = inputStream.read();
	        }
			outputStream.close();
			return tempIconFile.getAbsolutePath();
		}	
		catch (Exception e) {	
			throw new RuntimeException(e);
		}		
	}
}
