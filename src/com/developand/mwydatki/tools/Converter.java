package com.developand.mwydatki.tools;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Converter {

	public static Double toDouble(String toConv) {
		String replacedDots = toConv.replace(".", "");
		String replacedCommas = replacedDots.replace(",", ".");
		try {
		Double value = Double.valueOf(replacedCommas);
		return value;
		}
		catch (NumberFormatException e)
		{
			return 0.0;
		}
	}

	public static Date toDate(String date) {
		Date d = new Date();

		String[] split = date.split("-");
		if (split.length > 2) {
			Integer year = Integer.parseInt(split[2]) - 1900;
			Integer month = Integer.parseInt(split[1]) - 1;
			Integer day = Integer.parseInt(split[0]);
			d = new Date(year, month, day);
		}
		
		return d;
	}

	public static Calendar toCalendar(String dataKsiegowania) {
		Calendar cal =  Calendar.getInstance();
		String [] date = dataKsiegowania.split("-");
		if (date == null || date.length <3)
		{
			System.out.println("debug here");
		}
		cal.set(Integer.valueOf(date[2]), Integer.valueOf(date[1]), Integer.valueOf(date[0]));
		return cal;
	}

}
