package org.goaler.ballwar.app.util;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import android.util.Log;

public class GLog {
	
	public static void info(String tag, String format, Object... argArray){
		Log.i(tag, format(format,argArray));
	}
	
	public static void error(String tag, String format, Object... argArray){
		Log.e(tag, format(format,argArray));
	}
	
	private static String format(String format, Object... argArray){
		FormattingTuple arrayFormat = MessageFormatter.arrayFormat(format, argArray);
		return arrayFormat.getMessage();
	}
}
