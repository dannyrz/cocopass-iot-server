package com.cocopass.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CDate {

	public static Date ToDate(String textDate)
	{
		SimpleDateFormat sdf=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(textDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	public static String GetNow()
	{
		return CDate.ToString("yyyy-MM-dd HH:mm:ss", new Date());
	}
	
	public static String ToString(String format,Date date)
	{
		SimpleDateFormat sdf=new  SimpleDateFormat(format);
		String str = null;
		str = sdf.format(date);
		return str;
	}
	
	public static long ToMillions(String Date,String format) throws ParseException
	{
		SimpleDateFormat sdf= new SimpleDateFormat(format);
		Date dt2 = sdf.parse(Date);
		long lTime = dt2.getTime() ;
		return lTime;
	}
	
	public static Date MillionsToDate(long millSec)
	{
		 Date date= new Date(millSec);
		 return date;
	}
}
