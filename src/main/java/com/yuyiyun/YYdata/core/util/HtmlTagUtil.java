package com.yuyiyun.YYdata.core.util;

import org.springframework.web.util.HtmlUtils;

public class HtmlTagUtil {

	public static void main(String[] args) {
		String convertTag = convertTag("");
		System.out.println(convertTag);
	}

	/**
	 * HTML中特殊标签替换
	 * 
	 * @param string
	 * @return
	 */
	public static String convertTag(String string) {
		if (null==string) {
			return null;
		}
		string = string.replaceAll("& ", "&");
		String unescape = HtmlUtils.htmlUnescape(string);
		return unescape;
	}

}
