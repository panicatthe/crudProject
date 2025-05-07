package com.shinhan.emp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String converToString(Date d1) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		String str = sdf.format(d1);
		return str;
	}

	public static Date coverToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Date d2 = null;
		try {
			d2 = sdf.parse(str);
			System.out.println(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d2;
	}
	
	public static Date coverToDate2(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		Date d2 = null;
		try {
			d2 = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d2;
	}
	
	public static java.sql.Date parseToSqlDate(String dateStr) {
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date utilDate = sdf.parse(dateStr);
	        return new java.sql.Date(utilDate.getTime());
	    } catch (ParseException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	public static java.sql.Date converToSQLDATE(Date d){
		return new java.sql.Date(d.getTime());
	}

}
