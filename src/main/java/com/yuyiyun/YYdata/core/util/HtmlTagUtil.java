package com.yuyiyun.YYdata.core.util;

public class HtmlTagUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * HTML中特殊标签替换
	 * @param string
	 * @return
	 */
	public static String convertTag(String string) {
		string = string.replace("& #40;", "(").replace("& #41;", ")");
		string = string.replace("& #39;", "'");
		string = string.replace("& lt;", "<").replace("& gt;", ">");
		return string;
	}

}
