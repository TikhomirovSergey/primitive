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
	
	public String getCopy(String folder)
	{
		InputStream inputStream = getClass().getResourceAsStream(fileName);
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(folder + '/' + fileName));
			int data = inputStream.read();
			
			while(data != -1) {
				outputStream.write(data);
				data = inputStream.read();
	        }
			outputStream.close();
		}	
		catch (Exception e) {	
			throw new RuntimeException(e);
		}		
		return (folder + '/' + fileName);
	}
}
