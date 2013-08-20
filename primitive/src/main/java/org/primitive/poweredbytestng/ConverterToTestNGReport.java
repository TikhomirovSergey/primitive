/**
 * 
 */
package	org.primitive.poweredbytestng;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;

import org.primitive.logging.ILogConverter;
import org.primitive.logging.Log;
import org.primitive.logging.Photographer;
import org.primitive.logging.Log.LogRecWithAttach;
import org.testng.ITestResult;
import org.testng.Reporter;



/**
 * @author s.tihomirov
 *	It is the basic implementation of ISender for using by TESTNG framework. It posts log record in report
 */	
public class ConverterToTestNGReport implements ILogConverter{

	private final String debugColor = "Snow";
	private final String errorColor = "Red";
	private final int firstStackElements = 5;
	private final String htmlItemPattern = 
	"<li><table align='center' width='100%'>"+
		      "<tr  bgcolor=" + "#Color" + " valign=top align=left>"+
		      "<td><b>"+"#Time" + " " +"#Message"+" </d></td>"+
		      "</tr>"+
		      "</table></li>";
	private final String successColor = "LimeGreen";
	private final String warnColor = "Yellow";
	
	//for posting screenshots as pictures
	private final String textPatternForPicture = "<p><img src=\"file:///FilePath\" alt=\"Comment\"></p>";
	//for posting files as links
	private final String textPatternForAnyFile = "<a href= \"FilePath\" type=\"file\">Comment> </a>";
	//Can we post this to report? It depends on logger level
	private boolean iCanPostThisToReport(Level level, Level borderLevel)
	{
		if (borderLevel == Level.ALL)
		{
			return true;
		}
		
		else if ((borderLevel == Level.FINE)|(borderLevel == Level.FINER)|(borderLevel == Level.FINEST))
		{
			if ((level == Level.FINE)|(level == Level.FINER)|(level == Level.FINEST)|(level == Level.CONFIG))
			{
				return true;
			}
			else
			{
				return true;
			}
		}		
		else if (borderLevel == Level.INFO)
		{
			if ((level==Level.INFO)|(level==Level.WARNING)|(level==Level.SEVERE))
			{
				return true;
			}
			else
			{
				return false;
			}
		} 	
		else if (borderLevel == Level.WARNING)
		{
			if ((level==Level.WARNING)|(level==Level.SEVERE))
			{
				return true;
			}
			else
			{
				return false;
			}
		}	
		else if (borderLevel == Level.SEVERE)
		{
			if (level==Level.SEVERE)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	private String formatWithStackTrace(String original, LogRecWithAttach rec)
	{
		String formatted = null;
		
		StringBuilder stackBuilder = new StringBuilder();
		stackBuilder.append(original + "\n");
		//if there is a stack trace
		if (rec.getThrown()!=null)
		{
			StackTraceElement stack[] = rec.getThrown().getStackTrace();
			int stackLength = stack.length;
			int border = 0;
			
			if (stackLength<firstStackElements)
			{
				border = stackLength;
			}
			else
			{
				border = firstStackElements;
			}
			
			for (int i=0; i<=border-1; i++)
			{
				stackBuilder.append(stack[i].toString() + "\n");
			}
			
			stackBuilder.append("...");
		}	
		formatted = stackBuilder.toString();	
		return formatted;
	}
	
	
	private String returnLogMessage(LogRecWithAttach rec)
	{
		String formattedMessage = null;
		File attachment = rec.getAttachedFile();
		if (attachment==null) //if there is no attached file
		{
			formattedMessage = rec.getMessage();
		}
		else
		{
			String filepath  = attachment.getAbsolutePath();
			String pattern = null;
			if (!filepath.contains(Photographer.format)) //if there is not a picture
			{
				pattern = textPatternForAnyFile;
			}
			else
			{
				pattern = textPatternForPicture;
			}
			formattedMessage = pattern.replace("Comment", rec.getMessage());
			formattedMessage = formattedMessage.replace("FilePath", attachment.getAbsolutePath());
		}
		return formatWithStackTrace(formattedMessage, rec);
	}
	
	private String returnHtmlString(LogRecWithAttach rec)
	{
		Level level = rec.getLevel();
		String color = null; 
		if (level==Level.SEVERE)
		{
			color = errorColor;
		}
		else if (level==Level.WARNING)
		{
			color = warnColor;
		}
		else if (level==Level.INFO)
		{
			color = successColor;
		}
		else 
		{
			color = debugColor;
		}
		
		Date date = new Date(rec.getMillis());
		String turnedString = htmlItemPattern.replace("#Color", color);
		turnedString        = turnedString.replace("#Time", date.toString());
		turnedString        = turnedString.replace("#Message", returnLogMessage(rec));
		
		return turnedString;
	}

	private ITestResult setToReport(String htmlInjection)
	{
		Reporter.setEscapeHtml(false);
		Reporter.log(htmlInjection);
		return Reporter.getCurrentTestResult();
	}
	
	@Override
	public void convert(LogRecWithAttach record) 
	{
		Level level = Log.getLevel();
		if (iCanPostThisToReport(record.getLevel(), level))
		{				
			ITestResult res = setToReport(returnHtmlString(record));;
			ResultStore store = ResultStore.get(res);
			store.addLogRecord(record);
		}			
	}	
}