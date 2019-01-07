package com.monginis.ops.model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  private static SimpleDateFormat inSDF = new SimpleDateFormat("dd-MM-yyyy");
  private static SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd");

  public static String formatDate(String inDate) {
    String outDate = "";
    if (inDate != null) {
        try {
            Date date = inSDF.parse(inDate);
            outDate = outSDF.format(date);
        } catch (ParseException ex){ 
        }
    }
    return outDate;
  }

  public static Date stringToDate(String inDate) {
	  Date date=null;
	    if (inDate != null) {
	        try {
	             date = inSDF.parse(inDate);
	            
	            
	        } catch (ParseException ex){ 
	        }
	    }
	    return date;
	  }

  
  
  

}