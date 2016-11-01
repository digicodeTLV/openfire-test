package com.test.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tymoshenkol on 01-Nov-16.
 */
public class DateUtil {

	public static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
	public static String getNow(){
		return df.format(new Date());
	}
}
