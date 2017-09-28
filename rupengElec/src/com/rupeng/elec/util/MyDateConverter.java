package com.rupeng.elec.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.opensymphony.xwork2.XWorkException;

/**
 * 自定义日期类型转换器
 * 
 * @author LeeGossHK
 * 
 */
public class MyDateConverter extends StrutsTypeConverter {

	private DateFormat[] dateFormats = {

	new SimpleDateFormat("yyyy-MM-dd"), new SimpleDateFormat("yyyy年MM月dd日"), };

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Date date = null;
		String dateString = values[0];
		for (DateFormat dateFormat : dateFormats) {
			try {
				date = dateFormat.parse(dateString);
			} catch (ParseException e) {
			}
		}
		if (date == null) {
			throw new XWorkException("Could not parse date" + dateString);
		}
		return date;

	}

	@Override
	public String convertToString(Map context, Object o) {
		return dateFormats[0].format(o);
	}

}
