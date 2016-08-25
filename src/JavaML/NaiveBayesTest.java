package JavaML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NaiveBayesTest
{
	public static void main(String[]args){
		
	
		Pattern regex= Pattern.compile(".*(\\b\\d{4}\\b)");
		String answer="";
		String str="June 1995 – June 2003 (8 years 1 month)";
		Matcher m = regex.matcher(str);
		if (m.find()) {
		    String result = m.group(1);
		    
		    System.out.println(result);
		}
	
		}
		
		
		
				
		
	
		
		
	}




